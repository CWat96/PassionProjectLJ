package com.example.project.config;

import com.example.project.domain.Effects;
import com.example.project.repository.EffectsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class EffectsConfig {
    @Autowired
    private EffectsRepository repository;

    @PostConstruct
    public void setup() {
        Effects effect1 = new Effects();
        effect1.setId(1L);
        effect1.setEffectsName("Nickel Allergy");

        Effects effect2 = new Effects();
        effect2.setId(2L);
        effect2.setEffectsName("Eczema");

        Effects effect3 = new Effects();
        effect3.setId(3L);
        effect3.setEffectsName("Contact Dermatitis");

        Effects effect4 = new Effects();
        effect4.setId(4L);
        effect4.setEffectsName("Acute Dermatitis");

        Effects effect5 = new Effects();
        effect5.setId(5L);
        effect5.setEffectsName("Chronic Dermatitis");

        Effects effect6 = new Effects();
        effect6.setId(6L);
        effect6.setEffectsName("Pompholyx Dermatitis");

        Effects effect7 = new Effects();
        effect7.setId(7L);
        effect7.setEffectsName("Psoriasis");

        Effects effect8 = new Effects();
        effect8.setId(8L);
        effect8.setEffectsName("Koebner Phenomenon");

        Effects effect9 = new Effects();
        effect9.setId(9L);
        effect9.setEffectsName("Argyria");

        Effects effect10 = new Effects();
        effect10.setId(10L);
        effect10.setEffectsName("Patina");

        Effects effect11 = new Effects();
        effect11.setId(11L);
        effect11.setEffectsName("Hypoallergenic");



        repository.saveAll(Arrays.asList(
                effect1,
                effect2,
                effect3,
                effect4,
                effect5,
                effect6,
                effect7,
                effect8,
                effect9,
                effect10,
                effect11
        ));
    }

}
