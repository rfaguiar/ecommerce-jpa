package com.ecommerce.consultasnativas;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import org.junit.Test;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class StoredProceduresTest  extends EntityManagerTest {

    @Test
    public void receberListaDaProcedure() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("compraram_acima_media", Cliente.class);

        storedProcedureQuery.registerStoredProcedureParameter(
                "ano", Integer.class, ParameterMode.IN);

        storedProcedureQuery.setParameter("ano", 2020);

        List<Cliente> lista = storedProcedureQuery.getResultList();

        assertFalse(lista.isEmpty());
    }


    @Test
    public void usarParametrosInEOut() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("buscar_nome_produto");

        storedProcedureQuery.registerStoredProcedureParameter(
                "produto_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "produto_nome", String.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("produto_id", 1);

        String nome = (String) storedProcedureQuery
                .getOutputParameterValue("produto_nome");

        assertEquals("Kindle", nome);
    }

}
