package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertFalse;

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
}
