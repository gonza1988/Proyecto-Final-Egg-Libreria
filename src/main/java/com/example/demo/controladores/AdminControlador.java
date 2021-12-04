/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controladores;

import com.example.demo.entidades.Usuario;
import com.example.demo.servicios.ServicioUsuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Gonza Cozzo
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    
    	@Autowired ServicioUsuario servicioUsuario;
	
	@GetMapping("/dashboard")
	public String inicioAdmin(ModelMap modelo) {
		
		List<Usuario> usuarios = servicioUsuario.todosLosUsuarios();
		
		modelo.put("usuarios", usuarios);
		
		return "inicioAdmin";
	}
	
	@GetMapping("/alta/{id}")
	public String habilitar(ModelMap modelo, @PathVariable String id) {
		try {
			servicioUsuario.habilitar(id);
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible habilitar");
			return "inicioAdmin";
		}
	}
	
	@GetMapping("/baja/{id}")
	public String deshabilitar(ModelMap modelo, @PathVariable String id) {
		try {
			servicioUsuario.deshabilitar(id);
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible deshabilitar");
			return "inicioAdmin";
		}
	}
	
	@GetMapping("/cambiar_rol/{id}")
	public String cambiarRol(ModelMap modelo, @PathVariable String id) {
		try {
			servicioUsuario.cambiarRol(id);
			modelo.put("exito", "Rol modificado con Exito!");
			return "redirect:/admin/dashboard";
		}catch(Exception e) {
			modelo.put("error", "No fue posible reasignar el rol");
			return "inicioAdmin";
		}
	}
	
    
}
