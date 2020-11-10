package com.ecommerce;

import com.ecommerce.model.Cliente;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PrimeiroCrudTest extends EntityManagerTest {

    @Test
    public void inserirRegistroCliente() {
        var cliente = new Cliente(4, "Sebastião");
        entityManager.persist(cliente);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();

        entityManager.clear();

        Cliente clientePersistido = entityManager.find(Cliente.class, cliente.getId());
        assertEquals("Sebastião", clientePersistido.getNome());
    }

    @Test
    public void buscarClientePorId() {
        var cliente = entityManager.find(Cliente.class, 1);
        assertNotNull(cliente);
    }

    @Test
    public void atualizarCliente() {
        var cliente = entityManager.find(Cliente.class, 2);
        cliente.setNome("Joselito da Silva");
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();
        var clienteAtualizado = entityManager.find(Cliente.class, 2);
        assertNotNull(clienteAtualizado);
        assertEquals("Joselito da Silva", clienteAtualizado.getNome());
    }

    @Test
    public void removerCliente() {
        var cliente = entityManager.find(Cliente.class, 3);
        entityManager.remove(cliente);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();
        var clienteRemovido = entityManager.find(Cliente.class, 3);
        assertNull(clienteRemovido);
    }
}
