package com.example.project.config;

import com.example.project.domain.PlatingMaterial;
import com.example.project.repository.PlatingMaterialRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class PlatingMaterialConfig {
    @Autowired
    private PlatingMaterialRepository repository;

    @PostConstruct
    public void setup() {
        PlatingMaterial platingName1 = new PlatingMaterial();
        platingName1.setId(101L);
        platingName1.setPlatingName("Cobalt");

        PlatingMaterial platingName2 = new PlatingMaterial();
        platingName2.setId(102L);
        platingName2.setPlatingName("Gold");

        PlatingMaterial platingName3 = new PlatingMaterial();
        platingName3.setId(103L);
        platingName3.setPlatingName("Platinum");

        PlatingMaterial platingName4 = new PlatingMaterial();
        platingName4.setId(104L);
        platingName4.setPlatingName("Silver");

        PlatingMaterial platingName5 = new PlatingMaterial();
        platingName5.setId(105L);
        platingName5.setPlatingName("Stainless Steel");

        PlatingMaterial platingName6 = new PlatingMaterial();
        platingName6.setId(106L);
        platingName6.setPlatingName("Titanium");

        PlatingMaterial platingName7 = new PlatingMaterial();
        platingName7.setId(107L);
        platingName7.setPlatingName("Tungsten");

        PlatingMaterial platingName8 = new PlatingMaterial();
        platingName8.setId(108L);
        platingName8.setPlatingName("Brass");

        PlatingMaterial platingName9 = new PlatingMaterial();
        platingName9.setId(109L);
        platingName9.setPlatingName("Copper");

        PlatingMaterial platingName10 = new PlatingMaterial();
        platingName10.setId(110L);
        platingName10.setPlatingName("Chromium");

        PlatingMaterial platingName11 = new PlatingMaterial();
        platingName11.setId(111L);
        platingName11.setPlatingName("Sterling Silver");

        PlatingMaterial platingName12 = new PlatingMaterial();
        platingName12.setId(112L);
        platingName12.setPlatingName("Rhodium");


        repository.saveAll(Arrays.asList(
                platingName1,
                platingName2,
                platingName3,
                platingName3,
                platingName4,
                platingName5,
                platingName6,
                platingName7,
                platingName8,
                platingName9,
                platingName10,
                platingName11,
                platingName12
        ));
    }

}
