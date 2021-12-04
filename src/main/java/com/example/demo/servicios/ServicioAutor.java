/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.excepciones.WebException;
import com.example.demo.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gonza Cozzo
 */
@Service
public class ServicioAutor {
    
    @Autowired
  private AutorRepositorio autorRepositorio;
    
    @Autowired
  private ServicioLibro servicioLibro;
    
   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Autor registrar(String nombre) throws WebException {
        
    validar(nombre);    
    Autor autor = new Autor();
    
    autor.setNombre(nombre);
    autor.setAlta(Boolean.TRUE);
    
    return autorRepositorio.save(autor);
  }

  /*  @Transactional
  public Autor registrar(Autor autor) throws WebException {
    if (autor.getNombre() == null) {
      throw new WebException("El nombre del Autor no puede ser nulo");
    }
    return autorRepositorio.save(autor);
  }*/
    
  @Transactional(readOnly=true)  
  public List<Autor> listAll() {
    return autorRepositorio.findAll();
  }

  @Transactional(readOnly=true)
  public Autor BuscarPorNombre(String q) {
    return autorRepositorio.buscarPorNombre("%" + q + "%");
  }

  @Transactional(readOnly=true)
  public Autor findById(Autor autor) {
    Optional<Autor> optional = autorRepositorio.findById(autor.getId());
    if (optional.isPresent()) {
      autor = optional.get();
    }
    return autor;
  }

  @Transactional(readOnly=true)
  public Optional<Autor> findById(String id) {
    return autorRepositorio.findById(id);
  }

   @Transactional
  public void delete(Autor autor) {

    autorRepositorio.delete(autor);
  }

  @Transactional
  public void deleteById(String id) {
    Optional<Autor> optional = autorRepositorio.findById(id);
    if (optional.isPresent()) {
      Autor autor = optional.get();
      servicioLibro.deleteFieldAutor(autor);
      autorRepositorio.delete(autor);
    }

  }
  
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void deshabilitar(String id) throws WebException{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setAlta(Boolean.FALSE);
            autorRepositorio.save(autor);
            
        }else {
            throw new WebException("No se encontro al Autor solicitado ");
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void habilitar(String id) throws WebException{
        
        Optional<Autor> respuesta = autorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Autor autor = respuesta.get();
            autor.setAlta(Boolean.TRUE);
            autorRepositorio.save(autor);
            
        }else {
            throw new WebException("No se encontro al Autor solicitado ");
        }
    }
  
    public void validar(String nombre) throws WebException {

        if (nombre == null || nombre.isEmpty()) {
            throw new WebException("El nombre no puede ser nulo!");
        }
    }
  
}
