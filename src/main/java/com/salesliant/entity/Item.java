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
 * <p>Title: Item</p>
 *
 * <p>Description: Domain Object describing a Item entity</p>
 *
 */
@Entity (name="Item")
@Table (name="item")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_PRICE1 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE2 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE3 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE4 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE5 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE6 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_COST = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_LAST_COST = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_LANDED_COST = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_AVERAGE_COST = java.math.BigDecimal.valueOf(1000000.0000);
	public static final Integer __DEFAULT_ITEM_TYPE = Integer.valueOf(0);
	public static final Integer __DEFAULT_DEFAULT_SELL_QUANTITY = Integer.valueOf(1);
	public static final Integer __DEFAULT_DEFAULT_SUPPLY_QUANTITY = Integer.valueOf(1);
	public static final Integer __DEFAULT_REORDER_POINT = Integer.valueOf(0);
	public static final Integer __DEFAULT_RESTOCK_LEVEL = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_TAG_ALONG_QUANTITY = java.math.BigDecimal.valueOf(1.0000);
	public static final Integer __DEFAULT_SERIAL_NUMBER_COUNT = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_SUGGESTED_RETAILED_PRICE = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_WEIGHT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);
	public static final Integer __DEFAULT_STORE = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @label_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @label_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-label_id@
    @Column(name="label_id"   , nullable=true , unique=true)
    private Integer label; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_look_up_code@
    @Column(name="item_look_up_code"  , length=32 , nullable=false , unique=true)
    private String itemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @department_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @department_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-department_name@
    @Column(name="department_name"  , length=128 , nullable=true , unique=false)
    private String departmentName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @category_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @category_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-category_name@
    @Column(name="category_name"  , length=128 , nullable=true , unique=false)
    private String categoryName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @barcode_format-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @barcode_format-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-barcode_format@
    @Column(name="barcode_format"  , length=16 , nullable=true , unique=false)
    private String barcodeFormat; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price1@
    @Column(name="price1"   , nullable=true , unique=false)
    private java.math.BigDecimal price1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price2@
    @Column(name="price2"   , nullable=true , unique=false)
    private java.math.BigDecimal price2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price3@
    @Column(name="price3"   , nullable=true , unique=false)
    private java.math.BigDecimal price3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price4@
    @Column(name="price4"   , nullable=true , unique=false)
    private java.math.BigDecimal price4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price5-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price5-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price5@
    @Column(name="price5"   , nullable=true , unique=false)
    private java.math.BigDecimal price5; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price6-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price6-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price6@
    @Column(name="price6"   , nullable=true , unique=false)
    private java.math.BigDecimal price6; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=true , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_cost@
    @Column(name="last_cost"   , nullable=true , unique=false)
    private java.math.BigDecimal lastCost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @landed_cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @landed_cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-landed_cost@
    @Column(name="landed_cost"   , nullable=true , unique=false)
    private java.math.BigDecimal landedCost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @average_cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @average_cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-average_cost@
    @Column(name="average_cost"   , nullable=true , unique=false)
    private java.math.BigDecimal averageCost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_type@
    @Column(name="item_type"   , nullable=true , unique=false)
    private Integer itemType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @default_sell_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @default_sell_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-default_sell_quantity@
    @Column(name="default_sell_quantity"   , nullable=true , unique=false)
    private Integer defaultSellQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @default_supply_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @default_supply_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-default_supply_quantity@
    @Column(name="default_supply_quantity"   , nullable=true , unique=false)
    private Integer defaultSupplyQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @reorder_point-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @reorder_point-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-reorder_point@
    @Column(name="reorder_point"   , nullable=true , unique=false)
    private Integer reorderPoint; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @restock_level-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @restock_level-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-restock_level@
    @Column(name="restock_level"   , nullable=true , unique=false)
    private Integer restockLevel; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @unit_of_measure-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @unit_of_measure-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-unit_of_measure@
    @Column(name="unit_of_measure"  , length=12 , nullable=true , unique=false)
    private String unitOfMeasure; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tag_along_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tag_along_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tag_along_quantity@
    @Column(name="tag_along_quantity"   , nullable=true , unique=false)
    private java.math.BigDecimal tagAlongQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @internet_item-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @internet_item-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-internet_item@
    @Column(name="internet_item"   , nullable=true , unique=false)
    private Boolean internetItem; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @food_stampable-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @food_stampable-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-food_stampable@
    @Column(name="food_stampable"   , nullable=true , unique=false)
    private Boolean foodStampable; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discountable-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discountable-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discountable@
    @Column(name="discountable"   , nullable=true , unique=false)
    private Boolean discountable; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @consignment-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @consignment-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-consignment@
    @Column(name="consignment"   , nullable=true , unique=false)
    private Boolean consignment; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @active_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @active_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-active_tag@
    @Column(name="active_tag"   , nullable=true , unique=false)
    private Boolean activeTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @fraction_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @fraction_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-fraction_tag@
    @Column(name="fraction_tag"   , nullable=true , unique=false)
    private Boolean fractionTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @track_serial_numumber-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @track_serial_numumber-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-track_serial_numumber@
    @Column(name="track_serial_numumber"   , nullable=true , unique=false)
    private Boolean trackSerialNumumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @track_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @track_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-track_quantity@
    @Column(name="track_quantity"   , nullable=true , unique=false)
    private Boolean trackQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @add_to_label_list-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @add_to_label_list-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-add_to_label_list@
    @Column(name="add_to_label_list"   , nullable=true , unique=false)
    private Boolean addToLabelList; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @serial_number_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @serial_number_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-serial_number_count@
    @Column(name="serial_number_count"   , nullable=true , unique=false)
    private Integer serialNumberCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @suggested_retailed_price-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @suggested_retailed_price-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-suggested_retailed_price@
    @Column(name="suggested_retailed_price"   , nullable=true , unique=false)
    private java.math.BigDecimal suggestedRetailedPrice; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @weight-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @weight-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-weight@
    @Column(name="weight"   , nullable=true , unique=false)
    private java.math.BigDecimal weight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @mpn-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @mpn-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-mpn@
    @Column(name="mpn"  , length=64 , nullable=true , unique=false)
    private String mpn; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_item_look_up_code@
    @Column(name="vendor_item_look_up_code"  , length=64 , nullable=true , unique=false)
    private String vendorItemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @picture_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @picture_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-picture_name@
    @Column(name="picture_name"  , length=128 , nullable=true , unique=false)
    private String pictureName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @store_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @store_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-store_id@
    @Column(name="store_id"   , nullable=false , unique=false)
    private Integer store; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="category_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Category category;  

    @Column(name="category_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer categoryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="department_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Department department;  

    @Column(name="department_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer departmentId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="primary_vendor_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Vendor primaryVendor;  

    @Column(name="primary_vendor_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer primaryVendorId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="quantity_discount_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private QuantityDiscount quantityDiscount;  

    @Column(name="quantity_discount_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer quantityDiscountId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="tag_along_item_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Item tagAlongItem;  

    @Column(name="tag_along_item_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer tagAlongItemId;

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @consignments-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Consignment.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <Consignment> consignments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @items-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Item.class, fetch=FetchType.LAZY, mappedBy="tagAlongItem", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Item> items = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemAttributes-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemAttribute.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemAttribute> itemAttributes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @itemBomBomItems-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemBom.class, fetch=FetchType.LAZY, mappedBy="bomItem", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ItemBom> itemBomBomItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemBomComponentItems-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemBom.class, fetch=FetchType.LAZY, mappedBy="componentItem", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemBom> itemBomComponentItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @itemLabels-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLabel.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ItemLabel> itemLabels = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @itemLimits-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLimit.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ItemLimit> itemLimits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @itemLocations-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLocation.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ItemLocation> itemLocations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @itemLogs-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLog.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ItemLog> itemLogs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @itemLots-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLot.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ItemLot> itemLots = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @itemQuantities-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ItemQuantity.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ItemQuantity> itemQuantities = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitComponentItems-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Kit.class, fetch=FetchType.LAZY, mappedBy="componentItem", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Kit> kitComponentItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitKitItems-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Kit.class, fetch=FetchType.LAZY, mappedBy="kitItem", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Kit> kitKitItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitEntries-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.KitEntry.class, fetch=FetchType.LAZY, mappedBy="componentItem", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <KitEntry> kitEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @promotions-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Promotion.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <Promotion> promotions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderEntries-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderEntry.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderEntry> purchaseOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistoryEntries-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderHistoryEntry.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @reorderLists-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ReorderList.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <ReorderList> reorderLists = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrderEntries-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnOrderEntry.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnOrderEntry> returnOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnTransaction.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnTransaction> returnTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrderEntries-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrderEntry.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SalesOrderEntry> salesOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.SerialNumber.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SerialNumber> serialNumbers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @substituteItems-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Substitute.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Substitute> substituteItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @substituteSubstituteItems-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Substitute.class, fetch=FetchType.LAZY, mappedBy="substituteItem", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Substitute> substituteSubstituteItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderEntries-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrderEntry.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrderEntry> transferOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistoryEntries-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrderHistoryEntry.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrderHistoryEntry> transferOrderHistoryEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @vendorItems-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.VendorItem.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <VendorItem> vendorItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @vouchers-field-item@
    @OneToMany (targetEntity=com.salesliant.entity.Voucher.class, fetch=FetchType.LAZY, mappedBy="item", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <Voucher> vouchers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Item() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-label_id@
    public Integer getLabel() {
        return label;
    }
	
    public void setLabel (Integer label) {
        this.label =  label;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_look_up_code@
    public String getItemLookUpCode() {
        return itemLookUpCode;
    }
	
    public void setItemLookUpCode (String itemLookUpCode) {
        this.itemLookUpCode =  itemLookUpCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-department_name@
    public String getDepartmentName() {
        return departmentName;
    }
	
    public void setDepartmentName (String departmentName) {
        this.departmentName =  departmentName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-category_name@
    public String getCategoryName() {
        return categoryName;
    }
	
    public void setCategoryName (String categoryName) {
        this.categoryName =  categoryName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-barcode_format@
    public String getBarcodeFormat() {
        return barcodeFormat;
    }
	
    public void setBarcodeFormat (String barcodeFormat) {
        this.barcodeFormat =  barcodeFormat;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price1@
    public java.math.BigDecimal getPrice1() {
        return price1;
    }
	
    public void setPrice1 (java.math.BigDecimal price1) {
        this.price1 =  price1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price2@
    public java.math.BigDecimal getPrice2() {
        return price2;
    }
	
    public void setPrice2 (java.math.BigDecimal price2) {
        this.price2 =  price2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price3@
    public java.math.BigDecimal getPrice3() {
        return price3;
    }
	
    public void setPrice3 (java.math.BigDecimal price3) {
        this.price3 =  price3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price4@
    public java.math.BigDecimal getPrice4() {
        return price4;
    }
	
    public void setPrice4 (java.math.BigDecimal price4) {
        this.price4 =  price4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price5@
    public java.math.BigDecimal getPrice5() {
        return price5;
    }
	
    public void setPrice5 (java.math.BigDecimal price5) {
        this.price5 =  price5;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price6@
    public java.math.BigDecimal getPrice6() {
        return price6;
    }
	
    public void setPrice6 (java.math.BigDecimal price6) {
        this.price6 =  price6;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cost@
    public java.math.BigDecimal getCost() {
        return cost;
    }
	
    public void setCost (java.math.BigDecimal cost) {
        this.cost =  cost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_cost@
    public java.math.BigDecimal getLastCost() {
        return lastCost;
    }
	
    public void setLastCost (java.math.BigDecimal lastCost) {
        this.lastCost =  lastCost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-landed_cost@
    public java.math.BigDecimal getLandedCost() {
        return landedCost;
    }
	
    public void setLandedCost (java.math.BigDecimal landedCost) {
        this.landedCost =  landedCost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-average_cost@
    public java.math.BigDecimal getAverageCost() {
        return averageCost;
    }
	
    public void setAverageCost (java.math.BigDecimal averageCost) {
        this.averageCost =  averageCost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_type@
    public Integer getItemType() {
        return itemType;
    }
	
    public void setItemType (Integer itemType) {
        this.itemType =  itemType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-default_sell_quantity@
    public Integer getDefaultSellQuantity() {
        return defaultSellQuantity;
    }
	
    public void setDefaultSellQuantity (Integer defaultSellQuantity) {
        this.defaultSellQuantity =  defaultSellQuantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-default_supply_quantity@
    public Integer getDefaultSupplyQuantity() {
        return defaultSupplyQuantity;
    }
	
    public void setDefaultSupplyQuantity (Integer defaultSupplyQuantity) {
        this.defaultSupplyQuantity =  defaultSupplyQuantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-reorder_point@
    public Integer getReorderPoint() {
        return reorderPoint;
    }
	
    public void setReorderPoint (Integer reorderPoint) {
        this.reorderPoint =  reorderPoint;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-restock_level@
    public Integer getRestockLevel() {
        return restockLevel;
    }
	
    public void setRestockLevel (Integer restockLevel) {
        this.restockLevel =  restockLevel;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-unit_of_measure@
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
	
    public void setUnitOfMeasure (String unitOfMeasure) {
        this.unitOfMeasure =  unitOfMeasure;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tag_along_quantity@
    public java.math.BigDecimal getTagAlongQuantity() {
        return tagAlongQuantity;
    }
	
    public void setTagAlongQuantity (java.math.BigDecimal tagAlongQuantity) {
        this.tagAlongQuantity =  tagAlongQuantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-note@
    public String getNote() {
        return note;
    }
	
    public void setNote (String note) {
        this.note =  note;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-internet_item@
    public Boolean getInternetItem() {
        return internetItem;
    }
	
    public void setInternetItem (Boolean internetItem) {
        this.internetItem =  internetItem;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-food_stampable@
    public Boolean getFoodStampable() {
        return foodStampable;
    }
	
    public void setFoodStampable (Boolean foodStampable) {
        this.foodStampable =  foodStampable;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discountable@
    public Boolean getDiscountable() {
        return discountable;
    }
	
    public void setDiscountable (Boolean discountable) {
        this.discountable =  discountable;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-consignment@
    public Boolean getConsignment() {
        return consignment;
    }
	
    public void setConsignment (Boolean consignment) {
        this.consignment =  consignment;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-active_tag@
    public Boolean getActiveTag() {
        return activeTag;
    }
	
    public void setActiveTag (Boolean activeTag) {
        this.activeTag =  activeTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-fraction_tag@
    public Boolean getFractionTag() {
        return fractionTag;
    }
	
    public void setFractionTag (Boolean fractionTag) {
        this.fractionTag =  fractionTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-track_serial_numumber@
    public Boolean getTrackSerialNumumber() {
        return trackSerialNumumber;
    }
	
    public void setTrackSerialNumumber (Boolean trackSerialNumumber) {
        this.trackSerialNumumber =  trackSerialNumumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-track_quantity@
    public Boolean getTrackQuantity() {
        return trackQuantity;
    }
	
    public void setTrackQuantity (Boolean trackQuantity) {
        this.trackQuantity =  trackQuantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-add_to_label_list@
    public Boolean getAddToLabelList() {
        return addToLabelList;
    }
	
    public void setAddToLabelList (Boolean addToLabelList) {
        this.addToLabelList =  addToLabelList;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-serial_number_count@
    public Integer getSerialNumberCount() {
        return serialNumberCount;
    }
	
    public void setSerialNumberCount (Integer serialNumberCount) {
        this.serialNumberCount =  serialNumberCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-suggested_retailed_price@
    public java.math.BigDecimal getSuggestedRetailedPrice() {
        return suggestedRetailedPrice;
    }
	
    public void setSuggestedRetailedPrice (java.math.BigDecimal suggestedRetailedPrice) {
        this.suggestedRetailedPrice =  suggestedRetailedPrice;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-weight@
    public java.math.BigDecimal getWeight() {
        return weight;
    }
	
    public void setWeight (java.math.BigDecimal weight) {
        this.weight =  weight;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-mpn@
    public String getMpn() {
        return mpn;
    }
	
    public void setMpn (String mpn) {
        this.mpn =  mpn;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_item_look_up_code@
    public String getVendorItemLookUpCode() {
        return vendorItemLookUpCode;
    }
	
    public void setVendorItemLookUpCode (String vendorItemLookUpCode) {
        this.vendorItemLookUpCode =  vendorItemLookUpCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-picture_name@
    public String getPictureName() {
        return pictureName;
    }
	
    public void setPictureName (String pictureName) {
        this.pictureName =  pictureName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-store_id@
    public Integer getStore() {
        return store;
    }
	
    public void setStore (Integer store) {
        this.store =  store;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public Category getCategory () {
    	return category;
    }
	
    public void setCategory (Category category) {
    	this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
	
    public void setCategoryId (Integer category) {
        this.categoryId =  category;
    }
	
    public Department getDepartment () {
    	return department;
    }
	
    public void setDepartment (Department department) {
    	this.department = department;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }
	
    public void setDepartmentId (Integer department) {
        this.departmentId =  department;
    }
	
    public Vendor getPrimaryVendor () {
    	return primaryVendor;
    }
	
    public void setPrimaryVendor (Vendor primaryVendor) {
    	this.primaryVendor = primaryVendor;
    }

    public Integer getPrimaryVendorId() {
        return primaryVendorId;
    }
	
    public void setPrimaryVendorId (Integer primaryVendor) {
        this.primaryVendorId =  primaryVendor;
    }
	
    public QuantityDiscount getQuantityDiscount () {
    	return quantityDiscount;
    }
	
    public void setQuantityDiscount (QuantityDiscount quantityDiscount) {
    	this.quantityDiscount = quantityDiscount;
    }

    public Integer getQuantityDiscountId() {
        return quantityDiscountId;
    }
	
    public void setQuantityDiscountId (Integer quantityDiscount) {
        this.quantityDiscountId =  quantityDiscount;
    }
	
    public Item getTagAlongItem () {
    	return tagAlongItem;
    }
	
    public void setTagAlongItem (Item tagAlongItem) {
    	this.tagAlongItem = tagAlongItem;
    }

    public Integer getTagAlongItemId() {
        return tagAlongItemId;
    }
	
    public void setTagAlongItemId (Integer tagAlongItem) {
        this.tagAlongItemId =  tagAlongItem;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @consignments-getter-item@
    public List<Consignment> getConsignments() {
        if (consignments == null){
            consignments = new ArrayList<>();
        }
        return consignments;
    }

    public void setConsignments (List<Consignment> consignments) {
        this.consignments = consignments;
    }	
    
    public void addConsignments (Consignment element) {
    	    getConsignments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @items-getter-item@
    public List<Item> getItems() {
        if (items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems (List<Item> items) {
        this.items = items;
    }	
    
    public void addItems (Item element) {
    	    getItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemAttributes-getter-item@
    public List<ItemAttribute> getItemAttributes() {
        if (itemAttributes == null){
            itemAttributes = new ArrayList<>();
        }
        return itemAttributes;
    }

    public void setItemAttributes (List<ItemAttribute> itemAttributes) {
        this.itemAttributes = itemAttributes;
    }	
    
    public void addItemAttributes (ItemAttribute element) {
    	    getItemAttributes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemBomBomItems-getter-item@
    public List<ItemBom> getItemBomBomItems() {
        if (itemBomBomItems == null){
            itemBomBomItems = new ArrayList<>();
        }
        return itemBomBomItems;
    }

    public void setItemBomBomItems (List<ItemBom> itemBomBomItems) {
        this.itemBomBomItems = itemBomBomItems;
    }	
    
    public void addItemBomBomItems (ItemBom element) {
    	    getItemBomBomItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemBomComponentItems-getter-item@
    public List<ItemBom> getItemBomComponentItems() {
        if (itemBomComponentItems == null){
            itemBomComponentItems = new ArrayList<>();
        }
        return itemBomComponentItems;
    }

    public void setItemBomComponentItems (List<ItemBom> itemBomComponentItems) {
        this.itemBomComponentItems = itemBomComponentItems;
    }	
    
    public void addItemBomComponentItems (ItemBom element) {
    	    getItemBomComponentItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLabels-getter-item@
    public List<ItemLabel> getItemLabels() {
        if (itemLabels == null){
            itemLabels = new ArrayList<>();
        }
        return itemLabels;
    }

    public void setItemLabels (List<ItemLabel> itemLabels) {
        this.itemLabels = itemLabels;
    }	
    
    public void addItemLabels (ItemLabel element) {
    	    getItemLabels().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLimits-getter-item@
    public List<ItemLimit> getItemLimits() {
        if (itemLimits == null){
            itemLimits = new ArrayList<>();
        }
        return itemLimits;
    }

    public void setItemLimits (List<ItemLimit> itemLimits) {
        this.itemLimits = itemLimits;
    }	
    
    public void addItemLimits (ItemLimit element) {
    	    getItemLimits().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLocations-getter-item@
    public List<ItemLocation> getItemLocations() {
        if (itemLocations == null){
            itemLocations = new ArrayList<>();
        }
        return itemLocations;
    }

    public void setItemLocations (List<ItemLocation> itemLocations) {
        this.itemLocations = itemLocations;
    }	
    
    public void addItemLocations (ItemLocation element) {
    	    getItemLocations().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-getter-item@
    public List<ItemLog> getItemLogs() {
        if (itemLogs == null){
            itemLogs = new ArrayList<>();
        }
        return itemLogs;
    }

    public void setItemLogs (List<ItemLog> itemLogs) {
        this.itemLogs = itemLogs;
    }	
    
    public void addItemLogs (ItemLog element) {
    	    getItemLogs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLots-getter-item@
    public List<ItemLot> getItemLots() {
        if (itemLots == null){
            itemLots = new ArrayList<>();
        }
        return itemLots;
    }

    public void setItemLots (List<ItemLot> itemLots) {
        this.itemLots = itemLots;
    }	
    
    public void addItemLots (ItemLot element) {
    	    getItemLots().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemQuantities-getter-item@
    public List<ItemQuantity> getItemQuantities() {
        if (itemQuantities == null){
            itemQuantities = new ArrayList<>();
        }
        return itemQuantities;
    }

    public void setItemQuantities (List<ItemQuantity> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }	
    
    public void addItemQuantities (ItemQuantity element) {
    	    getItemQuantities().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitComponentItems-getter-item@
    public List<Kit> getKitComponentItems() {
        if (kitComponentItems == null){
            kitComponentItems = new ArrayList<>();
        }
        return kitComponentItems;
    }

    public void setKitComponentItems (List<Kit> kitComponentItems) {
        this.kitComponentItems = kitComponentItems;
    }	
    
    public void addKitComponentItems (Kit element) {
    	    getKitComponentItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitKitItems-getter-item@
    public List<Kit> getKitKitItems() {
        if (kitKitItems == null){
            kitKitItems = new ArrayList<>();
        }
        return kitKitItems;
    }

    public void setKitKitItems (List<Kit> kitKitItems) {
        this.kitKitItems = kitKitItems;
    }	
    
    public void addKitKitItems (Kit element) {
    	    getKitKitItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitEntries-getter-item@
    public List<KitEntry> getKitEntries() {
        if (kitEntries == null){
            kitEntries = new ArrayList<>();
        }
        return kitEntries;
    }

    public void setKitEntries (List<KitEntry> kitEntries) {
        this.kitEntries = kitEntries;
    }	
    
    public void addKitEntries (KitEntry element) {
    	    getKitEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @promotions-getter-item@
    public List<Promotion> getPromotions() {
        if (promotions == null){
            promotions = new ArrayList<>();
        }
        return promotions;
    }

    public void setPromotions (List<Promotion> promotions) {
        this.promotions = promotions;
    }	
    
    public void addPromotions (Promotion element) {
    	    getPromotions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderEntries-getter-item@
    public List<PurchaseOrderEntry> getPurchaseOrderEntries() {
        if (purchaseOrderEntries == null){
            purchaseOrderEntries = new ArrayList<>();
        }
        return purchaseOrderEntries;
    }

    public void setPurchaseOrderEntries (List<PurchaseOrderEntry> purchaseOrderEntries) {
        this.purchaseOrderEntries = purchaseOrderEntries;
    }	
    
    public void addPurchaseOrderEntries (PurchaseOrderEntry element) {
    	    getPurchaseOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistoryEntries-getter-item@
    public List<PurchaseOrderHistoryEntry> getPurchaseOrderHistoryEntries() {
        if (purchaseOrderHistoryEntries == null){
            purchaseOrderHistoryEntries = new ArrayList<>();
        }
        return purchaseOrderHistoryEntries;
    }

    public void setPurchaseOrderHistoryEntries (List<PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries) {
        this.purchaseOrderHistoryEntries = purchaseOrderHistoryEntries;
    }	
    
    public void addPurchaseOrderHistoryEntries (PurchaseOrderHistoryEntry element) {
    	    getPurchaseOrderHistoryEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @reorderLists-getter-item@
    public List<ReorderList> getReorderLists() {
        if (reorderLists == null){
            reorderLists = new ArrayList<>();
        }
        return reorderLists;
    }

    public void setReorderLists (List<ReorderList> reorderLists) {
        this.reorderLists = reorderLists;
    }	
    
    public void addReorderLists (ReorderList element) {
    	    getReorderLists().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrderEntries-getter-item@
    public List<ReturnOrderEntry> getReturnOrderEntries() {
        if (returnOrderEntries == null){
            returnOrderEntries = new ArrayList<>();
        }
        return returnOrderEntries;
    }

    public void setReturnOrderEntries (List<ReturnOrderEntry> returnOrderEntries) {
        this.returnOrderEntries = returnOrderEntries;
    }	
    
    public void addReturnOrderEntries (ReturnOrderEntry element) {
    	    getReturnOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-getter-item@
    public List<ReturnTransaction> getReturnTransactions() {
        if (returnTransactions == null){
            returnTransactions = new ArrayList<>();
        }
        return returnTransactions;
    }

    public void setReturnTransactions (List<ReturnTransaction> returnTransactions) {
        this.returnTransactions = returnTransactions;
    }	
    
    public void addReturnTransactions (ReturnTransaction element) {
    	    getReturnTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrderEntries-getter-item@
    public List<SalesOrderEntry> getSalesOrderEntries() {
        if (salesOrderEntries == null){
            salesOrderEntries = new ArrayList<>();
        }
        return salesOrderEntries;
    }

    public void setSalesOrderEntries (List<SalesOrderEntry> salesOrderEntries) {
        this.salesOrderEntries = salesOrderEntries;
    }	
    
    public void addSalesOrderEntries (SalesOrderEntry element) {
    	    getSalesOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-getter-item@
    public List<SerialNumber> getSerialNumbers() {
        if (serialNumbers == null){
            serialNumbers = new ArrayList<>();
        }
        return serialNumbers;
    }

    public void setSerialNumbers (List<SerialNumber> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }	
    
    public void addSerialNumbers (SerialNumber element) {
    	    getSerialNumbers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @substituteItems-getter-item@
    public List<Substitute> getSubstituteItems() {
        if (substituteItems == null){
            substituteItems = new ArrayList<>();
        }
        return substituteItems;
    }

    public void setSubstituteItems (List<Substitute> substituteItems) {
        this.substituteItems = substituteItems;
    }	
    
    public void addSubstituteItems (Substitute element) {
    	    getSubstituteItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @substituteSubstituteItems-getter-item@
    public List<Substitute> getSubstituteSubstituteItems() {
        if (substituteSubstituteItems == null){
            substituteSubstituteItems = new ArrayList<>();
        }
        return substituteSubstituteItems;
    }

    public void setSubstituteSubstituteItems (List<Substitute> substituteSubstituteItems) {
        this.substituteSubstituteItems = substituteSubstituteItems;
    }	
    
    public void addSubstituteSubstituteItems (Substitute element) {
    	    getSubstituteSubstituteItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderEntries-getter-item@
    public List<TransferOrderEntry> getTransferOrderEntries() {
        if (transferOrderEntries == null){
            transferOrderEntries = new ArrayList<>();
        }
        return transferOrderEntries;
    }

    public void setTransferOrderEntries (List<TransferOrderEntry> transferOrderEntries) {
        this.transferOrderEntries = transferOrderEntries;
    }	
    
    public void addTransferOrderEntries (TransferOrderEntry element) {
    	    getTransferOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistoryEntries-getter-item@
    public List<TransferOrderHistoryEntry> getTransferOrderHistoryEntries() {
        if (transferOrderHistoryEntries == null){
            transferOrderHistoryEntries = new ArrayList<>();
        }
        return transferOrderHistoryEntries;
    }

    public void setTransferOrderHistoryEntries (List<TransferOrderHistoryEntry> transferOrderHistoryEntries) {
        this.transferOrderHistoryEntries = transferOrderHistoryEntries;
    }	
    
    public void addTransferOrderHistoryEntries (TransferOrderHistoryEntry element) {
    	    getTransferOrderHistoryEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorItems-getter-item@
    public List<VendorItem> getVendorItems() {
        if (vendorItems == null){
            vendorItems = new ArrayList<>();
        }
        return vendorItems;
    }

    public void setVendorItems (List<VendorItem> vendorItems) {
        this.vendorItems = vendorItems;
    }	
    
    public void addVendorItems (VendorItem element) {
    	    getVendorItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vouchers-getter-item@
    public List<Voucher> getVouchers() {
        if (vouchers == null){
            vouchers = new ArrayList<>();
        }
        return vouchers;
    }

    public void setVouchers (List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }	
    
    public void addVouchers (Voucher element) {
    	    getVouchers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-item@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (price1==null) price1=__DEFAULT_PRICE1;
        if (price2==null) price2=__DEFAULT_PRICE2;
        if (price3==null) price3=__DEFAULT_PRICE3;
        if (price4==null) price4=__DEFAULT_PRICE4;
        if (price5==null) price5=__DEFAULT_PRICE5;
        if (price6==null) price6=__DEFAULT_PRICE6;
        if (cost==null) cost=__DEFAULT_COST;
        if (lastCost==null) lastCost=__DEFAULT_LAST_COST;
        if (landedCost==null) landedCost=__DEFAULT_LANDED_COST;
        if (averageCost==null) averageCost=__DEFAULT_AVERAGE_COST;
        if (itemType==null) itemType=__DEFAULT_ITEM_TYPE;
        if (defaultSellQuantity==null) defaultSellQuantity=__DEFAULT_DEFAULT_SELL_QUANTITY;
        if (defaultSupplyQuantity==null) defaultSupplyQuantity=__DEFAULT_DEFAULT_SUPPLY_QUANTITY;
        if (reorderPoint==null) reorderPoint=__DEFAULT_REORDER_POINT;
        if (restockLevel==null) restockLevel=__DEFAULT_RESTOCK_LEVEL;
        if (tagAlongQuantity==null) tagAlongQuantity=__DEFAULT_TAG_ALONG_QUANTITY;
        if (serialNumberCount==null) serialNumberCount=__DEFAULT_SERIAL_NUMBER_COUNT;
        if (suggestedRetailedPrice==null) suggestedRetailedPrice=__DEFAULT_SUGGESTED_RETAILED_PRICE;
        if (weight==null) weight=__DEFAULT_WEIGHT;
        if (version==null) version=__DEFAULT_VERSION;
        if (store==null) store=__DEFAULT_STORE;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-item@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (price1==null) price1=__DEFAULT_PRICE1;
        if (price2==null) price2=__DEFAULT_PRICE2;
        if (price3==null) price3=__DEFAULT_PRICE3;
        if (price4==null) price4=__DEFAULT_PRICE4;
        if (price5==null) price5=__DEFAULT_PRICE5;
        if (price6==null) price6=__DEFAULT_PRICE6;
        if (cost==null) cost=__DEFAULT_COST;
        if (lastCost==null) lastCost=__DEFAULT_LAST_COST;
        if (landedCost==null) landedCost=__DEFAULT_LANDED_COST;
        if (averageCost==null) averageCost=__DEFAULT_AVERAGE_COST;
        if (itemType==null) itemType=__DEFAULT_ITEM_TYPE;
        if (defaultSellQuantity==null) defaultSellQuantity=__DEFAULT_DEFAULT_SELL_QUANTITY;
        if (defaultSupplyQuantity==null) defaultSupplyQuantity=__DEFAULT_DEFAULT_SUPPLY_QUANTITY;
        if (reorderPoint==null) reorderPoint=__DEFAULT_REORDER_POINT;
        if (restockLevel==null) restockLevel=__DEFAULT_RESTOCK_LEVEL;
        if (tagAlongQuantity==null) tagAlongQuantity=__DEFAULT_TAG_ALONG_QUANTITY;
        if (serialNumberCount==null) serialNumberCount=__DEFAULT_SERIAL_NUMBER_COUNT;
        if (suggestedRetailedPrice==null) suggestedRetailedPrice=__DEFAULT_SUGGESTED_RETAILED_PRICE;
        if (weight==null) weight=__DEFAULT_WEIGHT;
        if (version==null) version=__DEFAULT_VERSION;
        if (store==null) store=__DEFAULT_STORE;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}
