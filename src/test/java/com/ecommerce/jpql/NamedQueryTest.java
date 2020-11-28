package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.Produto;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class NamedQueryTest extends EntityManagerTest {

    @Test
    public void executarConsulta() {
        TypedQuery<Produto> typedQuery = entityManager
                .createNamedQuery("Produto.listarPorCategoria", Produto.class);
        typedQuery.setParameter("categoria", 2);

        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsultaArquivoXMLEspecificoProduto() {
        TypedQuery<Produto> typedQuery = entityManager
                .createNamedQuery("Produto.todos", Produto.class);

        List<Produto> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsultaArquivoXMLEspecificoPedido() {
        TypedQuery<Pedido> typedQuery = entityManager
                .createNamedQuery("Pedido.todos", Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
    }

    @Test
    public void executarConsultaArquivoXML() {
        TypedQuery<Pedido> typedQuery = entityManager
                .createNamedQuery("Pedido.listar", Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
    }

}
