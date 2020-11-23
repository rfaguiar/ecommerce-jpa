package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.NotaFiscal;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPagamento;
import org.junit.Test;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class PassandoParametrosTest extends EntityManagerTest {

    @Test
    public void passarParametro() {
        var jpql = "select p from Pedido p join p.pagamento pag " +
                " where p.id = :pedidoId " +
                " and pag.status = ?2 ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        typedQuery.setParameter("pedidoId", 2);
        typedQuery.setParameter(2, StatusPagamento.PROCESSANDO);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void passarParametroDate() {
        var jpql = "select nf from NotaFiscal nf " +
                " where nf.dataEmissao <= ?1 ";

        TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(jpql, NotaFiscal.class);
        typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP);

        List<NotaFiscal> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }
}
