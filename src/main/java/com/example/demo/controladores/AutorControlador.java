/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Autor;
import com.example.demo.excepciones.WebException;
import com.example.demo.servicios.ServicioAutor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Gonza Cozzo
 */
@Controller
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private ServicioAutor servicioAutor;

    @GetMapping("/lista-autores")
    public String registro(ModelMap modelo) {

        List<Autor> autores = servicioAutor.listAll();
        modelo.put("autores", autores);

        return "autores";
    }

    @GetMapping("/registro") //localhost:8080/autor/registro
    public String formulario() {

        return "form-autor";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo, @RequestParam String nombre) {

        try {
            servicioAutor.registrar(nombre);

            modelo.put("exito", "Registro exitoso");

            return "form-autor";

        } catch (Exception e) {

            modelo.put("error", e.getMessage());
            return "form-autor";
        }
    }
    
    @GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {
				
		try {
			servicioAutor.deshabilitar(id);
			return "redirect:/autor/lista-autores";
		} catch (Exception e) {
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {
		
		try {
			servicioAutor.habilitar(id);
			return "redirect:/autor/lista-autores";
		} catch (Exception e) {
			return "redirect:/";
		}
	}
	

}
