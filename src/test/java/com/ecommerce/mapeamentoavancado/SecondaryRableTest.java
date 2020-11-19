package com.ecommerce.mapeamentoavancado;

import com.ecommerce.EntityManagerTest;
import com.ecommerce.model.Cliente;
import com.ecommerce.model.SexoCliente;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

public class SecondaryRableTest extends EntityManagerTest {

    @Test
    public void salvarCliente() {
        var cliente = Cliente.builder()
                .nome("Carlos Finotti")
                .cpf("77116984024")
                .sexo(SexoCliente.MASCULINO)
                .dataNascimento(LocalDate.of(1990, 1, 1))
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertNotNull(clienteVerificacao.getSexo());
        assertNotNull(clienteVerificacao.getDataNascimento());
    }
}
