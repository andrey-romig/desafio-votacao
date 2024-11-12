package br.com.cooperativa.client;

import org.springframework.stereotype.Component;

@Component
public class ClientValidadorCPFFallback implements ClientValidadorCPF {

    @Override
    public boolean isCpfValido(String cpf) {
        return Math.random() < 0.5; //Retorna aleatoriamente true ou false
    }
}
