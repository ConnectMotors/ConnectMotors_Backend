package br.com.ConnectMotors.Entidade.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import br.com.ConnectMotors.Entidade.Model.Anuncio.CepResponse;

@RestController
@RequestMapping("/cep")
public class CepController {

    @GetMapping("/{cep}")
    public CepResponse buscarCep(@PathVariable String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        RestTemplate restTemplate = new RestTemplate();
        CepResponse response = restTemplate.getForObject(url, CepResponse.class);

        if (response == null || response.getLocalidade() == null) {
            throw new IllegalArgumentException("CEP inválido.");
        }
        return response;
    }
}