package br.inatel.labs.labrest.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception que representa um curso não encontrado pelo ID informao
 *
 * @author Gabriel Pivoto
 * @since 10 set 2022
 */
public class CursoNaoEncontradoException extends ResponseStatusException {


    public CursoNaoEncontradoException(Long cursoId) {
        super(HttpStatus.NOT_FOUND, String.format("Nenhum curso encontrado com o ID [%s]", cursoId));
    }
}
