package com.salesliant.validator;

import com.salesliant.entity.Store;

public class StoreValidator {

    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 25;
    private static final int MAX_LENGTH_NAME = 50;

    public void validate(Store store) throws InvalidException {
        String storeCode = store.getStoreCode();
        String storeName = store.getStoreName();
        String city = store.getCity();
        String state = store.getState();
        String zip = store.getPostCode();
        if (storeCode != null && !storeCode.isEmpty()) {
            if ((storeCode.length() < MIN_LENGTH) || (storeCode.length() > MAX_LENGTH)) {
                throw new InvalidException("Store Code must be 3 to 25 characters long!");
            }
        } else {
            throw new InvalidException("Store Code cann't be empty!");
        }
        if (storeName != null && !storeName.isEmpty()) {
            if ((storeName.length() < MIN_LENGTH) || (storeName.length() > MAX_LENGTH_NAME)) {
                throw new InvalidException("Store must be 3 to 50 characters long!");
            }

        } else {
            throw new InvalidException("Store cann't be empty!");
        }
        if (!nameHasValidCharacters(storeCode)) {
            throw new InvalidException("Store Code can only have (a-z A-Z 0-9 space - and _) characters!");
        }
        if (city == null || city.isEmpty()) {
            throw new InvalidException("City cann't be empty!");
        }
        if (state == null || state.isEmpty()) {
            throw new InvalidException("State cann't be empty!");
        }
        if (zip == null || zip.isEmpty()) {
            throw new InvalidException("Zip cann't be empty!");
        }
    }

    private boolean nameHasValidCharacters(String input) {
        char[] cc = input.toCharArray();
        for (char c : cc) {
            if ((Character.isLetterOrDigit(c)) || (Character.isSpaceChar(c)) || (c == '_') || (c == '-')) {
            } else {
                return false;
            }
        }
        return true;
    }
}
