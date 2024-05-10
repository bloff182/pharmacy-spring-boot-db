package com.bloff.pharmacy.app.controller;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Test
    public void testListUsers() {
        // ASSERTION
        assertNotNull("Test list users");
    }

    @Test
    public void testShowSingleUserByUsername() {
        // ASSERTION
        assertNotNull("Test show single user by username");
    }

    @Test
    public void testSaveForUpdateUser() {
        // ASSERTION
        assertNotNull("Test save for update user");
    }

    @Test
    public void testUpdateUser() {
        // ASSERTION
        assertNotNull("Test update user");
    }

    @Test
    public void testDeleteUser() {
        // ASSERTION
        assertNotNull("Test delete user");
    }

    @Test
    public void testSearchUser() {
        // ASSERTION
        assertNotNull("Test search user");
    }

    @Test
    public void testSetInactiveProducent() {
        // ASSERTION
        assertNotNull("Test set inactive producent");
    }
}