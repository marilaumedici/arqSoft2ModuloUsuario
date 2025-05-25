package com.arqsoft.medici.infrastructure.rest.puertos;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.arqsoft.medici.infrastructure.rest.dto.UsuarioDTO;
import com.arqsoft.medici.infrastructure.rest.dto.UsuarioResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;



@RequestMapping("/usuario")
public interface UsuarioController {
	
    @PostMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "crear_usuario", description = "Crea un usuario comprador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario creado"),
        @ApiResponse(responseCode = "400", description = "Usuario existente",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
        @ApiResponse(responseCode = "500", description = "Error de servicio",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
    })
	public void crearUsuario(@RequestBody UsuarioDTO request);
    
    @PutMapping(path = "/", 
    consumes = MediaType.APPLICATION_JSON_VALUE, 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "modificar_usuario", description = "Modifica un usuario comprador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario modificado"),
        @ApiResponse(responseCode = "400", description = "Usuario no modificado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
        @ApiResponse(responseCode = "500", description = "Error de servicio",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
    })
	public void modificarUsuario(@RequestBody UsuarioDTO request);
    
    @DeleteMapping(path = "/{email}", 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "borrar_usuario", description = "Borra un usuario logicamente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Usuario borrado"),
        @ApiResponse(responseCode = "400", description = "Usuario no borrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
        @ApiResponse(responseCode = "500", description = "Error de servicio",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
    })
	public void eliminarUsuario( @Parameter(description = "Email del usuario a borrar", example = "analopez@gmail.com") @PathVariable(value = "email") String mail);
    
    @GetMapping(path = "/{email}", 
    produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "obtener_usuario", description = "Retorna un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = UsuarioResponseDTO.class)
	        )
	    ),
        @ApiResponse(responseCode = "400", description = "Usuario no encontrado",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
        @ApiResponse(responseCode = "500", description = "Error de servicio",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ResponseStatusException.class)
	        )
	    ),
        
    })
	public UsuarioResponseDTO obtenerUsuario(@Parameter(description = "Email del usuario a buscar", example = "analopez@gmail.com" )@PathVariable(value = "email") String mail);

}
