package com.arqsoft.medici.domain.dto;

import com.arqsoft.medici.domain.utils.UsuarioEstado;

public class UsuarioResponseDTO {
	
	private String nombre;
	private String apellido;
	private String mail;
	private UsuarioEstado estado;

	
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public UsuarioEstado getEstado() {
		return estado;
	}
	public void setEstado(UsuarioEstado estado) {
		this.estado = estado;
	}

}
