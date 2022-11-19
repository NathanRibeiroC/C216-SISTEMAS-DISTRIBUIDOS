package br.inatel.labs.labrest.server.service;

import br.inatel.labs.labrest.server.model.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
//1hr 10min
/**
 * Serviço com método de negócio para curso
 * @author Nathan Ribeiro
 * @since 19 set 2022
 */
@Service
public class CursoService {

    private List<Curso> listaDeCursos = new ArrayList<>();

    @PostConstruct
    public void setup() {
        Curso c1 = new Curso(1L, "Curso com Spring Boot", 20);
        Curso c2 = new Curso(2L, "Programacao Java 11", 80);
        Curso c3 = new Curso(3L, "Dominando a IDE IntelliJ", 120);
        listaDeCursos.add(c1);
        listaDeCursos.add(c2);
        listaDeCursos.add(c3);
    }

    public List<Curso> pesquisarCurso(){
        return listaDeCursos;
    }

    public Optional<Curso> buscarCursoPeloId(Long cursoId){
        return listaDeCursos.stream().filter(c -> c.getId().equals(cursoId)).findFirst();
    }

    public Curso criarCurso(Curso curso){
        curso.setId(new Random().nextLong());
        listaDeCursos.add(curso);
        return curso;
    }

    public void atualizarCurso(Curso curso){
        listaDeCursos.remove(curso);
        listaDeCursos.add(curso);
    }

    public void removerCurso(Curso cursoASerRemovido){
        listaDeCursos.remove(cursoASerRemovido);
    }

    /**
     * Pesquisa cursos pelo fragmento da descrição ignorando espaços em branco e letter case
     * @param fragDescricao
     * @return
     */
    public List<Curso> pesquisarCursoPeloFragmentoDescricao(String fragDescricao){
        if(Objects.isNull(fragDescricao) || fragDescricao.isBlank()){
            return List.of();
        }
        return this.listaDeCursos.stream()
                .filter(c -> c.getDescricao().trim().toLowerCase().contains(fragDescricao.trim().toLowerCase()))
                .collect(Collectors.toList());
    }
}
