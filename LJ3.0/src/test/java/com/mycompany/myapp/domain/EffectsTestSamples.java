package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EffectsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Effects getEffectsSample1() {
        return new Effects().id(1L).effect("effect1");
    }

    public static Effects getEffectsSample2() {
        return new Effects().id(2L).effect("effect2");
    }

    public static Effects getEffectsRandomSampleGenerator() {
        return new Effects().id(longCount.incrementAndGet()).effect(UUID.randomUUID().toString());
    }
}
