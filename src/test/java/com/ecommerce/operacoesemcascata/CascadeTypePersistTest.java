package com.ecommerce.operacoesemcascata;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Categoria;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.ItemPedido;
import com.ecommerce.model.ItemPedidoId;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import com.ecommerce.model.SexoCliente;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CascadeTypePersistTest extends EntityManagerTest {

//    @Test
    public void persistirPedidoComItens() {
        var cliente = entityManager.find(Cliente.class, 1);
        var produto = entityManager.find(Produto.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(produto.getPreco())
                .status(StatusPedido.AGUARDANDO)
                .build();

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .pedido(pedido)
                .produto(produto)
                .quantidade(1)
                .precoProduto(produto.getPreco())
                .build();
        pedido.setItensPedido(Set.of(itemPedido)); //CascadeType.PERSIST

        entityManager.getTransaction().begin();
        entityManager.persist(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao);
        assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
    }

    @Test
    public void persistirItemPedidoComPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var produto = entityManager.find(Produto.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(produto.getPreco())
                .status(StatusPedido.AGUARDANDO)
                .build();

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .pedido(pedido)
                .produto(produto)
                .quantidade(1)
                .precoProduto(produto.getPreco())
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao);
        assertFalse(pedidoVerificacao.getItensPedido().isEmpty());
    }

    @Test
    public void persistirPedidoComCliente() {
        var cliente = Cliente.builder()
                .dataNascimento(LocalDate.of(1980, 1, 1))
                .sexo(SexoCliente.MASCULINO)
                .nome("José Carlos")
                .cpf("0123456789")
                .build();

        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(BigDecimal.ZERO)
                .status(StatusPedido.AGUARDANDO)
                .build();


        entityManager.getTransaction().begin();
        entityManager.persist(cliente); //cascadeType.PERSIST
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertNotNull(clienteVerificacao);
    }

//    @Test
    public void persistirProdutoComCategoria() {
        var produto = Produto.builder()
                .preco(BigDecimal.TEN)
                .nome("Fones de Ouvido")
                .descricao("A melhor qualidade de som")
                .build();

        var categoria = Categoria.builder()
                .nome("Áudio")
                .build();
        produto.setCategorias(Set.of(categoria));//cascadeType.PERSIST


        entityManager.getTransaction().begin();
        entityManager.persist(produto);
        entityManager.getTransaction().commit();
        entityManager.clear();


        var categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
        assertNotNull(categoriaVerificacao);
    }

//    @Test
    public void atualizarPedidoComItens() {
        var cliente = entityManager.find(Cliente.class, 1);
        var produto = entityManager.find(Produto.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(produto.getPreco())
                .status(StatusPedido.AGUARDANDO)
                .build();
        pedido.setId(1);

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .pedido(pedido)
                .produto(produto)
                .quantidade(3)
                .precoProduto(produto.getPreco())
                .build();
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());

        pedido.setItensPedido(Set.of(itemPedido));// CasdadeType.MERGE

        entityManager.getTransaction().begin();
        entityManager.merge(pedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao);
        assertTrue(pedidoVerificacao.getItensPedido()
                .stream()
                .findFirst()
                .get()
                .getQuantidade()
                .equals(3));
    }

//    @Test
    public void atualizarItemPedidoComPedido() {
        var cliente = entityManager.find(Cliente.class, 1);
        var produto = entityManager.find(Produto.class, 1);

        var pedido = Pedido.builder()
                .cliente(cliente)
                .total(produto.getPreco())
                .status(StatusPedido.PAGO)
                .build();
        pedido.setId(1);

        var itemPedido = ItemPedido.builder()
                .id(new ItemPedidoId())
                .pedido(pedido)
                .produto(produto)
                .quantidade(5)
                .precoProduto(produto.getPreco())
                .build();
        itemPedido.getId().setPedidoId(pedido.getId());
        itemPedido.getId().setProdutoId(produto.getId());

        pedido.setItensPedido(Set.of(itemPedido));// CasdadeType.MERGE

        entityManager.getTransaction().begin();
        entityManager.merge(itemPedido);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var itemPedidoVerificacao = entityManager.find(ItemPedido.class, itemPedido.getId());
        assertNotNull(itemPedidoVerificacao);
        assertTrue(StatusPedido.PAGO.equals(itemPedidoVerificacao.getPedido().getStatus()));
    }
}
