package br.inatel.labs.labrest.server.controller;

import br.inatel.labs.labrest.server.model.Curso;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CursoControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void deveListarCursos(){
        webTestClient.get()
                .uri("/curso")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .returnResult();
    }

    @Test
    void dadoCursoIdValido_quandoGetCursoPeloId_entaoRespondeComCursoValido(){
        Long cursoIdValido = 1L;

        Curso cursoRespondido = webTestClient.get()
                .uri("/curso/" + cursoIdValido)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Curso.class)
                .returnResult()
                .getResponseBody();

        assertNotNull(cursoRespondido);
        assertEquals(cursoRespondido.getId(),cursoIdValido);

        //assertThat(cursoRespondido).isNotNull();
        //assertThat(cursoIdValido).isEqualTo(cursoRespondido.getId());
    }

    @Test
    void dadoCursoIdInvalido_quandoGetCursoPeloId_entaoRespondeComStatusNotFound(){
        Long cursoIdInvalido = 99L;

        webTestClient.get()
                .uri("/curso/" + cursoIdInvalido)
                .exchange()
                .expectStatus().isNotFound();
    }


    /**
     * EX1 POST
     */
    @Test
    void dadoNovoCurso_quandoPostCurso_entaoRespondeComStatusCreatedECursoValido(){

        Curso novoCurso = new Curso();
        novoCurso.setDescricao("Testes");
        novoCurso.setCargaHoraria(120);

        Curso cursoRespondido = webTestClient.post()
                .uri("/curso")
                .bodyValue(novoCurso)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Curso.class)
                .returnResult().getResponseBody();

        assertNotNull(cursoRespondido);
        assertNotNull(cursoRespondido.getId());

    }

    /**
     * EX2 PUT
     */
    @Test
    void dadoCursoIdValido_quandoPutCurso_entaoBodyMustBeEmpty(){

        Curso createdCourse = new Curso();
        createdCourse.setId(1000L);
        createdCourse.setDescricao("Testes");
        createdCourse.setCargaHoraria(120);

        webTestClient
                .put()
                .uri("/curso")
                .bodyValue(createdCourse)
                .exchange()
                .expectBody().isEmpty();
    }

    /**
     * EX3 DELETE CASE 1
     */
    @Test
    void dadoCursoIdValido_quandoDeleteCursoPeloId_entaoExpectedBodyIsEmpty(){

        Long courseIdToBeRemoved = 1000L;

        webTestClient
                .delete()
                .uri("/curso/" + courseIdToBeRemoved)
                .exchange()
                .expectBody().isEmpty();
    }

    /**
     * EX4 DELETE CASE 2
     */
    @Test
    void dadoCursoIdInvalido_quandoDeleteCursoPeloId_entaoIdNotFound(){

        Long courseIdToBeRemoved = 100000000000L;

        webTestClient
                .delete()
                .uri("/curso/" + courseIdToBeRemoved)
                .exchange()
                .expectStatus().isNotFound();
    }

    /**
     * CORREÇÃO PROFESSOR
     */
    @Test
    void dadoCursoExistente_quandoPutCurso_entaoRespondeComStatusAcceptedECorpoVazio(){
        Curso cursoExistente = new Curso();
        cursoExistente.setId(1L);
        cursoExistente.setDescricao("Descricao Atualizada");
        cursoExistente.setCargaHoraria(111);

        webTestClient.put()
                .uri("/curso")
                .bodyValue(cursoExistente)
                .exchange()
                .expectStatus()
                .isAccepted()
                .expectBody()
                .isEmpty();
    }

    @Test
    void dadoCursoExistente_quandoDeletePeloId_entaoRespondeComStatusNoContentECorpoVazio(){
        Long cursoIdValido = 2L;

        webTestClient.delete()
                .uri("/curso/" + cursoIdValido)
                .exchange()
                .expectStatus()
                .isNoContent()
                .expectBody()
                .isEmpty();
    }

    @Test
    void dadoCursoExistente_quandoDeleteCursoPeloId_entaoRespondeComStatusNotFound(){
        Long cursoIdValido = 99L;

        webTestClient.delete()
                .uri("/curso/" + cursoIdValido)
                .exchange()
                .expectStatus()
                .isNotFound();
    }
}