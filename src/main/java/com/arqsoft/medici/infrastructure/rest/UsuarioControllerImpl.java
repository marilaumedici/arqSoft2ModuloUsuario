package com.arqsoft.medici.infrastructure.rest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.arqsoft.medici.application.UsuarioService;
import com.arqsoft.medici.domain.Usuario;
import com.arqsoft.medici.domain.dto.UsuarioDomainDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.UsuarioExistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.infrastructure.rest.dto.UsuarioDTO;
import com.arqsoft.medici.infrastructure.rest.dto.UsuarioResponseDTO;
import com.arqsoft.medici.infrastructure.rest.puertos.UsuarioController;


@RestController
public class UsuarioControllerImpl implements UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	private ModelMapper modelMapper = new ModelMapper();
	
	@Override
	public void crearUsuario(UsuarioDTO dto) {
    	
    	try {
    		
    		UsuarioDomainDTO request = modelMapper.map(dto, UsuarioDomainDTO.class);
			usuarioService.crearUsuario(request);

			
		} catch (UsuarioExistenteException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario "+dto.getMail()+" ya existe.", e);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

		} catch (FormatoEmailInvalidoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El email debe poseer un formato valido.", e);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}	
    }
    
	@Override
	public void modificarUsuario(UsuarioDTO dto) {
    	
			try {
				
				UsuarioDomainDTO request = modelMapper.map(dto, UsuarioDomainDTO.class);
				usuarioService.modificarUsuario(request);
				
			} catch (UsuarioNoEncontradoException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el usuario "+dto.getMail()+".", e);

			} catch (InternalErrorException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
				
			}
    }
	
	@Override
	public void eliminarUsuario(String mail) {
    	
			try {
				usuarioService.eliminarUsuario(mail);
				
			} catch (InternalErrorException e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);

			} catch (UsuarioNoEncontradoException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el usuario "+mail+".", e);

			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
				
			}
    }
	
	@Override
	public UsuarioResponseDTO obtenerUsuario(String mail) {
		
		UsuarioResponseDTO dto = new UsuarioResponseDTO();

		try {
			Usuario usuario = usuarioService.obtenerUsuarioByID(mail);
			dto.setApellido(usuario.getApellido());
			dto.setEstado(usuario.getEstado());
			dto.setMail(usuario.getMail());
			dto.setNombre(usuario.getNombre());
			
		} catch (UsuarioNoEncontradoException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontro el usuario "+mail+".", e);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Hubo un error, por favor vuelva a probar mas adelante.", e);
			
		}
		return dto;
		
	}
	

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}



}
