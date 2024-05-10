package com.bloff.pharmacy.app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SecurityControllerTests {

    @Test
    public void testShowMyLoginPage() {
        // GIVEN
        // UNKNOWN

        // WHEN
        String result = new SecurityController().showMyLoginPage();

        // THEN
        // UNKNOWN
    }

    @Test
    public void testShowAccessDenied() {
        // GIVEN
        // UNKNOWN

        // WHEN
        String result = new SecurityController().showAccessDenied();

        // THEN
        // UNKNOWN
    }
}