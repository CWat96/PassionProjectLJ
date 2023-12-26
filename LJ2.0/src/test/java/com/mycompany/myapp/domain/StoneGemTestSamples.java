package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class StoneGemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static StoneGem getStoneGemSample1() {
        return new StoneGem().id(1L).stoneGemName("stoneGemName1");
    }

    public static StoneGem getStoneGemSample2() {
        return new StoneGem().id(2L).stoneGemName("stoneGemName2");
    }

    public static StoneGem getStoneGemRandomSampleGenerator() {
        return new StoneGem().id(longCount.incrementAndGet()).stoneGemName(UUID.randomUUID().toString());
    }
}
