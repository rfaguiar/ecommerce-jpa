package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.Pagamento;
import com.ecommerce.model.PagamentoCartao;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.SexoCliente;
import com.ecommerce.model.StatusPagamento;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class HerancaTest extends EntityManagerTest {

    @Test
    public void salvarCliente() {
        var cliente = Cliente.builder()
                .nome("Fernando Morais")
                .cpf("77116984023")
                .sexo(SexoCliente.MASCULINO)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertNotNull(clienteVerificacao.getId());
    }

    @Test
    public void buscarPagamentos() {
        List<Pagamento> pagamentos = entityManager
                .createQuery("select p from Pagamento p")
                .getResultList();

        assertFalse(pagamentos.isEmpty());
    }

    @Test
    public void incluirPagamentoPedido() {
        var pedido = entityManager.find(Pedido.class, 1);

        var pagamentoCartao = PagamentoCartao.builder()
                .numero("123")
                .build();
        pagamentoCartao.setPedido(pedido);
        pagamentoCartao.setStatus(StatusPagamento.PROCESSANDO);

        entityManager.getTransaction().begin();
        entityManager.persist(pagamentoCartao);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(pedidoVerificacao.getPagamento());
    }
}
