/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gonza Cozzo
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Query("SELECT c FROM Libro c WHERE c.titulo = :titulo ")
    public Libro buscarPorTitulo(@Param ("titulo") String titulo);
    
    @Query("SELECT c FROM Libro c WHERE c.autor.nombre = :q ORDER BY c.titulo DESC")
    public List<Libro> findAllByAutor(@Param ("q") String q);
    
    @Query("SELECT c FROM Libro c WHERE c.editorial.nombre = :q ORDER BY c.titulo DESC")
    public List<Libro> findAllByEditorial(@Param ("q") String q);
}
