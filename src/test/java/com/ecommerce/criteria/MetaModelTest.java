package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Produto;
import com.ecommerce.model.Produto_;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class MetaModelTest extends EntityManagerTest {

    @Test
    public void utilizarMetaModel() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> selectProduto = criteriaBuilder.createQuery(Produto.class);
        Root<Produto> fromProduto = selectProduto.from(Produto.class);

        selectProduto.select(fromProduto);

        selectProduto.where(criteriaBuilder.or(
                criteriaBuilder.like(fromProduto.get(Produto_.nome), "%C%"),
                criteriaBuilder.like(fromProduto.get(Produto_.descricao), "%C%")
        ));

        TypedQuery<Produto> typedQuery = entityManager.createQuery(selectProduto);
        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

}
