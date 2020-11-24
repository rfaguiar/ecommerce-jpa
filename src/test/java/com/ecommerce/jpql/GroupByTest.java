package com.ecommerce.jpql;

import com.ecommerce.EntityManagerTest;
import lombok.extern.java.Log;
import org.junit.Test;

import javax.persistence.TypedQuery;
import java.util.List;

import static org.junit.Assert.assertFalse;

@Log
public class GroupByTest extends EntityManagerTest {

    @Test
    public void agruparResultado() {
//         Quantidade de produtos por categoria.
//        String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";

//         Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
//                " from Pedido p " +
//                " group by year(p.dataCriacao), month(p.dataCriacao) ";

//         Total de vendas por categoria.
        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias c " +
                " group by c.id";


        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());

        lista.forEach(arr -> log.info(arr[0] + ", " + arr[1]));
    }

    @Test
    public void condicionarAgrupamentoComHaving() {
//         Total de vendas dentre as categorias que mais vendem.
        String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.produto pro join pro.categorias cat " +
                " group by cat.id " +
                " having avg(ip.precoProduto) > 1500 ";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

    @Test
    public void agruparEFiltrarResultado() {
//         Total de vendas por mês.
//        String jpql = "select concat(year(p.dataCriacao), '/', function('monthname', p.dataCriacao)), sum(p.total) " +
//                " from Pedido p " +
//                " where year(p.dataCriacao) = year(current_date) " +
//                " group by year(p.dataCriacao), month(p.dataCriacao) ";

//         Total de vendas por categoria.
//        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
//                " join ip.produto pro join pro.categorias c join ip.pedido p " +
//                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) = month(current_date) " +
//                " group by c.id";

//        Total de vendas por cliente
        String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
                " join ip.pedido p join p.cliente c join ip.pedido p " +
                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) >= (month(current_date) - 3) " +
                " group by c.id";

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> lista = typedQuery.getResultList();

        assertFalse(lista.isEmpty());

        lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }

}
