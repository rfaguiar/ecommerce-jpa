package com.ecommerce.detalhesimportantes;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Cliente_;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Pedido_;
import org.junit.Test;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
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
    public void buscarAtributosEssenciaisDePedidoFetchGraph02() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes("dataCriacao", "status", "total");

        Subgraph<Cliente> subgraph = entityGraph.addSubgraph("cliente", Cliente.class);
        subgraph.addAttributeNodes("nome", "cpf");

        TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
        typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
        var lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
    }

    @Test
    public void buscarAtributosEssenciaisDePedidoFetchGraph03() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(Pedido_.dataCriacao, Pedido_.status, Pedido_.total);

        Subgraph<Cliente> subgraph = entityGraph.addSubgraph("cliente", Cliente.class);
        subgraph.addAttributeNodes(Cliente_.nome, Cliente_.cpf);

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
