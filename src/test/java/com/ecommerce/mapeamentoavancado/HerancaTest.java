package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class HerancaTest extends EntityManagerTest {

    @Test
    public void salvarCliente() {
        var cliente = Cliente.builder()
                .nome("Fernando Morais")
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertNotNull(clienteVerificacao.getId());
    }
}
