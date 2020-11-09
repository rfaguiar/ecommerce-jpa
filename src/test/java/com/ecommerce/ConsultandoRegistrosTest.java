package com.ecommerce;

import com.ecommerce.model.Produto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ConsultandoRegistrosTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
        Produto produto = entityManager.find(Produto.class, 1);
        assertNotNull(produto);
        assertEquals("Kindle", produto.getNome());
    }

}
