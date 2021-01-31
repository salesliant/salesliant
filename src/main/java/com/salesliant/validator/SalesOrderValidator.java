package com.salesliant.validator;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.Tax;
import static com.salesliant.util.BaseListUI.getQuantity;
import static com.salesliant.util.BaseListUI.getQuantityCommitted;
import static com.salesliant.util.BaseListUI.getTax;
import java.math.BigDecimal;
import java.util.List;

public class SalesOrderValidator {

    public void validate(SalesOrder salesOrder) throws InvalidException {
        List<SalesOrderEntry> list = salesOrder.getSalesOrderEntries();
        for (SalesOrderEntry e : list) {
            Item item = e.getItem();
            if (item.getTrackQuantity()) {
                BigDecimal stock = getQuantity(item);
                BigDecimal committed = getQuantityCommitted(item);
                if (!Config.getStore().getAllowZeroQtySale() && stock.compareTo(BigDecimal.ZERO) == 0 && e.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    throw new InvalidException("Can't sell none stock item! SKU: " + item.getItemLookUpCode());
                }
                if (Config.getStore().getEnableBackOrders() && stock.compareTo(committed) <= 0 && e.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    throw new InvalidException("Item quntity has been allocated to other orders, not enough quantity to sell! SKU: " + item.getItemLookUpCode());
                }
            }
            if (item.getCategory().getCountTag()) {
                BigDecimal stock = getQuantity(item);
                BigDecimal committed = getQuantityCommitted(item);
                if (!Config.getStore().getAllowZeroQtySale() && stock.compareTo(BigDecimal.ZERO) == 0 && e.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    throw new InvalidException("Can't sell none stock item! SKU: " + item.getItemLookUpCode());
                }
                if (Config.getStore().getEnableBackOrders() && stock.compareTo(committed) <= 0 && e.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                    throw new InvalidException("Item quntity has been allocated to other orders, not enough quantity to sell! SKU: " + item.getItemLookUpCode());
                }
            }
            Tax tax = getTax(item, salesOrder.getTaxZone());
            if (tax != null && tax.getTaxRate().getRate().compareTo(BigDecimal.ZERO) != 0) {
                if (e.getCost() == null || e.getCost().compareTo(BigDecimal.ZERO) == 0) {
                    throw new InvalidException("Please enter cost for item with SKU: " + item.getItemLookUpCode());
                }
            } else {
                if (item.getCategory().getIsShippingTag() != null && item.getCategory().getIsShippingTag()) {
                    if (e.getCost() == null || e.getCost().compareTo(BigDecimal.ZERO) == 0) {
                        throw new InvalidException("Please enter cost for item with SKU: " + item.getItemLookUpCode());
                    }
                }
            }
        }
    }
}
