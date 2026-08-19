package com.example.ComercialHuguito.Models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ProveedorTest {

    @Test
    public void testRucValido() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("12345678901");
        assertTrue(proveedor.getRuc().length() == 11);
    }

    @Test
    public void testRucInvalido() {
        Proveedor proveedor = new Proveedor();
        proveedor.setRuc("123");
        assertNotEquals(11, proveedor.getRuc().length());
    }
}

