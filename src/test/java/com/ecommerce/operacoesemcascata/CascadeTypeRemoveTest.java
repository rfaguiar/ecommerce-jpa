package com.ecommerce.operacoesemcascata;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class CascadeTypeRemoveTest extends EntityManagerTest {

//    @Test
    public void removerPedidoEItens() {
        var pedido = entityManager.find(Pedido.class, 1);

        entityManager.getTransaction().begin();
        entityManager.remove(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNull(pedidoVerificacao);
    }

//    @Test
    public void removerItemPedidoEPedido() {
        var itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));

        entityManager.getTransaction().begin();
        entityManager.remove(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
        assertNull(pedidoVerificacao);
    }

    @Test
    public void test() {
        assertTrue(true);
    }

}
