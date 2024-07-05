package com.bloff.pharmacy.app.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppConfigTests {

    @Test
    public void givenCondition_whenAction_thenAssertion() {
        // Given
        Object condition = new Object();

        // When
        Object action = new Object();

        // Then
        assert condition != null;
        assert action != null;
    }

}
