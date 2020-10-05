package com.gildedrose;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {
    private Item AGED_BRIE = new Item("Aged Brie", 0, 0);
    private Item BACKSTAGE_PASSES = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0);
    private Item ANY_ITEM = new Item("item", 0, 0);
    private final Item SULFURAS = new Item("Sulfuras, Hand of Ragnaros", 0, 80);

    private GildedRose app;

    private GildedRoseBuilder gildedRose() {
        return new GildedRoseBuilder();
    }

    @Test
    void should_reduce_by_one_the_sellIn() {
        app = gildedRose()
                .withItem(ANY_ITEM, 15, 20)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(13, ANY_ITEM.sellIn);
    }

    @Test
    void should_reduce_by_one_the_quality() {
        app = gildedRose()
                .withItem(ANY_ITEM, 15, 20)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(18, ANY_ITEM.quality);
    }

    @Test
    void should_never_make_the_quality_negative() {
        app = gildedRose()
                .withItem(ANY_ITEM, 15, 1)
                .build();

        app.updateQuality();
        assertEquals(0, ANY_ITEM.quality);

        app.updateQuality();
        assertEquals(0, ANY_ITEM.quality);
    }

    @Test
    void should_reduce_by_two_the_quality_when_sell_date_is_passed() {
        app = gildedRose()
                .withItem(ANY_ITEM, 0, 4)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(-2, ANY_ITEM.sellIn);
        assertEquals(0, ANY_ITEM.quality);
    }

    @Test
    void should_increase_by_one_the_quality_of_Aged_Brie() {
        app = gildedRose()
                .withItem(AGED_BRIE, 15, 40)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(42, AGED_BRIE.quality);
    }

    @Test
    void should_increase_by_two_the_quality_of_Aged_Brie_when_sell_date_is_passed() {
        app = gildedRose()
                .withItem(AGED_BRIE, 0, 40)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(44, AGED_BRIE.quality);
    }

    @Test
    void should_increase_by_one_the_quality_of_Backstage_Passes() {
        app = gildedRose()
                .withItem(BACKSTAGE_PASSES, 15, 40)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(42, BACKSTAGE_PASSES.quality);
    }

    @Test
    void should_increase_by_two_the_quality_of_Backstage_Passes_when_there_are_10_days_or_less() {
        app = gildedRose()
                .withItem(BACKSTAGE_PASSES, 10, 30)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(8, BACKSTAGE_PASSES.sellIn);
        assertEquals(34, BACKSTAGE_PASSES.quality);
    }

    @Test
    void should_increase_by_three_the_quality_of_Backstage_Passes_when_there_are_5_days_or_less() {
        app = gildedRose()
                .withItem(BACKSTAGE_PASSES, 5, 30)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(3, BACKSTAGE_PASSES.sellIn);
        assertEquals(36, BACKSTAGE_PASSES.quality);
    }

    @Test
    void should_drop_to_zero_the_quality_of_Backstage_Passes_when_there_are_0_days_or_less() {
        app = gildedRose()
                .withItem(BACKSTAGE_PASSES, 1, 30)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(-1, BACKSTAGE_PASSES.sellIn);
        assertEquals(0, BACKSTAGE_PASSES.quality);
    }

    @Test
    void should_never_make_the_quality_of_any_item_greater_than_50() {
        app = gildedRose()
                .withItem(AGED_BRIE, 15, 49)
                .withItem(BACKSTAGE_PASSES, 15, 49)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(50, AGED_BRIE.quality);
        assertEquals(50, BACKSTAGE_PASSES.quality);
    }

    @Test
    void should_never_modify_the_quality_of_Sulfuras() {
        app = gildedRose()
                .withItem(SULFURAS, 15, 80)
                .build();

        assertEquals(80, SULFURAS.quality);

        app.updateQuality();
        app.updateQuality();

        assertEquals(80, SULFURAS.quality);
    }

    @Test
    void should_update_quality_of_more_items() {
        app = gildedRose()
                .withItem(ANY_ITEM, 11, 1)
                .withItem(AGED_BRIE, 12, 2)
                .withItem(BACKSTAGE_PASSES, 13, 3)
                .build();

        app.updateQuality();

        assertEquals(0, ANY_ITEM.quality);
        assertEquals(3, AGED_BRIE.quality);
        assertEquals(4, BACKSTAGE_PASSES.quality);
    }

    @Disabled
    @Test
    void should_reduce_by_two_the_quality_of_Conjured() {
        Item CONJURED = new Item("Conjured", 15, 20);
        app = gildedRose()
                .withItem(CONJURED, 2, 30)
                .build();

        app.updateQuality();
        app.updateQuality();

        assertEquals(0, CONJURED.sellIn);
        assertEquals(26, CONJURED.quality);

    }
}
