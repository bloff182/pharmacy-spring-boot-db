package com.bloff.pharmacy.app.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.bloff.pharmacy.app.dao.ProducentRepository;
import com.bloff.pharmacy.app.entity.Producent;

@ExtendWith(MockitoExtension.class)
class ProducentServiceImplTests {

    @Mock
    private ProducentRepository producentRepository;

    @InjectMocks
    private ProducentServiceImpl producentService;

    private Producent testProducent;

    @BeforeEach
    void setUp() {
        testProducent = new Producent("Test Producent", 1L, 1, new ArrayList<>());
    }

    @Test
    void testFindAll() {
        List<Producent> producentList = new ArrayList<>();
        producentList.add(testProducent);

        when(producentRepository.findAll()).thenReturn(producentList);

        List<Producent> result = producentService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProducent, result.get(0));
    }

    @Test
    void testFindById() {
        when(producentRepository.findById(1L)).thenReturn(Optional.of(testProducent));

        Producent result = producentService.findById(1L);

        assertNotNull(result);
        assertEquals(testProducent, result);
    }

    @Test
    void testSave() {
        when(producentRepository.save(testProducent)).thenReturn(testProducent);

        Producent result = producentService.save(testProducent);

        assertNotNull(result);
        assertEquals(testProducent, result);
    }

    @Test
    void testDeleteById() {
        assertDoesNotThrow(() -> producentService.deleteById(1L));
    }

    @Test
    void testFindAllByUserId() {
        List<Producent> producentList = new ArrayList<>();
        producentList.add(testProducent);

        when(producentRepository.findAllByUserId(1L)).thenReturn(producentList);

        List<Producent> result = producentService.findAllByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testProducent, result.get(0));
    }

    @Test
    void testDeleteByUserId() {
        assertDoesNotThrow(() -> producentService.deleteByUserId(1L));
    }
}
