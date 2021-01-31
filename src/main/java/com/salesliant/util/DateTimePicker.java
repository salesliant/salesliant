/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import javafx.scene.control.skin.DatePickerSkin;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 *
 * @author Lewis
 */
public final class DateTimePicker extends DatePicker {

    private final ObjectProperty<LocalTime> timeValue = new SimpleObjectProperty<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a");
    private ObjectProperty<LocalDateTime> dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.now());

    public DateTimePicker() {
        super();
        this.setEditable(false);
        setValue(LocalDate.now());
        setTimeValue(LocalTime.now());
        setConverter(new InternalConverter());
        valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                dateTimeValue.set(null);
            } else {
                if (dateTimeValue.get() == null) {
                    dateTimeValue.set(LocalDateTime.of(newValue, LocalTime.now()));
                } else {
                    LocalTime time = dateTimeValue.get().toLocalTime();
                    dateTimeValue.set(LocalDateTime.of(newValue, time));
                }
            }
        });

        dateTimeValue.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate dateValue = newValue.toLocalDate();
                boolean forceUpdate = dateValue.equals(valueProperty().get());
                setValue(dateValue);
                if (forceUpdate) {
                    setConverter(new InternalConverter());
                }
            } else {
                setValue(null);
            }

        });
        getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                simulateEnterPressed();
            }
        });
    }

    public void simulateEnterPressed() {
        getEditor().commitValue();
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new DateTimePickerSkin(this);
    }

    public LocalTime getTimeValue() {
        return timeValue.get();
    }

    public void setTimeValue(LocalTime timeValue) {
        this.timeValue.set(timeValue);
    }

    public ObjectProperty<LocalTime> timeValueProperty() {
        return timeValue;
    }

    public LocalDateTime getDateTimeValue() {
        return dateTimeValueProperty().get();
    }

    public void setDateTimeValue(LocalDateTime dateTimeValue) {
        dateTimeValueProperty().set(dateTimeValue);
    }

    public ObjectProperty<LocalDateTime> dateTimeValueProperty() {
        if (dateTimeValue == null) {
            dateTimeValue = new SimpleObjectProperty<>(LocalDateTime.of(this.getValue(), timeValue.get()));
            timeValue.addListener(t -> {
                dateTimeValue.set(LocalDateTime.of(this.getValue(), timeValue.get()));
            });

            valueProperty().addListener(t -> {
                dateTimeValue.set(LocalDateTime.of(this.getValue(), timeValue.get()));
            });
        }
        return dateTimeValue;
    }

    class InternalConverter extends StringConverter<LocalDate> {

        @Override
        public String toString(LocalDate object) {
            LocalDateTime value = getDateTimeValue();
            return (value != null) ? value.format(formatter) : "";
        }

        @Override
        public LocalDate fromString(String value) {
            if (value == null || value.isEmpty()) {
                dateTimeValue.set(null);
                return null;
            }

            dateTimeValue.set(LocalDateTime.parse(value, formatter));
            return dateTimeValue.get().toLocalDate();
        }
    }

    class DateTimePickerSkin extends DatePickerSkin {

        private final DateTimePicker dateTimePicker;
        private Node datePickerContent;

        public DateTimePickerSkin(DateTimePicker datePicker) {
            super(datePicker);
            this.dateTimePicker = datePicker;
        }

        @Override
        public Node getPopupContent() {
            if (datePickerContent == null) {
                datePickerContent = super.getPopupContent();

                Slider hours = new Slider(0, 23, (dateTimePicker.getTimeValue() != null ? dateTimePicker.getTimeValue().getHour() : 0));
                Label hoursValue = new Label("Hours: " + (dateTimePicker.getTimeValue() != null ? DateTimeFormatter.ofPattern("hh a").format(dateTimePicker.getTimeValue()) : "") + " ");
                hoursValue.setPrefWidth(80);

                Slider minutes = new Slider(0, 59, (dateTimePicker.getTimeValue() != null ? dateTimePicker.getTimeValue().getMinute() : 0));
                Label minutesValue = new Label("Minutes: " + (dateTimePicker.getTimeValue() != null ? dateTimePicker.getTimeValue().getMinute() : "") + " ");
                minutesValue.setPrefWidth(80);

                ((VBox) datePickerContent).getChildren().addAll(new HBox(hoursValue, hours), new HBox(minutesValue, minutes));

                hours.valueProperty().addListener((observable, oldValue, newValue) -> {
                    dateTimePicker.setDateTimeValue(dateTimePicker.getDateTimeValue().withHour(newValue.intValue()));
                    hoursValue.setText("Hours: " + DateTimeFormatter.ofPattern("hh a").format(dateTimePicker.getDateTimeValue()) + " ");
                });

                minutes.valueProperty().addListener((observable, oldValue, newValue) -> {
                    dateTimePicker.setDateTimeValue(dateTimePicker.getDateTimeValue().withMinute(newValue.intValue()));
                    minutesValue.setText("Minutes: " + String.format("%02d", dateTimePicker.getDateTimeValue().getMinute()) + " ");
                });
            }
            return datePickerContent;
        }
    }
}
