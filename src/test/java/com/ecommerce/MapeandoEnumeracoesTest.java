package com.ecommerce;

import com.ecommerce.model.Cliente;
import com.ecommerce.model.SexoCliente;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class MapeandoEnumeracoesTest extends EntityManagerTest {

    @Test
    public void testarEnum() {
        var cliente = Cliente.builder()
                .nome("José Mineiro")
                .sexo(SexoCliente.MASCULINO)
                .build();

        entityManager.persist(cliente);
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
        entityManager.clear();

        var clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
        assertNotNull(clienteVerificacao);

    }
}
