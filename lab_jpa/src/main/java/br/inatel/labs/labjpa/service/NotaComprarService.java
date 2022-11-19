package br.inatel.labs.labjpa.service;

import br.inatel.labs.labjpa.entity.NotaCompra;
import br.inatel.labs.labjpa.entity.NotaCompraItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class NotaComprarService {
    @PersistenceContext
    private EntityManager em;

    //1.Nota Compra

    public NotaCompra salvarNotaCompra(NotaCompra notaCompra){
        return em.merge(notaCompra);
    }

    public NotaCompra buscarNotaCompraPeloId(Long id){
        return em.find(NotaCompra.class,id);
    }

    public List<NotaCompra> listarNotaCompra(){
        return em.createQuery("select n from NotaCompra n", NotaCompra.class).getResultList();
    }

    //2.Nota Compra Item

    public NotaCompraItem salvarNotaCompraItem(NotaCompraItem notaCompraItem){
        return em.merge(notaCompraItem);
    }

    public NotaCompraItem buscarNotaCompraItemPeloId(Long id){
        return em.find(NotaCompraItem.class,id);
    }

    public List<NotaCompraItem> listarNotaCompraItem(){
        return em.createQuery("select i from NotaCompraItem i", NotaCompraItem.class).getResultList();
    }

    //PROVOCAR PROXY
    public NotaCompra buscarNotaCompraPeloIdComListaItem(Long id){
        NotaCompra nota =  em.find(NotaCompra.class,id);
        int tamanho = nota.getListaNotaCompraItem().size(); //provocando o proxy para buscar a lista de itens
        return nota;
    }
}
