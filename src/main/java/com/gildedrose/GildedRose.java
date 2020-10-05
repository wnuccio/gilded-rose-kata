package com.gildedrose;

import java.util.stream.Stream;

class GildedRose {
    Item[] items;
    private String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private String AGED_BRIE = "Aged Brie";
    private Item item;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (Item value : items) {
            this.item = value;

            if (is(SULFURAS)) {

            } else if (is(AGED_BRIE)) {
                increaseQualityNotOver50(sellDatePassed() ? 2 : 1);

            } else if (is(BACKSTAGE_PASS)) {
                if (thereAreDays(11, Integer.MAX_VALUE)) {
                    increaseQualityNotOver50(1);

                } else if (thereAreDays(6, 10)) {
                    increaseQualityNotOver50(2);

                } else if (thereAreDays(1, 5)) {
                    increaseQualityNotOver50(3);

                } else {
                    item.quality = 0;
                }

            } else {
                reduceQualityNotUnderZero(sellDatePassed() ? 2 : 1);
            }

            reduceSellInByOne();
        }
    }

    private boolean sellDatePassed() {
        return item.sellIn <= 0;
    }

    private boolean thereAreDays(int min, int max) {
        return item.sellIn >= min && item.sellIn <= max;
    }

    private void reduceSellInByOne() {
        item.sellIn = item.sellIn - 1;
    }

    private void increaseQualityNotOver50(int increment) {
        if (item.quality < 50)
            item.quality = item.quality + increment;
    }

    private boolean is(String... names) {
        return Stream.of(names)
                .anyMatch(name -> this.item.name.equals(name));
    }

    private void reduceQualityNotUnderZero(int decrement) {
        if (item.quality > 0)
            item.quality = item.quality - decrement;
    }

    private boolean thereAreXDaysOrLess(int maxSellIn) {
        return item.sellIn <= maxSellIn;
    }
}
