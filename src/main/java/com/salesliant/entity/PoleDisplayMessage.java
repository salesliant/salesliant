/**
	* Copyright (c) minuteproject, minuteproject@gmail.com
	* All rights reserved.
	* 
	* Licensed under the Apache License, Version 2.0 (the "License")
	* you may not use this file except in compliance with the License.
	* You may obtain a copy of the License at
	* 
	* http://www.apache.org/licenses/LICENSE-2.0
	* 
	* Unless required by applicable law or agreed to in writing, software
	* distributed under the License is distributed on an "AS IS" BASIS,
	* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	* See the License for the specific language governing permissions and
	* limitations under the License.
	* 
	* More information on minuteproject:
	* twitter @minuteproject
	* wiki http://minuteproject.wikispaces.com 
	* blog http://minuteproject.blogspot.net
	* 
*/
/**
	* template reference : 
	* - Minuteproject version : 0.9.11
	* - name      : DomainEntityJPA2Annotation
	* - file name : DomainEntityJPA2Annotation.vm
	* - time      : 2021/01/30 AD at 23:59:31 EST
*/
package com.salesliant.entity;

//MP-MANAGED-ADDED-AREA-BEGINNING @import@
//MP-MANAGED-ADDED-AREA-ENDING @import@
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * <p>Title: PoleDisplayMessage</p>
 *
 * <p>Description: Domain Object describing a PoleDisplayMessage entity</p>
 *
 */
@Entity (name="PoleDisplayMessage")
@Table (name="pole_display_message")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class PoleDisplayMessage implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @message_line1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @message_line1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-message_line1@
    @Column(name="message_line1"  , length=65535 , nullable=true , unique=false)
    private String messageLine1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @effect_line1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @effect_line1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-effect_line1@
    @Column(name="effect_line1"   , nullable=true , unique=false)
    private Integer effectLine1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @update_rate_line1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @update_rate_line1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-update_rate_line1@
    @Column(name="update_rate_line1"   , nullable=true , unique=false)
    private Integer updateRateLine1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_and_time_line1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_and_time_line1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_and_time_line1@
    @Column(name="date_and_time_line1"   , nullable=true , unique=false)
    private Boolean dateAndTimeLine1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @scroll_change_size_line1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @scroll_change_size_line1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-scroll_change_size_line1@
    @Column(name="scroll_change_size_line1"   , nullable=true , unique=false)
    private Integer scrollChangeSizeLine1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @message_line2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @message_line2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-message_line2@
    @Column(name="message_line2"  , length=65535 , nullable=true , unique=false)
    private String messageLine2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @effect_line2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @effect_line2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-effect_line2@
    @Column(name="effect_line2"   , nullable=true , unique=false)
    private Integer effectLine2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @update_rate_line2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @update_rate_line2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-update_rate_line2@
    @Column(name="update_rate_line2"   , nullable=true , unique=false)
    private Integer updateRateLine2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_and_time_line2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_and_time_line2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_and_time_line2@
    @Column(name="date_and_time_line2"   , nullable=true , unique=false)
    private Boolean dateAndTimeLine2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @scroll_change_size_line2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @scroll_change_size_line2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-scroll_change_size_line2@
    @Column(name="scroll_change_size_line2"   , nullable=true , unique=false)
    private Integer scrollChangeSizeLine2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-field-pole_display_message@
    @OneToMany (targetEntity=com.salesliant.entity.Station.class, fetch=FetchType.LAZY, mappedBy="poleDisplayMessage", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Station> stations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public PoleDisplayMessage() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-message_line1@
    public String getMessageLine1() {
        return messageLine1;
    }
	
    public void setMessageLine1 (String messageLine1) {
        this.messageLine1 =  messageLine1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-effect_line1@
    public Integer getEffectLine1() {
        return effectLine1;
    }
	
    public void setEffectLine1 (Integer effectLine1) {
        this.effectLine1 =  effectLine1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-update_rate_line1@
    public Integer getUpdateRateLine1() {
        return updateRateLine1;
    }
	
    public void setUpdateRateLine1 (Integer updateRateLine1) {
        this.updateRateLine1 =  updateRateLine1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_and_time_line1@
    public Boolean getDateAndTimeLine1() {
        return dateAndTimeLine1;
    }
	
    public void setDateAndTimeLine1 (Boolean dateAndTimeLine1) {
        this.dateAndTimeLine1 =  dateAndTimeLine1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-scroll_change_size_line1@
    public Integer getScrollChangeSizeLine1() {
        return scrollChangeSizeLine1;
    }
	
    public void setScrollChangeSizeLine1 (Integer scrollChangeSizeLine1) {
        this.scrollChangeSizeLine1 =  scrollChangeSizeLine1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-message_line2@
    public String getMessageLine2() {
        return messageLine2;
    }
	
    public void setMessageLine2 (String messageLine2) {
        this.messageLine2 =  messageLine2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-effect_line2@
    public Integer getEffectLine2() {
        return effectLine2;
    }
	
    public void setEffectLine2 (Integer effectLine2) {
        this.effectLine2 =  effectLine2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-update_rate_line2@
    public Integer getUpdateRateLine2() {
        return updateRateLine2;
    }
	
    public void setUpdateRateLine2 (Integer updateRateLine2) {
        this.updateRateLine2 =  updateRateLine2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_and_time_line2@
    public Boolean getDateAndTimeLine2() {
        return dateAndTimeLine2;
    }
	
    public void setDateAndTimeLine2 (Boolean dateAndTimeLine2) {
        this.dateAndTimeLine2 =  dateAndTimeLine2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-scroll_change_size_line2@
    public Integer getScrollChangeSizeLine2() {
        return scrollChangeSizeLine2;
    }
	
    public void setScrollChangeSizeLine2 (Integer scrollChangeSizeLine2) {
        this.scrollChangeSizeLine2 =  scrollChangeSizeLine2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING



//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-getter-pole_display_message@
    public List<Station> getStations() {
        if (stations == null){
            stations = new ArrayList<>();
        }
        return stations;
    }

    public void setStations (List<Station> stations) {
        this.stations = stations;
    }	
    
    public void addStations (Station element) {
    	    getStations().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-pole_display_message@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-pole_display_message@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}
