package com.example.project.testconfig;

import com.example.project.config.EffectsConfig;
import com.example.project.domain.Effects;
import com.example.project.repository.EffectsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class EffectsConfigTest {

    @Mock
    private EffectsRepository repository;

    @InjectMocks
    private EffectsConfig effectsConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setup() {
        // Arrange
        List<Effects> effectsList = new ArrayList<>();
        when(repository.saveAll(anyList())).thenReturn(effectsList);

        // Act
        effectsConfig.setup();

        // Assert
        verify(repository, times(1)).saveAll(anyList());
        // Add more assertions if necessary to verify the saved effects
    }
}
