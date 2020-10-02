package com.gildedrose;

import java.util.ArrayList;

class GildedRoseBuilder {

    private ArrayList<Item> items = new ArrayList<>();

    public GildedRoseBuilder newGildedRose() {
        this.items = new ArrayList<Item>();
        return this;
    }

    public GildedRoseBuilder withItem(String name, int sellIn, int quality) {
        items.add(new Item(name, sellIn, quality));
        return this;
    }

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
