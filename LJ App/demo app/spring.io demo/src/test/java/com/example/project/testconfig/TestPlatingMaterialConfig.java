package com.example.project.testconfig;

import com.example.project.config.PlatingMaterialConfig;
import com.example.project.domain.PlatingMaterial;
import com.example.project.repository.PlatingMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class PlatingMaterialConfigTest {

    @Mock
    private PlatingMaterialRepository repository;

    @InjectMocks
    private PlatingMaterialConfig platingMaterialConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void setup() {
        // Arrange
        List<PlatingMaterial> platingMaterialsList = new ArrayList<>();
        when(repository.saveAll(anyList())).thenReturn(platingMaterialsList);

        // Act
        platingMaterialConfig.setup();

        // Assert
        verify(repository, times(1)).saveAll(anyList());
        // Add more assertions if necessary to verify the saved plating materials
    }
}

