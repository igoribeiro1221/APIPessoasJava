package br.com.elotech.pessoa.controller;

import br.com.elotech.pessoa.entity.Contato;
import br.com.elotech.pessoa.entity.Pessoa;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;

@SuppressWarnings("DataFlowIssue")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class PessoaControllerTest {
    @Autowired
    TestRestTemplate webClient;

    @Test
    @Order(1)
    void readSemExistirUsuario() {
        ResponseEntity<Pessoa> response = webClient.getForEntity("/pessoa/90", Pessoa.class);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    @Order(2)
    void listSemResultado() {
        ResponseEntity<Pessoa> response = webClient.getForEntity("/pessoa/", Pessoa.class);
        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    @Order(3)
    void createComDadosCorretos() {
        Pessoa pessoaValida = new Pessoa(null,
                "Nome",
                "12345678909",
                LocalDate.of(1980, Month.JANUARY, 1),
                List.of(new Contato(null, "contato 1", "11987654321", "email@gmail.com")));

        ResponseEntity<Pessoa> resposta = webClient.postForEntity("/pessoa", pessoaValida, Pessoa.class);
        assertThat(resposta.getStatusCode().value()).isEqualTo(201);
        assertThat(resposta.getBody().getId()).isPositive();
        assertThat(resposta.getBody().getContatos().get(0).getId()).isPositive();
    }

    @Test
    @Order(4)
    void createComDadosIncorretos() {
        Pessoa cpfInvalido = new Pessoa(null,
                "Nome",
                "11111111111",
                LocalDate.of(1980, Month.JANUARY, 1),
                List.of(new Contato(null, "contato 1", "11987654321", "email@gmail.com")));

        ResponseEntity<Pessoa> resposta = webClient.postForEntity("/pessoa", cpfInvalido, Pessoa.class);
        assertThat(resposta.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    @Order(5)
    void updateComSucesso() {
        Pessoa pessoa1 = new Pessoa(1L,
                "Nome alterado",
                "12345678909",
                LocalDate.of(1980, Month.JANUARY, 1),
                List.of(new Contato(1L, "contato alterado", "11987654321", "email@gmail.com")));

        ResponseEntity<Pessoa> resposta = webClient.exchange("/pessoa",
                PUT,
                new HttpEntity<>(pessoa1),
                Pessoa.class);
        assertThat(resposta.getStatusCode().value()).isEqualTo(200);
        assertThat(resposta.getBody().getNome()).isEqualTo("Nome alterado");
        assertThat(resposta.getBody().getContatos().get(0).getNome()).isEqualTo("contato alterado");
    }

    @Test
    @Order(6)
    void updateComDadosInvalidos() {
        Pessoa pessoa1 = new Pessoa(1L,
                "Nome alterado email errado",
                "11111111111",
                LocalDate.of(1980, Month.JANUARY, 1),
                List.of(new Contato(1L, "contato alterado", "119876543", "email")));

        ResponseEntity<Pessoa> resposta = webClient.exchange("/pessoa",
                PUT,
                new HttpEntity<>(pessoa1),
                Pessoa.class);
        assertThat(resposta.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    @Order(7)
    void listComResultado() {
        ResponseEntity<Pessoa> response = webClient.getForEntity("/pessoa/1", Pessoa.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    @Order(8)
    void deleteDoPrimeiro() {
        ResponseEntity<Object> resposta = webClient.exchange("/pessoa/{id}",
                DELETE,
                null,
                (Class<Object>) null,
                Map.of("id", 1));
        assertThat(resposta.getStatusCode().value()).isEqualTo(200);
    }


}