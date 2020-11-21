package com.ecommerce.listener;

import lombok.extern.java.Log;

import javax.persistence.PostLoad;

@Log
public class GenericoListener {

    @PostLoad
    public void logCarregamento(Object obj){
        log.info("Entidade " + obj.getClass().getSimpleName() + " foi carregada.");
    }
}
