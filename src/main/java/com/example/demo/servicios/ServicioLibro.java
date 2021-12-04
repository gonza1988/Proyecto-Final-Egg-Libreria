/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.servicios;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import com.example.demo.excepciones.WebException;
import com.example.demo.repositorios.AutorRepositorio;
import com.example.demo.repositorios.EditorialRepositorio;
import com.example.demo.repositorios.LibroRepositorio;
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
public class ServicioLibro {

    @Autowired
    private LibroRepositorio libroRepositorio;
    
     @Autowired
    private AutorRepositorio autorRepositorio;
     
      @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private ServicioAutor servicioAutor;

    @Autowired
    private ServicioEditorial servicioEditorial;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Libro registrar(String isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String idAutor, String idEditorial) throws WebException {
       
        Autor autor = autorRepositorio.getById(idAutor);
        Editorial editorial = editorialRepositorio.getById(idEditorial);
        
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados, autor, editorial);
        
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setEjemplaresRestantes(libro.getEjemplares() - libro.getEjemplaresPrestados());
        libro.setAlta(Boolean.TRUE);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        return libroRepositorio.save(libro);
    }

    @Transactional(readOnly=true)
    public List<Libro> listAll() {
        return libroRepositorio.findAll();
    }

    @Transactional(readOnly=true)
    public Libro buscarPorTitulo(String q) {
        return libroRepositorio.buscarPorTitulo("%" + q + "%");
    }

    @Transactional(readOnly=true)
    public List<Libro> listAllbyEditorial(String nombre) {
        return libroRepositorio.findAllByEditorial(nombre);
    }

    @Transactional(readOnly=true)
    public List<Libro> listAllbyAutor(String nombre) {
        return libroRepositorio.findAllByAutor(nombre);
    }

    @Transactional(readOnly=true)
    public Optional<Libro> findById(String id) {
        return libroRepositorio.findById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void delete(Libro libro) {
        libroRepositorio.delete(libro);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void deleteFieldAutor(Autor autor) {
        List<Libro> libros = listAllbyAutor(autor.getNombre());
        for (Libro libro : libros) {
            libro.setAutor(null);
        }
        libroRepositorio.saveAll(libros);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void deleteFieldEditorial(Editorial editorial) {
        List<Libro> libros = listAllbyEditorial(editorial.getNombre());
        for (Libro libro : libros) {
            libro.setEditorial(null);
        }
        libroRepositorio.saveAll(libros);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void deleteById(String id) {
        Optional<Libro> optional = libroRepositorio.findById(id);
        if (optional.isPresent()) {
            libroRepositorio.delete(optional.get());

        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void deshabilitar(String id) throws WebException {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.setAlta(Boolean.FALSE);
            libroRepositorio.save(libro);

        } else {
            throw new WebException("No se encontro el Libro solicitado ");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void habilitar(String id) throws WebException {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Libro libro = respuesta.get();
            libro.setAlta(Boolean.TRUE);
            libroRepositorio.save(libro);

        } else {
            throw new WebException("No se encontro el Libro solicitado ");
        }
    }

    public void validar(String isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, Autor autor, Editorial editorial) throws WebException {
        if (isbn.isEmpty() || isbn == null || isbn.length() < 9) {
            throw new WebException(" El ISBN no puede contener menos de 9 digitos");
        }

        if (titulo.isEmpty() || titulo == null) {
            throw new WebException(" El titulo no puede estar vacío");
        }
        if (anio == null || anio < 1440) {
            throw new WebException(" Ingrese un Año valido");
        }
        if (ejemplares < 0) {
            throw new WebException("Ingrese cantidad de ejmplares valido");
        }
        if (ejemplaresPrestados > ejemplares || ejemplaresPrestados < 0) {
            throw new WebException("No se pueden prestar Libros que no existen");
        }
        if (autor == null) {
            throw new WebException(" El autor del libro no puede ser nulo");
        }
        if (editorial == null) {
            throw new WebException(" La editorial del libro no puede ser nulo");
        }

    }
}
