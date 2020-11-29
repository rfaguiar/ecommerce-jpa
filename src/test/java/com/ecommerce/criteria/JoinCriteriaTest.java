package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.NotaFiscal;
import com.ecommerce.model.Pagamento;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPagamento;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JoinCriteriaTest extends EntityManagerTest {

    @Test
    public void fazerJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = fromPedido.join("pagamento");
        criteriaQuery.select(fromPedido);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        assertEquals(4, pedidos.size());
    }

    @Test
    public void fazerJoinComOn() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = fromPedido.join("pagamento");
        joinPagamento.on(
                criteriaBuilder.equal(
                        joinPagamento.get("status"),
                        StatusPagamento.PROCESSANDO
                )
        );

        criteriaQuery.select(fromPedido);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        assertEquals(2, pedidos.size());
    }

    @Test
    public void fazerLeftOuterJoin() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = criteriaQuery.from(Pedido.class);
        Join<Pedido, Pagamento> joinPagamento = fromPedido.join("pagamento", JoinType.LEFT);

        criteriaQuery.select(fromPedido);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> pedidos = typedQuery.getResultList();
        assertEquals(4, pedidos.size());
    }

    @Test
    public void usarJoinFetch() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);
        Fetch<Pedido, Cliente> joinFetchCliente = fromPedido.fetch("cliente");
        Fetch<Pedido, NotaFiscal> joinFetchNotFiscal = fromPedido.fetch("notaFiscal", JoinType.LEFT);
        Fetch<Pedido, Pagamento> joinFetchPagamento = fromPedido.fetch("pagamento", JoinType.LEFT);


        selectPedido.select(fromPedido);

        Path<Integer> pedidoId = fromPedido.get("id");
        selectPedido.where(criteriaBuilder.equal(pedidoId, 1));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

}
