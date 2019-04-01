package com.sv.springboot.jpa.bill.app.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> {
	
	private String url;
	
	private Page<T> page;
	
	private Integer totalPagina;
	
	private Integer numElementosPorPagina;
	
	private Integer paginaActual;
	
	private List<PageItem> paginas;
	
	public String getUrl() {
		return url;
	}

	public Integer getTotalPagina() {
		return totalPagina;
	}

	public Integer getPaginaActual() {
		return paginaActual;
	}

	public List<PageItem> getPaginas() {
		return paginas;
	}

	public PageRender(String url, Page<T> page) {
		
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		
		this.numElementosPorPagina = page.getSize();
		this.totalPagina = page.getTotalPages();
		this.paginaActual = page.getNumber()+1;
		
		Integer desde, hasta;
		
		if(this.totalPagina <= this.numElementosPorPagina) {
			desde = 1;
			hasta = this.totalPagina;
		} else {
			if(this.paginaActual <= this.numElementosPorPagina/2) {
				desde = 1;
				hasta = this.numElementosPorPagina;
			} else if(this.paginaActual >= this.totalPagina - this.numElementosPorPagina/2) {
				desde = this.totalPagina - this.numElementosPorPagina +1;
				hasta = this.numElementosPorPagina;
			} else {
				desde = this.paginaActual - this.numElementosPorPagina/2;
				hasta = this.numElementosPorPagina;
			}
		}
		
		for(int i=0; i < hasta; i++) {
			paginas.add(new PageItem(desde + i, this.paginaActual == desde + i));
		}
	}
	
	public boolean isFirst() {
		return this.page.isFirst();
	}
	public boolean isLast() {
		return this.page.isLast();
	}
	public boolean isHasNext() {
		return this.page.hasNext();
	}
	public boolean isHasPrevious() {
		return this.page.hasPrevious();
	}
	
}
