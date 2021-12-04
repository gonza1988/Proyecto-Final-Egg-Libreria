/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Editorial;
import com.example.demo.servicios.ServicioEditorial;
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
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private ServicioEditorial servicioEditorial;

    
    
    @GetMapping("/lista-editoriales")
    public String registro(ModelMap modelo) {

        List<Editorial> editoriales = servicioEditorial.listAll();
        modelo.put("editoriales", editoriales);

        return "editoriales";
    }

    @GetMapping("/registro") //localhost:8080/editorial/registro
    public String formulario() {

        return "form-editorial";
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo, @RequestParam String nombre) {

        try {
            servicioEditorial.registrar(nombre);

            modelo.put("exito", "Registro exitoso");

            return "form-editorial";

        } catch (Exception e) {

            modelo.put("error", e.getMessage());
            return "form-editorial";
        }
    }
    
    @GetMapping("/baja/{id}")
	public String baja(@PathVariable String id) {
				
		try {
			servicioEditorial.deshabilitar(id);
			return "redirect:/editorial/lista-editoriales";
		} catch (Exception e) {
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/alta/{id}")
	public String alta(@PathVariable String id) {
		
		try {
			servicioEditorial.habilitar(id);
			return "redirect:/editorial/lista-editoriales";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

}
