package com.ecommerce.detalhesimportantes;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente_;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Pedido_;
import org.junit.Test;

import javax.persistence.EntityGraph;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class ProblemaN1Test extends EntityManagerTest {

    @Test
    public void resolverComFetch() {
        List<Pedido> lista = entityManager.createQuery(
                "select p from Pedido p " +
                        " join fetch p.cliente c " +
                        " join fetch p.pagamento " +
                        " join fetch p.notaFiscal ",
                Pedido.class)
                .getResultList();

        assertFalse(lista.isEmpty());
    }

    @Test
    public void resolverComEntityGraph() {
        EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
        entityGraph.addAttributeNodes(Pedido_.cliente, Pedido_.notaFiscal, Pedido_.pagamento);

        List<Pedido> lista = entityManager.createQuery("select p from Pedido p ",Pedido.class)
                .setHint("javax.persistence.loadgraph", entityGraph)
                .getResultList();

        assertFalse(lista.isEmpty());
    }

}
