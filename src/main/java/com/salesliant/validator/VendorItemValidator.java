package com.salesliant.validator;

import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorItem_;
import com.salesliant.util.BaseDao;

public class VendorItemValidator {

    protected final BaseDao<VendorItem> daoVendorItem = new BaseDao<>(VendorItem.class);

    public void validate(VendorItem item) throws InvalidException {
        if (item.getId() == null && daoVendorItem.exists(VendorItem_.vendor, item.getVendor(), VendorItem_.item, item.getItem())) {
            throw new InvalidException("The SKU exists");
        }
        if (item.getCost() == null) {
            throw new InvalidException("Please enter a cost for the item!");
        }
    }
}
