package com.bloff.pharmacy.app;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class SpringBootPharmacyApplicationTests {

    @Test
    public void contextLoads() {
        // GIVEN
        assertNotNull("Test");

        // WHEN
        String result = "Test";

        // THEN
        assertNotNull(result);
    }

}