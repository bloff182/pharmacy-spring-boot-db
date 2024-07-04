package com.bloff.pharmacy.app.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProducentTests {

    @Test
    public void testGetId() {
        Producent producent = new Producent();
        producent.setId(1L);
        assertEquals(1L, producent.getId());
    }

    @Test
    public void testGetProducentName() {
        Producent producent = new Producent();
        producent.setProducentName("TestProducent");
        assertEquals("TestProducent", producent.getProducentName());
    }

    @Test
    public void testGetUserId() {
        Producent producent = new Producent();
        producent.setUserId(1L);
        assertEquals(1L, producent.getUserId());
    }

    @Test
    public void testGetActiv() {
        Producent producent = new Producent();
        producent.setActiv(1);
        assertEquals(1, producent.getActiv());
    }

    @Test
    public void testGetMedicines() {
        Producent producent = new Producent();
        producent.setMedicines(null);
        assertNull(producent.getMedicines());
    }

    @Test
    public void testProducentConstructor() {
        Producent producent = new Producent("TestProducent", 1L, 1, null);
        assertEquals("TestProducent", producent.getProducentName());
        assertEquals(1L, producent.getUserId());
        assertEquals(1, producent.getActiv());
        assertNull(producent.getMedicines());
    }
}
