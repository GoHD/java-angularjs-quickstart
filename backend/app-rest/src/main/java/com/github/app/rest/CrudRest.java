package com.github.app.rest;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.app.common.exception.EntidadeNaoEncontradaException;
import com.github.app.model.entity.IEntity;
import com.github.app.model.service.ServicoGenerico;

public abstract class CrudRest<E extends IEntity<ID> , ID extends Serializable> {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response busca(@PathParam("id") final ID id) {
        try {
            E entidade  = getServico().findById(id);
            return Response.status(Status.OK).entity(entidade).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscaTodos() {
        try {
            List<E> entidades = getServico().findAll();
            return Response.status(Status.OK).entity(entidades).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(E entidade) {
         getServico().add(entidade);
         String resultado = "Cadastrado com sucesso";
         return Response.status(Status.CREATED).entity(resultado).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(E entidade) {
        getServico().update(entidade);
        String resultado = "Atualizado com sucesso";
        return Response.status(Status.OK).entity(resultado).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response remover(@PathParam("id") final ID id) {
        getServico().delete(id);
        String resultado = "Deletado com sucesso";
        return Response.status(Status.OK).entity(resultado).build();
    }
    
    protected abstract ServicoGenerico<E, ID> getServico();
    
}
