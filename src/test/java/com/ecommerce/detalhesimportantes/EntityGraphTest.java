package com.ecommerce.detalhesimportantes;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Pedido;
import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.TypedQuery;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class EntityGraphTest  extends EntityManagerTest {

    @Test
    public void buscarAtributosEssenciaisDePedidoFetchGraph() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("dataCriacao", "status", "total", "cliente", "notaFiscal");

//        var properties = new HashMap<String, Object>();
//        properties.put("javax.persistence.fetchgraph", entityGraph);
//        var pedido = entityManager.find(Pedido.class, 1, properties);
//        assertNotNull(pedido);

        TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        var lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDePedidoLoadGraph() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("dataCriacao", "status", "total", "cliente", "notaFiscal");

        var properties = new HashMap<String, Object>();
        properties.put("javax.persistence.loadgraph", entityGraph);

        var pedido = entityManager.find(Pedido.class, 1, properties);
        assertNotNull(pedido);
    }

}
