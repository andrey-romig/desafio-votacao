package br.com.cooperativa.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "validadorCPF", url = "https://validador-cpf.com")
@Component
public interface ClientValidadorCPF {

    @GetMapping("/valida")
    boolean isCpfValido(@RequestParam("cpf") String cpf);
}
