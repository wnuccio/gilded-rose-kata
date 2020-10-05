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
                updateQualityIn0_50range(sellDatePassed() ? +2 : +1);

            } else if (is(BACKSTAGE_PASS)) {
                updateBackstage();

            } else {
                updateQualityIn0_50range(sellDatePassed() ? -2 : -1);
            }

            reduceSellInByOne();
        }
    }

    private void updateBackstage() {
        if (sellDatePassed()) {
            item.quality = 0;

        } else if (thereAreXDaysOrLess(5)) {
            updateQualityIn0_50range(3);

        } else if (thereAreXDaysOrLess(10)) {
            updateQualityIn0_50range(2);

        } else {
            updateQualityIn0_50range(1);
        }
    }

    private boolean sellDatePassed() {
        return item.sellIn <= 0;
    }

    private void reduceSellInByOne() {
        item.sellIn = item.sellIn - 1;
    }

    private boolean is(String... names) {
        return Stream.of(names)
                .anyMatch(name -> this.item.name.equals(name));
    }

    private void updateQualityIn0_50range(int increment) {
        if (increment >0 && item.quality < 50 || increment < 0 && item.quality > 0)
            item.quality = item.quality + increment;
    }

    private boolean thereAreXDaysOrLess(int maxSellIn) {
        return item.sellIn <= maxSellIn;
    }
}
