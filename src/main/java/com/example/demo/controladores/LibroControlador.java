/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.entidades.Editorial;
import com.example.demo.entidades.Libro;
import com.example.demo.repositorios.AutorRepositorio;
import com.example.demo.repositorios.EditorialRepositorio;
import com.example.demo.servicios.ServicioEditorial;
import com.example.demo.servicios.ServicioLibro;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Gonza Cozzo
 */
@Controller
@RequestMapping("/libro")
public class LibroControlador {
    
     @Autowired
    private ServicioLibro servicioLibro;
     
     @Autowired
    private AutorRepositorio autorRepositorio;
     
     @Autowired
    private EditorialRepositorio editorialRepositorio;
     
    
    @GetMapping("/lista-libros")
    public String registro(ModelMap modelo) {

        List<Libro> libros = servicioLibro.listAll();
        modelo.put("libros", libros);

        return "libros";
    }

    @GetMapping("/registro") //localhost:8080/autor/registro
    public String formulario(ModelMap modelo) {

        List<Autor> autores = autorRepositorio.findAll();
        modelo.put("autores", autores);
        
        List<Editorial> editoriales = editorialRepositorio.findAll();
        modelo.put("editoriales", editoriales);
        
        return "form-libro";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo, @RequestParam String isbn, @RequestParam String titulo, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam Integer ejemplaresPrestados, @RequestParam String idAutor, @RequestParam String idEditorial) {

        try {
            servicioLibro.registrar(isbn,titulo,anio,ejemplares,ejemplaresPrestados, idAutor, idEditorial);

            modelo.put("exito", "Registro exitoso");

            return "form-libro";

        } catch (Exception e) {

            List<Autor> autores = autorRepositorio.findAll();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialRepositorio.findAll();
            modelo.put("editoriales", editoriales);

            modelo.put("error", e.getMessage());
            modelo.put("isbn", isbn);
            modelo.put("titulo", titulo);
            modelo.put("anio", anio);
            modelo.put("ejemplares", ejemplares);
            modelo.put("ejemplaresPrestados", ejemplaresPrestados);

            return "form-libro";
        }
    }
    
    @GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {
				
		try {
			servicioLibro.deshabilitar(id);
			return "redirect:/libro/lista-libros";
		} catch (Exception e) {
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {
		
		try {
			servicioLibro.habilitar(id);
			return "redirect:/libro/lista-libros";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

}
