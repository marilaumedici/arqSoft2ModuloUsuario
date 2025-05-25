package com.arqsoft.medici.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UsuarioDTO {

	@Schema(description = "Nombre completo del usuario",example = "Ana" )
	private String nombre;
	@Schema(description = "Apellido completo del usuario",example = "López" )
	private String apellido;
	@Schema(description = "Email del usuario",example = "analopez@gmail.com", nullable = false )
	private String mail;
	
	public UsuarioDTO(String nombre, String apellido, String mail) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.mail = mail;
	}
	
	public UsuarioDTO() {}
	
	
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
	
	
}
