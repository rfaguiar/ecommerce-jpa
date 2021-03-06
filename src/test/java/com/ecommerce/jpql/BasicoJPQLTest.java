package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.dto.ProdutoDTO;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Pedido;
import lombok.extern.java.Log;
import org.junit.Test;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Log
public class BasicoJPQLTest extends EntityManagerTest {

    @Test
    public void buscarPorIdentificador() {
//        entityManager.find(Pedido.class, 1);

        TypedQuery<Pedido> typedQuery = entityManager
                .createQuery("select p from Pedido p where p.id = 1", Pedido.class);
        Pedido pedido = typedQuery.getSingleResult();
        assertNotNull(pedido);
    }

    @Test
    public void mostrarDiferencaQueries() {
        var jpql = "select p from Pedido p where p.id = 1";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
        Pedido pedido1 = typedQuery.getSingleResult();
        assertNotNull(pedido1);

        Query query = entityManager.createQuery(jpql);
        Pedido pedido2 = (Pedido) query.getSingleResult();
        assertNotNull(pedido2);
    }

    @Test
    public void selecionarUmAtributoParaRetorno() {
        var jpql = "select p.nome from Produto p";

        TypedQuery<String> typedQuery = entityManager
                .createQuery(jpql, String.class);
        List<String> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        var jpqlCliente = "select p.cliente from Pedido p";

        TypedQuery<Cliente> typedQueryCliente = entityManager
                .createQuery(jpqlCliente, Cliente.class);
        List<Cliente> listaCliente = typedQueryCliente.getResultList();
        assertFalse(listaCliente.isEmpty());
    }

    @Test
    public void projetarOResultado() {
        var jpql = "select id, nome from Produto";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
        List<Object[]> lista = typedQuery.getResultList();

        assertEquals(2, lista.get(0).length);
        lista.forEach(arr -> log.info(arr[0] + ", " + arr[1]));
    }

    @Test
    public void projetarDTO() {
        var jpql = "select new com.ecommerce.dto.ProdutoDTO(id, nome) from Produto";

        TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
        List<ProdutoDTO> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());
        lista.forEach(dto -> log.info(dto.toString()));
    }

    @Test
    public void ordenarResultados() {
        var jpql = "select c from Cliente c order by c.nome asc"; // desc

        TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);

        List<Cliente> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
    }

    @Test
    public void usarDistinct() {
        var jpql = "select distinct p from Pedido p " +
                " join p.itensPedido i join i.produto pro " +
                " where pro.id in (1, 2, 3, 4) ";

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);

        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        System.out.println(lista.size());
    }

}
