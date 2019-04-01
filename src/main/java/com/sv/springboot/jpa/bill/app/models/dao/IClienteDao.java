package com.sv.springboot.jpa.bill.app.models.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.sv.springboot.jpa.bill.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
	
}
