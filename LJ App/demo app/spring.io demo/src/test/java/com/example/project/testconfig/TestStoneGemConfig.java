package com.example.project.testconfig;

import com.example.project.config.StoneGemConfig;
import com.example.project.domain.StoneGem;
import com.example.project.repository.StoneGemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class StoneGemConfigTest {

    @Mock
    private StoneGemRepository repository;

    @InjectMocks
    private StoneGemConfig stoneGemConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setup() {
        // Arrange
        List<StoneGem> stoneGemsList = new ArrayList<>();
        when(repository.saveAll(anyList())).thenReturn(stoneGemsList);

        // Act
        stoneGemConfig.setup();

        // Assert
        verify(repository, times(1)).saveAll(anyList());
        // Add more assertions if necessary to verify the saved stone gems
    }
}

