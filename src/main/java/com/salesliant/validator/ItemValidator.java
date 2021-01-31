package com.salesliant.validator;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.Item_;
import com.salesliant.util.BaseDao;
import java.util.List;

public class ItemValidator {

    protected final BaseDao<Item> daoItem = new BaseDao<>(Item.class);

    public void validate(Item item) throws InvalidException {
        if (item.getId() == null && daoItem.exists(Item_.itemLookUpCode, item.getItemLookUpCode())) {
            throw new InvalidException("The SKU exists");
        }
        if (item.getId() != null && daoItem.exists(Item_.itemLookUpCode, item.getItemLookUpCode())) {
            Item foundItem = daoItem.find(Item_.itemLookUpCode, item.getItemLookUpCode());
            if (!item.getId().equals(foundItem.getId())) {
                throw new InvalidException("The SKU exists");
            }
        }
        if (item.getCategory() == null) {
            throw new InvalidException("Please assign a category to the item!");
        }
        if (item.getCost() == null) {
            throw new InvalidException("Please enter a cost for the item!");
        }
        List<ItemPriceLevel> list = Config.getItemPriceLevel();
        for (int i = 0; i < list.size(); i++) {
            if (i == 0 && item.getPrice1() == null) {
                throw new InvalidException("Please enter item's " + list.get(i).getDescription() + "!");
            }
            if (i == 1 && item.getPrice2() == null) {
                throw new InvalidException("Please enter item's " + list.get(i).getDescription() + "!");
            }
            if (i == 2 && item.getPrice3() == null) {
                throw new InvalidException("Please enter item's " + list.get(i).getDescription() + "!");
            }
            if (i == 3 && item.getPrice4() == null) {
                throw new InvalidException("Please enter item's " + list.get(i).getDescription() + "!");
            }
            if (i == 4 && item.getPrice5() == null) {
                throw new InvalidException("Please enter item's " + list.get(i).getDescription() + "!");
            }
            if (i == 5 && item.getPrice6() == null) {
                throw new InvalidException("Please enter item's " + list.get(i).getDescription() + "!");
            }
        }
    }
}
