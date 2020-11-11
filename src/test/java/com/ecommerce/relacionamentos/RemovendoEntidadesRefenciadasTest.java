package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class RemovendoEntidadesRefenciadasTest extends EntityManagerTest {

    @Test
    public void removerEntidadeRelacionada() {
        var pedido = entityManager.find(Pedido.class, 1);

        assertFalse(pedido.getItensPedido().isEmpty());

        pedido.getItensPedido().forEach(entityManager::remove);
        entityManager.remove(pedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, 1);
        assertNull(pedidoVerificacao);
    }
}
