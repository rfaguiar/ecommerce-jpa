package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PropriedadeTransientesTest extends EntityManagerTest {


    @Test
    public void validarPrimeiroNome() {
        var cliente = entityManager.find(Cliente.class, 1);
        assertEquals("Fernando", cliente.getPrimeiroNome());
    }

}
