package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlatingMaterialTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PlatingMaterial getPlatingMaterialSample1() {
        return new PlatingMaterial().id(1L).platingName("platingName1");
    }

    public static PlatingMaterial getPlatingMaterialSample2() {
        return new PlatingMaterial().id(2L).platingName("platingName2");
    }

    public static PlatingMaterial getPlatingMaterialRandomSampleGenerator() {
        return new PlatingMaterial().id(longCount.incrementAndGet()).platingName(UUID.randomUUID().toString());
    }
}
