package com.sv.springboot.jpa.bill.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity // Data Persistence
@Table(name="clientes") // Table name
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L; // Serial default
	
	@Id // Table key
	@GeneratedValue(strategy=GenerationType.IDENTITY) // auto increment
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String apellido;
	
	@NotEmpty
	private String edad;
	
	@NotEmpty @Email
	private String correo;
	
	private String foto;
	
	@NotNull
	@Column(name="creado_el")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date creadoEl;
	
	@OneToMany(mappedBy="cliente", fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	private List<Factura> facturas;
	
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

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getCreadoEl() {
		return creadoEl;
	}

	public void setCreadoEl(Date creadoEl) {
		this.creadoEl = creadoEl;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}

	public void addFactura(Factura factura) {
		this.facturas.add(factura);
	}

	public Cliente() {
		this.facturas = new ArrayList<Factura>();
	}
}