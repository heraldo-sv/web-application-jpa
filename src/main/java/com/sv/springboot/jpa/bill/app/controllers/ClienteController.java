package com.sv.springboot.jpa.bill.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sv.springboot.jpa.bill.app.models.service.IUploadFileService;
import com.sv.springboot.jpa.bill.app.models.entity.Cliente;
import com.sv.springboot.jpa.bill.app.models.service.IClienteService;
import com.sv.springboot.jpa.bill.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@RequestMapping(value = "/cliente/list", method = RequestMethod.GET)
	public String list(@RequestParam(name="page",defaultValue="0") Integer page, Model model) {
		
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/cliente/list",clientes);
		
		model.addAttribute("titulo","Listado de clientes");
		model.addAttribute("grupoActual","catalogo");
		model.addAttribute("paginaActual","cliente");
		
		model.addAttribute("clientes",clientes);
		model.addAttribute("page",pageRender);
		
		return "views/cliente/list";
	}
	
	@RequestMapping(value="/cliente/show/{id}")
	public String show(@PathVariable(value="id") Long id, Model model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/views/cliente/list";
		}
		model.addAttribute("titulo", "Detalle del cliente: ");
		model.addAttribute("cliente", cliente);
		
		model.addAttribute("grupoActual","catalogo");
		model.addAttribute("paginaActual","cliente");
		
		return "views/cliente/show";
	}
	
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> lookFhoto(@PathVariable String filename) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}
	
	@RequestMapping(value="/cliente/form")
	public String create(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de nuevo cliente");
		
		model.put("grupoActual","catalogo");
		model.put("paginaActual","cliente");
		
		return "views/cliente/form";
	}
	
	@RequestMapping(value = "/cliente/form", method = RequestMethod.POST)
	public String save(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("inputFile") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

		String mensajeFlash = (cliente.getId() != null) ? "Cliente editado con exito" : "Cliente creado con exito";

		model.addAttribute("titulo", "Formulario de cliente");
		if (result.hasErrors()) {
			return "views/cliente/form";
		}

		if (!foto.isEmpty()) {

			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadFileService.delete(cliente.getFoto());
			}

			String uniqueFileName = null;

			try {
				uniqueFileName = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			flash.addFlashAttribute("info", "Has subido correctamente" + uniqueFileName);
			cliente.setFoto(uniqueFileName);
		}

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:/cliente/list";
	}
	
	@RequestMapping(value = "/cliente/form/{id}")
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El ID del cliente no existe en la base de datos");
				return "redirect:/cliente/list";
			}
		} else {
			flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
			return "redirect:/cliente/list";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de edición cliente");
		return "views/cliente/form";
	}
	
	@RequestMapping(value = "/cliente/delete/{id}")
	public String drop(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		
		if (id > 0) {
			
			Cliente cliente = clienteService.findOne(id);
			
			if(cliente != null) {
				if(cliente.getFoto().length() > 0) {
					if (uploadFileService.delete(cliente.getFoto())) {
						flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito");
					} else {
						flash.addFlashAttribute("error", "La imagen no pudo ser eliminada.");
						return "redirect:/cliente/list";
					}
				}
				clienteService.delete(id);
				flash.addFlashAttribute("success", "Cliente eliminado con exito");
			} else {
				flash.addFlashAttribute("error", "El ID del cliente no puede ser cero!");
				return "redirect:/cliente/list";
			}	
		}
		return "redirect:/cliente/list";
	}
	
}
