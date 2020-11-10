package com.ecommerce;

import com.ecommerce.model.EnderecoEntregaPedido;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

public class MapeandoObjetoEmbutidoTest extends EntityManagerTest {

    @Test
    public void testarEnum() {
        var pedido = new Pedido(1, LocalDateTime.now(), LocalDateTime.now(), 1,
                new BigDecimal(1000), StatusPedido.AGUARDANDO,
                new EnderecoEntregaPedido("00000-00", "Rua das Laranjeiras",
                        "123", null, "Centro",
                        "São Paulo", "SP"));
        entityManager.persist(pedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(clienteVerificacao);
        assertNotNull(clienteVerificacao.getEndereco());
    }
}
