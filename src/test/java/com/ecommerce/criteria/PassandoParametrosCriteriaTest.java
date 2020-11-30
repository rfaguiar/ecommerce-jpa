package com.ecommerce.criteria;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.NotaFiscal;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class PassandoParametrosCriteriaTest extends EntityManagerTest {

    @Test
    public void passarParametroDate() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<NotaFiscal> selectNotaFiscal = criteriaBuilder.createQuery(NotaFiscal.class);
        Root<NotaFiscal> fromNotafiscal = selectNotaFiscal.from(NotaFiscal.class);

        selectNotaFiscal.select(fromNotafiscal);

        ParameterExpression<Date> parameterExpressionData = criteriaBuilder
                .parameter(Date.class, "dataInicial");

        selectNotaFiscal.where(criteriaBuilder.greaterThan(fromNotafiscal.get("dataEmissao"), parameterExpressionData));

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(selectNotaFiscal);

        Calendar dataInicial = Calendar.getInstance();
        dataInicial.add(Calendar.DATE, -30);

        typedQuery.setParameter("dataInicial", dataInicial.getTime(), TemporalType.TIMESTAMP);

        List<NotaFiscal> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void passarParametro() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> selectPedido = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> fromPedido = selectPedido.from(Pedido.class);

        selectPedido.select(fromPedido);

        ParameterExpression<Integer> parameterExpressionId = criteriaBuilder
                .parameter(Integer.class, "id");

        selectPedido.where(criteriaBuilder.equal(fromPedido.get("id"), parameterExpressionId));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(selectPedido);
        typedQuery.setParameter("id", 1);

        Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

}
