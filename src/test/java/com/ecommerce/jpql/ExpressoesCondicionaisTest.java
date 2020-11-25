package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import lombok.extern.java.Log;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertFalse;

@Log
public class ExpressoesCondicionaisTest extends EntityManagerTest {

    @Test
    public void usarExpressaoCondicionalLike() {
        var jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%') ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("nome", "Fernando");

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoIsNull() {
        var jpql = "select p from Produto p where p.foto is null ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoIsEmpty() {
        var jpql = "select p from Produto p where p.categorias is empty";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoMaiorEMenor() {
        var jpql = "select p from Produto p where p.preco >= :precoInicial and p.preco <= :precoFinal";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("precoInicial", new BigDecimal(400));
        typedQuery.setParameter("precoFinal", new BigDecimal(1500));

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarMaiorMenorComDatas() {
        String jpql = "select p from Pedido p where p.dataCriacao > :data";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("data", LocalDateTime.now().minusDays(2));

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoBetween() {
        var jpql = "select p from Produto p where p.preco between :precoInicial and :precoFinal";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        typedQuery.setParameter("precoInicial", new BigDecimal(400));
        typedQuery.setParameter("precoFinal", new BigDecimal(1500));

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarBetweenComDatas() {
        String jpql = "select p from Pedido p where p.dataCriacao between :dataInicial and :dataFinal";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(10));
        typedQuery.setParameter("dataFinal", LocalDateTime.now());

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoDiferente() {
        var jpql = "select p from Produto p where p.id <> 1";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void usarExpressaoCase() {
        var jpql = "select p.id, " +
                " case type(p.pagamento) " +
                "       when PagamentoBoleto then 'Pago com boleto' " +
                "       when PagamentoCartao then 'Pago com cartão' " +
                "       else 'Não pago ainda.' " +
                " end " +
                " from Pedido p ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
        lista.forEach(arr -> log.info(arr[0] + ", " + arr[1]));
    }
}
