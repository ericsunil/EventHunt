package com.peyxen.eventilizer.Model;

/**
 * Created by Pci on 1/26/2016.
 */
public class Shops {
    String item, shop_name;
    int shop_id, price;

    public Shops(int shop_id, String shop_name, String item, int price) {
        this.item = item;
        this.price = price;
        this.shop_id = shop_id;
        this.shop_name = shop_name;
    }

    public String getItem() {
        return item;
    }

    public String getShop_name() {
        return shop_name;
    }

    public int getShop_id() {
        return shop_id;
    }

    public int getPrice() {
        return price;
    }
}
