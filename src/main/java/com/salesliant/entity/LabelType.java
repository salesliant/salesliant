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
 * <p>Title: LabelType</p>
 *
 * <p>Description: Domain Object describing a LabelType entity</p>
 *
 */
@Entity (name="LabelType")
@Table (name="label_type")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class LabelType implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_MAXIMUM_ROWS = Integer.valueOf(1);
	public static final Integer __DEFAULT_MAXIMUM_COLUMNS = Integer.valueOf(1);
	public static final Integer __DEFAULT_TOP_MARGIN = Integer.valueOf(1);
	public static final Integer __DEFAULT_BOTTOM_MARGIN = Integer.valueOf(1);
	public static final Integer __DEFAULT_HORIZONTAL_SPACE = Integer.valueOf(1);
	public static final Integer __DEFAULT_VERTICAL_SPACE = Integer.valueOf(1);
	public static final Integer __DEFAULT_LABEL_WIDTH = Integer.valueOf(1);
	public static final Integer __DEFAULT_LABEL_HEIGHT = Integer.valueOf(1);
	public static final Integer __DEFAULT_MAX_LENGTH = Integer.valueOf(1);
	public static final Integer __DEFAULT_BARCODE_WIDTH = Integer.valueOf(1);
	public static final Integer __DEFAULT_BARCODE_HEIGHT = Integer.valueOf(1);
	public static final Integer __DEFAULT_LEFT_MARGIN = Integer.valueOf(1);
	public static final Integer __DEFAULT_RIGHT_MARGIN = Integer.valueOf(1);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name@
    @Column(name="name"  , length=128 , nullable=false , unique=false)
    private String name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @maximum_rows-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @maximum_rows-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-maximum_rows@
    @Column(name="maximum_rows"   , nullable=false , unique=false)
    private Integer maximumRows; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @maximum_columns-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @maximum_columns-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-maximum_columns@
    @Column(name="maximum_columns"   , nullable=false , unique=false)
    private Integer maximumColumns; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @top_margin-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @top_margin-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-top_margin@
    @Column(name="top_margin"   , nullable=false , unique=false)
    private Integer topMargin; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bottom_margin-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bottom_margin-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bottom_margin@
    @Column(name="bottom_margin"   , nullable=false , unique=false)
    private Integer bottomMargin; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @horizontal_space-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @horizontal_space-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-horizontal_space@
    @Column(name="horizontal_space"   , nullable=false , unique=false)
    private Integer horizontalSpace; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vertical_space-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vertical_space-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vertical_space@
    @Column(name="vertical_space"   , nullable=false , unique=false)
    private Integer verticalSpace; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @label_width-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @label_width-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-label_width@
    @Column(name="label_width"   , nullable=false , unique=false)
    private Integer labelWidth; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @label_height-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @label_height-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-label_height@
    @Column(name="label_height"   , nullable=false , unique=false)
    private Integer labelHeight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @max_length-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @max_length-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-max_length@
    @Column(name="max_length"   , nullable=false , unique=false)
    private Integer maxLength; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @barcode_width-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @barcode_width-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-barcode_width@
    @Column(name="barcode_width"   , nullable=false , unique=false)
    private Integer barcodeWidth; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @barcode_height-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @barcode_height-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-barcode_height@
    @Column(name="barcode_height"   , nullable=false , unique=false)
    private Integer barcodeHeight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @page_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @page_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-page_type@
    @Column(name="page_type"  , length=32 , nullable=false , unique=false)
    private String pageType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @left_margin-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @left_margin-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-left_margin@
    @Column(name="left_margin"   , nullable=false , unique=false)
    private Integer leftMargin; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @right_margin-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @right_margin-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-right_margin@
    @Column(name="right_margin"   , nullable=false , unique=false)
    private Integer rightMargin; 
//MP-MANAGED-UPDATABLE-ENDING

    /**
    * Default constructor
    */
    public LabelType() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-name@
    public String getName() {
        return name;
    }
	
    public void setName (String name) {
        this.name =  name;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-maximum_rows@
    public Integer getMaximumRows() {
        return maximumRows;
    }
	
    public void setMaximumRows (Integer maximumRows) {
        this.maximumRows =  maximumRows;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-maximum_columns@
    public Integer getMaximumColumns() {
        return maximumColumns;
    }
	
    public void setMaximumColumns (Integer maximumColumns) {
        this.maximumColumns =  maximumColumns;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-top_margin@
    public Integer getTopMargin() {
        return topMargin;
    }
	
    public void setTopMargin (Integer topMargin) {
        this.topMargin =  topMargin;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bottom_margin@
    public Integer getBottomMargin() {
        return bottomMargin;
    }
	
    public void setBottomMargin (Integer bottomMargin) {
        this.bottomMargin =  bottomMargin;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-horizontal_space@
    public Integer getHorizontalSpace() {
        return horizontalSpace;
    }
	
    public void setHorizontalSpace (Integer horizontalSpace) {
        this.horizontalSpace =  horizontalSpace;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vertical_space@
    public Integer getVerticalSpace() {
        return verticalSpace;
    }
	
    public void setVerticalSpace (Integer verticalSpace) {
        this.verticalSpace =  verticalSpace;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-label_width@
    public Integer getLabelWidth() {
        return labelWidth;
    }
	
    public void setLabelWidth (Integer labelWidth) {
        this.labelWidth =  labelWidth;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-label_height@
    public Integer getLabelHeight() {
        return labelHeight;
    }
	
    public void setLabelHeight (Integer labelHeight) {
        this.labelHeight =  labelHeight;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-max_length@
    public Integer getMaxLength() {
        return maxLength;
    }
	
    public void setMaxLength (Integer maxLength) {
        this.maxLength =  maxLength;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-barcode_width@
    public Integer getBarcodeWidth() {
        return barcodeWidth;
    }
	
    public void setBarcodeWidth (Integer barcodeWidth) {
        this.barcodeWidth =  barcodeWidth;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-barcode_height@
    public Integer getBarcodeHeight() {
        return barcodeHeight;
    }
	
    public void setBarcodeHeight (Integer barcodeHeight) {
        this.barcodeHeight =  barcodeHeight;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-page_type@
    public String getPageType() {
        return pageType;
    }
	
    public void setPageType (String pageType) {
        this.pageType =  pageType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-left_margin@
    public Integer getLeftMargin() {
        return leftMargin;
    }
	
    public void setLeftMargin (Integer leftMargin) {
        this.leftMargin =  leftMargin;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-right_margin@
    public Integer getRightMargin() {
        return rightMargin;
    }
	
    public void setRightMargin (Integer rightMargin) {
        this.rightMargin =  rightMargin;
    }
	
//MP-MANAGED-UPDATABLE-ENDING




//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-label_type@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (maximumRows==null) maximumRows=__DEFAULT_MAXIMUM_ROWS;
        if (maximumColumns==null) maximumColumns=__DEFAULT_MAXIMUM_COLUMNS;
        if (topMargin==null) topMargin=__DEFAULT_TOP_MARGIN;
        if (bottomMargin==null) bottomMargin=__DEFAULT_BOTTOM_MARGIN;
        if (horizontalSpace==null) horizontalSpace=__DEFAULT_HORIZONTAL_SPACE;
        if (verticalSpace==null) verticalSpace=__DEFAULT_VERTICAL_SPACE;
        if (labelWidth==null) labelWidth=__DEFAULT_LABEL_WIDTH;
        if (labelHeight==null) labelHeight=__DEFAULT_LABEL_HEIGHT;
        if (maxLength==null) maxLength=__DEFAULT_MAX_LENGTH;
        if (barcodeWidth==null) barcodeWidth=__DEFAULT_BARCODE_WIDTH;
        if (barcodeHeight==null) barcodeHeight=__DEFAULT_BARCODE_HEIGHT;
        if (leftMargin==null) leftMargin=__DEFAULT_LEFT_MARGIN;
        if (rightMargin==null) rightMargin=__DEFAULT_RIGHT_MARGIN;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-label_type@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (maximumRows==null) maximumRows=__DEFAULT_MAXIMUM_ROWS;
        if (maximumColumns==null) maximumColumns=__DEFAULT_MAXIMUM_COLUMNS;
        if (topMargin==null) topMargin=__DEFAULT_TOP_MARGIN;
        if (bottomMargin==null) bottomMargin=__DEFAULT_BOTTOM_MARGIN;
        if (horizontalSpace==null) horizontalSpace=__DEFAULT_HORIZONTAL_SPACE;
        if (verticalSpace==null) verticalSpace=__DEFAULT_VERTICAL_SPACE;
        if (labelWidth==null) labelWidth=__DEFAULT_LABEL_WIDTH;
        if (labelHeight==null) labelHeight=__DEFAULT_LABEL_HEIGHT;
        if (maxLength==null) maxLength=__DEFAULT_MAX_LENGTH;
        if (barcodeWidth==null) barcodeWidth=__DEFAULT_BARCODE_WIDTH;
        if (barcodeHeight==null) barcodeHeight=__DEFAULT_BARCODE_HEIGHT;
        if (leftMargin==null) leftMargin=__DEFAULT_LEFT_MARGIN;
        if (rightMargin==null) rightMargin=__DEFAULT_RIGHT_MARGIN;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}
