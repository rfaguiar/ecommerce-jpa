package com.ecommerce;

import com.ecommerce.model.Cliente;
import com.ecommerce.model.EnderecoEntregaPedido;
import com.ecommerce.model.Pedido;
import com.ecommerce.model.StatusPedido;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.assertNotNull;

public class MapeandoObjetoEmbutidoTest extends EntityManagerTest {

    @Test
    public void testarEnum() {
        var cliente = entityManager.find(Cliente.class, 1);
        var pedido = Pedido.builder()
                .cliente(cliente)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .total(new BigDecimal(1000))
                .status(StatusPedido.AGUARDANDO)
                .endereco(EnderecoEntregaPedido.builder()
                        .cep("00000-00")
                        .logradouro("Rua das Laranjeiras")
                        .numero("123")
                        .bairro("Centro")
                        .cidade("São Paulo")
                        .estado("SP")
                        .build())
                .build();
        entityManager.persist(pedido);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Pedido.class, pedido.getId());
        assertNotNull(clienteVerificacao);
        assertNotNull(clienteVerificacao.getEndereco());
    }
}
