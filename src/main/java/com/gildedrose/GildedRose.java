package com.gildedrose;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

class GildedRose {
    Item[] items;
    private String SULFURAS = "Sulfuras, Hand of Ragnaros";
    private String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
    private String AGED_BRIE = "Aged Brie";
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
    }

    private int agedBrieQuality() {
        return increaseQualityNotOver50(sellDatePassed() ? +2 : +1);
    }

    private int sulfurasQuality() {
        return item.quality;
    }

    public void updateQuality() {
        for (Item value : items) {
            this.item = value;
            item.quality = qualityStrategy().updatedQuality();
            reduceSellInByOne();
        }
    }

    private QualityStrategy qualityStrategy() {
        return updatedQualityMap.getOrDefault(
                item.name, () -> reduceQualityNotNegative(sellDatePassed() ? 2 : 1));
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

    private interface QualityStrategy {
        int updatedQuality();
    }
}
