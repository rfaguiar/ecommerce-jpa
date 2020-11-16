package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.NotaFiscal;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MapsIdTest extends EntityManagerTest {

    @Test
    public void inserirPagamento() {
        var pedido = entityManager.find(Pedido.class, 1);

        var notaFiscal = NotaFiscal.builder()
                .pedido(pedido)
                .dataEmissao(new Date())
                .xml("<xml/>".getBytes())
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(notaFiscal);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
        assertNotNull(notaFiscalVerificacao);
        assertEquals(pedido.getId(), notaFiscalVerificacao.getId());
    }

    @Test
    public void inserirItemPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var produto = entityManager.find(Produto.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .dataCriacao(LocalDateTime.now())
                .status(StatusPedido.AGUARDANDO)
                .total(produto.getPreco())
                .build();

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .pedido(pedido)
                .produto(produto)
                .precoProduto(produto.getPreco())
                .quantidade(1)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var itemPedidoerificacao = entityManager.find(ItemPedido.class, new ItemPedidoId(pedido.getId(), produto.getId()));
        assertNotNull(itemPedidoerificacao);

    }

}
