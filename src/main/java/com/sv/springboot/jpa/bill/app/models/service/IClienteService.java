package com.sv.springboot.jpa.bill.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sv.springboot.jpa.bill.app.models.entity.Cliente;
import com.sv.springboot.jpa.bill.app.models.entity.Producto;

public interface IClienteService {
public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);

	public void save(Cliente cliente);

	public Cliente findOne(Long id);

	public void delete(Long id);
	
	public List<Producto> findByNombre(String term);
}
