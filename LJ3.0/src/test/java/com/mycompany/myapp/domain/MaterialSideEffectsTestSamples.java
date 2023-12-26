package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MaterialSideEffectsTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MaterialSideEffects getMaterialSideEffectsSample1() {
        return new MaterialSideEffects().id(1L).materialSideEffectsName("materialSideEffectsName1");
    }

    public static MaterialSideEffects getMaterialSideEffectsSample2() {
        return new MaterialSideEffects().id(2L).materialSideEffectsName("materialSideEffectsName2");
    }

    public static MaterialSideEffects getMaterialSideEffectsRandomSampleGenerator() {
        return new MaterialSideEffects().id(longCount.incrementAndGet()).materialSideEffectsName(UUID.randomUUID().toString());
    }
}
