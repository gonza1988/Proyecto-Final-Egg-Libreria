/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Editorial;
import com.example.demo.excepciones.WebException;
import com.example.demo.repositorios.EditorialRepositorio;
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
public class ServicioEditorial {
    
       @Autowired
  private EditorialRepositorio editorialRepositorio;
       
       @Autowired
  private ServicioLibro servicioLibro;
    
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Editorial registrar(String nombre) throws WebException{
        
        validar(nombre);
        
    Editorial editorial = new Editorial();
    
    editorial.setNombre(nombre);
    editorial.setAlta(Boolean.TRUE);
    
    return editorialRepositorio.save(editorial);
  }

   /* @Transactional
  public Editorial registrar(Editorial editorial) throws WebException {
    if (editorial.getNombre() == null) {
      throw new WebException("El nombre de la Editorial no puede ser nulo");
    }
    return editorialRepositorio.save(editorial);
  }*/
  
   @Transactional(readOnly=true)
   public List<Editorial> listAll() {
    return editorialRepositorio.findAll();
  }

   @Transactional(readOnly=true)
  public Editorial buscarPorNombre(String q) {
    return editorialRepositorio.buscarPorNombre("%" + q + "%");
  }

  @Transactional(readOnly=true)
  public Editorial findById(Editorial editorial) {
    Optional<Editorial> optional = editorialRepositorio.findById(editorial.getId());
    if (optional.isPresent()) {
      editorial = optional.get();
    }
    return editorial;
  }

  public Optional<Editorial> findById(String id) {
    return editorialRepositorio.findById(id);
  }
  
   @Transactional
  public void delete(Editorial editorial) {

    editorialRepositorio.delete(editorial);
  }

  @Transactional
  public void deleteById(String id) {
    Optional<Editorial> optional = editorialRepositorio.findById(id);
    if (optional.isPresent()) {
      Editorial editorial = optional.get();
      servicioLibro.deleteFieldEditorial(editorial);
      editorialRepositorio.delete(editorial);
    }

  }
  
  @Transactional
    public void deshabilitar(String id) throws WebException{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.FALSE);
            editorialRepositorio.save(editorial);
            
        }else {
            throw new WebException("No se encontro la Editorial solicitada ");
        }
    }
    
    @Transactional
    public void habilitar(String id) throws WebException{
        
        Optional<Editorial> respuesta = editorialRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Editorial editorial = respuesta.get();
            editorial.setAlta(Boolean.TRUE);
            editorialRepositorio.save(editorial);
            
        }else {
            throw new WebException("No se encontro la Editorial solicitada ");
        }
    }
  

     public void validar(String nombre) throws WebException {

        if (nombre == null || nombre.isEmpty()) {
            throw new WebException("El nombre no puede ser nulo!");
        }
    }
}
