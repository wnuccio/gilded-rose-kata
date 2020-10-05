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

            item.quality = updatedQuality();
            reduceSellInByOne();
        }
    }

    private int updatedQuality() {
        int newQuality;

        if (is(SULFURAS)) {
            newQuality = sulfurasQuality();

        } else if (is(AGED_BRIE)) {
            newQuality = increaseQualityNotOver50(sellDatePassed() ? +2 : +1);

        } else if (is(BACKSTAGE_PASS)) {
            newQuality = backstageQuality();

        } else {
            newQuality = reduceQualityNotNegative(sellDatePassed() ? 2 : 1);
        }
        return newQuality;
    }

    private int sulfurasQuality() {
        return item.quality;
    }

    private int backstageQuality() {
        if (sellDatePassed()) {
            return 0;

        } else if (thereAreXDaysOrLess(5)) {
            return increaseQualityNotOver50(3);

        } else if (thereAreXDaysOrLess(10)) {
            return increaseQualityNotOver50(2);

        } else {
            return increaseQualityNotOver50(1);
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

    private int increaseQualityNotOver50(int increment) {
        return item.quality < 50 ? item.quality + increment : item.quality;
    }

    private int reduceQualityNotNegative(int decrement) {
        return item.quality > 0 ? item.quality - decrement : item.quality;
    }

    private boolean thereAreXDaysOrLess(int maxSellIn) {
        return item.sellIn <= maxSellIn;
    }
}
