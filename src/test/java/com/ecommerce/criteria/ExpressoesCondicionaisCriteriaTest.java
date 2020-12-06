package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Cliente_;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Pedido_;
import com.ecommerce.model.Produto;
import com.ecommerce.model.Produto_;
import com.ecommerce.model.StatusPedido;
import lombok.extern.java.Log;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@Log
public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {

    @Test
    public void usarExpressaoCondicionalLike() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> selectCliente = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> fromCliente = selectCliente.from(Cliente.class);

        selectCliente.select(fromCliente);

        selectCliente.where(
                criteriaBuilder.like(
                        fromCliente.get(Cliente_.nome),
                        "%a%"
                )
        );

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(selectCliente);
        List<Cliente> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoIsNull() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> selectProduto = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> fromProduto = selectProduto.from(Produto.class);

        selectProduto.select(fromProduto);

        selectProduto.where(
                criteriaBuilder.isNull(
                    fromProduto.get(Produto_.foto)
                )
        );


        TypedQuery<Produto> typedQuery = entityManager.createQuery(selectProduto);
        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoIsEmpty() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> selectProduto = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> fromProduto = selectProduto.from(Produto.class);

        selectProduto.select(fromProduto);

        selectProduto.where(
                criteriaBuilder.isEmpty(
                        fromProduto.get(Produto_.categorias)
                )
        );


        TypedQuery<Produto> typedQuery = entityManager.createQuery(selectProduto);
        List<Produto> lista = typedQuery.getResultList();
        assertTrue(lista.isEmpty());
    }

    @Test
    public void usarExpressaoMaiorMenor() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> selectProduto = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> fromProduto = selectProduto.from(Produto.class);

        selectProduto.select(fromProduto);

        selectProduto.where(
                criteriaBuilder.greaterThanOrEqualTo(
                        fromProduto.get(Produto_.preco),
                        new BigDecimal(799)
                ),
                criteriaBuilder.lessThanOrEqualTo(
                        fromProduto.get(Produto_.preco),
                        new BigDecimal(3500)
                )
        );


        TypedQuery<Produto> typedQuery = entityManager.createQuery(selectProduto);
        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        lista.forEach(p -> log.info(p.getId() + " | " + p.getNome() + " | " + p.getPreco()));
    }

    @Test
    public void usarMaiorMenorComDatas() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);

        selectPedido.select(fromPedido);
        selectPedido.where(
                criteriaBuilder.greaterThanOrEqualTo(
                        fromPedido.get(Pedido_.dataCriacao),
                        LocalDateTime.now().minusDays(4)
                ),
                criteriaBuilder.lessThanOrEqualTo(
                        fromPedido.get(Pedido_.dataCriacao),
                        LocalDateTime.now().minusDays(1)
                )
        );

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        lista.forEach(p -> log.info(p.getId() + " | " + p.getDataCriacao()));
    }

    @Test
    public void usarExpressaoBetween() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);

        selectPedido.select(fromPedido);
        selectPedido.where(
                criteriaBuilder.between(
                        fromPedido.get(Pedido_.total),
                        new BigDecimal(499),
                        new BigDecimal(2398)
                )
        );

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        lista.forEach(p -> log.info(p.getId() + " | " + p.getTotal()));
    }

    @Test
    public void usarExpressaoDiferente() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);

        selectPedido.select(fromPedido);
        selectPedido.where(
                criteriaBuilder.notEqual(
                        fromPedido.get(Pedido_.total),
                        new BigDecimal(499)
                )
        );

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        lista.forEach(p -> log.info(p.getId() + " | " + p.getTotal()));
    }

    @Test
    public void usarOperadores() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);

        selectPedido.select(fromPedido);
        selectPedido.where(
                criteriaBuilder.or(
                    criteriaBuilder.equal(
                            fromPedido.get(Pedido_.status),
                            StatusPedido.AGUARDANDO
                    ),
                    criteriaBuilder.equal(
                            fromPedido.get(Pedido_.status),
                            StatusPedido.PAGO
                    )
                ),
                criteriaBuilder.greaterThan(
                        fromPedido.get(Pedido_.total),
                        new BigDecimal(499)
                )
        );

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        lista.forEach(p -> log.info(p.getId() + " | " + p.getTotal() + " | " + p.getStatus()));
    }

    @Test
    public void ordenarRegistros() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Cliente> selectCliente = criteriaBuilder.createQuery(Cliente.class);
        Root<Cliente> fromCliente = selectCliente.from(Cliente.class);

        selectCliente.select(fromCliente);

        selectCliente.orderBy(criteriaBuilder.asc(fromCliente.get(Cliente_.nome)));

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(selectCliente);
        List<Cliente> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        lista.forEach(c -> log.info(c.getId() + " | " + c.getNome()));
    }

    @Test
    public void usarExpressaoCase() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.multiselect(
                root.get(Pedido_.id),
//                criteriaBuilder.selectCase(root.get(Pedido_.STATUS))
//                        .when(StatusPedido.PAGO.toString(), "Foi pago.")
//                        .when(StatusPedido.AGUARDANDO.toString(), "Está aguardando.")
//                        .otherwise(root.get(Pedido_.status))
                criteriaBuilder.selectCase(root.get(Pedido_.pagamento).type().as(String.class))
                        .when("boleto", "Foi pago com boleto.")
                        .when("cartao", "Foi pago com cartão")
                        .otherwise("Não identificado")
        );

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void usarExpressaoIN02() {
        Cliente cliente01 = entityManager.find(Cliente.class, 1);

        Cliente cliente02 = new Cliente();
        cliente02.setId(2);

        List<Cliente> clientes = List.of(cliente01, cliente02);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(root.get(Pedido_.cliente).in(clientes));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoIN01() {
        List<Integer> ids = List.of(1, 3, 4, 6);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(root.get(Pedido_.id).in(ids));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

}
