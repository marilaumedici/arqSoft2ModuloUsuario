package com.arqsoft.medici.domain.dto;

import com.arqsoft.medici.domain.utils.UsuarioEstado;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioResponseDTO {
	
	@Schema(description = "Nombre completo del usuario",example = "Ana" )
	private String nombre;
	@Schema(description = "Apellido completo del usuario",example = "López" )
	private String apellido;
	@Schema(description = "Email del usuario",example = "analopez@gmail.com")
	private String mail;
	@Schema(description = "Estado del usuario",example = "[ACTIVO,BORRADO]")
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
