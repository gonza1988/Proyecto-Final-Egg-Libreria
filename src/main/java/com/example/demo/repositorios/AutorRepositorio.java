/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.repositorios;

import com.example.demo.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gonza Cozzo
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String>{
    
    @Query("SELECT c FROM Autor c WHERE c.nombre = :nombre ")
    public Autor buscarPorNombre(@Param ("nombre") String nombre);
    
}
