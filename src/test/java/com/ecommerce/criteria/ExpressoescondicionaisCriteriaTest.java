package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Cliente_;
import org.junit.Test;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class ExpressoescondicionaisCriteriaTest extends EntityManagerTest {

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

}
