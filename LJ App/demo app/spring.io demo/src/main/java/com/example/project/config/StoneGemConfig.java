package com.example.project.config;

import com.example.project.domain.StoneGem;
import com.example.project.repository.StoneGemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class StoneGemConfig {
    @Autowired
    private StoneGemRepository repository;

    @PostConstruct
    public void setup() {
        StoneGem stone1 = new StoneGem();
        stone1.setId(201L);
        stone1.setStoneGemName("Diamond");

        StoneGem stone2 = new StoneGem();
        stone2.setId(202L);
        stone2.setStoneGemName("Ruby");

        StoneGem stone3 = new StoneGem();
        stone3.setId(203L);
        stone3.setStoneGemName("Pearl");

        StoneGem stone4 = new StoneGem();
        stone4.setId(204L);
        stone4.setStoneGemName("Lab Grown Diamond");

        StoneGem stone5 = new StoneGem();
        stone5.setId(205L);
        stone5.setStoneGemName("Aquamarine");

        StoneGem stone6 = new StoneGem();
        stone6.setId(206L);
        stone6.setStoneGemName("Opal");

        StoneGem stone7 = new StoneGem();
        stone7.setId(207L);
        stone7.setStoneGemName("Moissanite Diamond");

        StoneGem stone8 = new StoneGem();
        stone8.setId(208L);
        stone8.setStoneGemName("Sapphire");

        StoneGem stone9 = new StoneGem();
        stone9.setId(209L);
        stone9.setStoneGemName("Topaz");

        StoneGem stone10 = new StoneGem();
        stone10.setId(210L);
        stone10.setStoneGemName("Emerald");

        StoneGem stone11 = new StoneGem();
        stone11.setId(211L);
        stone11.setStoneGemName("Quartz");

        StoneGem stone12 = new StoneGem();
        stone12.setId(212L);
        stone12.setStoneGemName("Peridot");

        StoneGem stone13 = new StoneGem();
        stone13.setId(213L);
        stone13.setStoneGemName("Cubic Zirconia");


        repository.saveAll(Arrays.asList(
                stone1,
                stone2,
                stone3,
                stone4,
                stone5,
                stone6,
                stone7,
                stone8,
                stone9,
                stone10,
                stone11,
                stone12,
                stone13
        ));
    }

}
