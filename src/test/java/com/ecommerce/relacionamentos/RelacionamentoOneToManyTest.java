package com.ecommerce.relacionamentos;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;

public class RelacionamentoOneToManyTest extends EntityManagerTest {

    @Test
    public void verificarRelacionamentoPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = new Pedido(null, LocalDateTime.now(), null,
                null, BigDecimal.TEN, StatusPedido.AGUARDANDO, null, cliente);

        entityManager.persist(pedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clientVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertFalse(clientVerificacao.getPedidos().isEmpty());
    }
}
