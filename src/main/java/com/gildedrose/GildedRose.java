package com.gildedrose;

import java.util.HashMap;
import java.util.Map;

class GildedRose {
    Item[] items;
    private String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private String AGED_BRIE = "Aged Brie";
    private String CONJURED = "Conjured";
    private Item item;
    private Map<String, QualityStrategy> updatedQualityMap;

    public GildedRose(Item[] items) {
        this.items = items;
        updatedQualityMap = new HashMap<>();
        initializeQualityMap();
    }

    private void initializeQualityMap() {
        updatedQualityMap.put(SULFURAS, this::sulfurasQuality);
        updatedQualityMap.put(AGED_BRIE, this::agedBrieQuality);
        updatedQualityMap.put(BACKSTAGE_PASS, this::backstageQuality);
        updatedQualityMap.put(CONJURED, this::conjuredQuality);
    }

    private int updatedQuality() {
        QualityStrategy qualityStrategy =
                updatedQualityMap.getOrDefault(item.name, this::defaultQuality);
        return qualityStrategy.updatedQuality();
    }

    public void updateQuality() {
        for (Item currentItem : items) {
            item = currentItem;
            item.quality = updatedQuality();
            reduceSellInByOne();
        }
    }

    private int defaultQuality() {
        return reduceQualityNotNegative(sellDatePassed() ? 2 : 1);
    }

    private int agedBrieQuality() {
        return increaseQualityNotOver50(sellDatePassed() ? +2 : +1);
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

    private int sulfurasQuality() {
        return item.quality;
    }

    private int conjuredQuality() {
        return reduceQualityNotNegative(2);
    }

    private boolean sellDatePassed() {
        return item.sellIn <= 0;
    }

    private void reduceSellInByOne() {
        item.sellIn = item.sellIn - 1;
    }

    private int increaseQualityNotOver50(int increment) {
        return item.quality < 50 ? item.quality + increment : item.quality;
    }

    public int reduceQualityNotNegative(int decrement) {
        return item.quality > 0 ? item.quality - decrement : item.quality;
    }

    private boolean thereAreXDaysOrLess(int maxSellIn) {
        return item.sellIn <= maxSellIn;
    }

}
