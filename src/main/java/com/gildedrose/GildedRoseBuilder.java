package com.gildedrose;

import java.util.ArrayList;

class GildedRoseBuilder {

    private ArrayList<Item> items = new ArrayList<>();

    public GildedRoseBuilder withItem(Item item, int sellIn, int quality) {
        item.sellIn = sellIn;
        item.quality = quality;
        return withItem(item);
    }

    public GildedRose build() {
        return new GildedRose(items.toArray(new Item[]{}));
    }

    public GildedRoseBuilder withItem(Item item) {
        items.add(item);
        return this;
    }
}
