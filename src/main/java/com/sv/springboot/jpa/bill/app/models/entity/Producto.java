package com.sv.springboot.jpa.bill.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="productos")
public class Producto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id // Table key
	@GeneratedValue(strategy=GenerationType.IDENTITY) // auto increment
	private Long id;
	
	private String nombre;
	
	private Double precio;
	
	@Column(name="creado_el")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date creadoEl;
	
	@PrePersist
	public void prePersist() {
		this.creadoEl = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Date getCreadoEl() {
		return creadoEl;
	}

	public void setCreadoEl(Date creadoEl) {
		this.creadoEl = creadoEl;
	}
	
}
