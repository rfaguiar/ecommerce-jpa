package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import lombok.extern.java.Log;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@Log
public class BasicoCriteriaTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);

        selectPedido.select(fromPedido);

        Path<Integer> pedidoId = fromPedido.get("id");
        selectPedido.where(criteriaBuilder.equal(pedidoId, 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void selecionarUmAtributoParaRetorno() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> selectCliente = criteriaBuilder.createQuery(Cliente.class);
        Root<Pedido> fromPedido = selectCliente.from(Pedido.class);

        selectCliente.select(fromPedido.get("cliente"));

        Path<Integer> pedidoId = fromPedido.get("id");
        selectCliente.where(criteriaBuilder.equal(pedidoId, 1));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(selectCliente);
        Cliente cliente = typedQuery.getSingleResult();
        assertEquals("Fernando Medeiros", cliente.getNome());
    }

    @Test
    public void selecionaUmAtributoParaRetorno() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> selectProduto = criteriaBuilder.createQuery(Produto.class);

        Root<Produto> fromProduto = selectProduto.from(Produto.class);
        selectProduto.select(fromProduto);

        TypedQuery<Produto> typedQuery = entityManager.createQuery(selectProduto);
        List<Produto> resultList = typedQuery.getResultList();
        assertFalse(resultList.isEmpty());
    }

    @Test
    public void projetarOResultado() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> selectProduto = criteriaBuilder.createQuery(Object[].class);

        Root<Produto> fromProduto = selectProduto.from(Produto.class);
        selectProduto.multiselect(fromProduto.get("id"), fromProduto.get("nome"));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(selectProduto);
        List<Object[]> resultList = typedQuery.getResultList();
        assertFalse(resultList.isEmpty());
        resultList.forEach(arr -> log.info("ID: " + arr[0] + ", Nome: " + arr[1]));
    }

}
