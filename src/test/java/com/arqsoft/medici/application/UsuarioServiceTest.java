package com.arqsoft.medici.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.arqsoft.medici.domain.Usuario;
import com.arqsoft.medici.domain.dto.UsuarioDomainDTO;
import com.arqsoft.medici.domain.exceptions.FormatoEmailInvalidoException;
import com.arqsoft.medici.domain.exceptions.InternalErrorException;
import com.arqsoft.medici.domain.exceptions.UsuarioExistenteException;
import com.arqsoft.medici.domain.utils.UsuarioEstado;
import com.arqsoft.medici.infrastructure.persistence.UsuarioRepository;
import com.arqsoft.medici.infrastructure.rest.dto.UsuarioDTO;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	@InjectMocks
	private UsuarioServiceImpl usuarioService;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Captor
	private ArgumentCaptor<Usuario> usuarioCaptor;
	
	private String emailIvalido     = "agussusu";
	private String email    		= "agussusu@gmal.com";
	private String nombre   		= "Agustin";
	private String apellido 		= "Delpane";
	
	private String nombre_otro   		= "Agustina";
	private String apellido_otro 		= "Delpaloma";
	
	@Test
	public void testCrearUsuarioInexistenteOK() throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {

		Optional<Usuario> usuarioOpcional = Optional.empty(); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		
		when(usuarioRepository.insert(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre, apellido, email);
		assertDoesNotThrow(() -> { usuarioService.crearUsuario(request); });
		
		//mockStatic.verify(() -> FormatUtils.isValidEmail(email), times(1));
		verify(usuarioRepository, times(1)).findById(email);
		verify(usuarioRepository).insert(usuarioCaptor.capture());
		//verify(usuarioRepository, times(1)).insert(any(Usuario.class));
		
		Usuario capturado = usuarioCaptor.getValue();

	    assertEquals(email, capturado.getMail());
	    assertEquals(nombre, capturado.getNombre());
	    assertEquals(apellido, capturado.getApellido());
	    assertEquals(UsuarioEstado.ACTIVO, capturado.getEstado());

	}
	
	@Test
	public void testCrearUsuarioExistenteBorradoOK() throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.BORRADO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		
		when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));
		
		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre, apellido, email);
		assertDoesNotThrow(() -> { usuarioService.crearUsuario(request); });
		
		verify(usuarioRepository, times(1)).findById(email);
		verify(usuarioRepository, times(0)).insert(any(Usuario.class));
		verify(usuarioRepository, times(1)).save(any(Usuario.class));
		verify(usuarioRepository).save(usuarioCaptor.capture());
		
		Usuario capturado = usuarioCaptor.getValue();

	    assertEquals(email, capturado.getMail());
	    assertEquals(nombre, capturado.getNombre());
	    assertEquals(apellido, capturado.getApellido());
	    assertEquals(UsuarioEstado.ACTIVO, capturado.getEstado());
		
	}
	
	@Test
	public void testCrearUsuarioExistenteActivo() throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.ACTIVO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
				
		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre, apellido, email);
		assertThrows(UsuarioExistenteException.class, () -> {  usuarioService.crearUsuario(request); });
		
	}
	
	
	@Test
	public void testCrearUsuarioEmailVacio() throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {
				
		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre, apellido, null);
		assertThrows(InternalErrorException.class, () -> {  usuarioService.crearUsuario(request); });
		
	}
	
	@Test
	public void testCrearUsuarioEmailEmpty() throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {
				
		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre, apellido, "");
		assertThrows(InternalErrorException.class, () -> {  usuarioService.crearUsuario(request); });
		
	}

	@Test
	public void testCrearUsuarioEmailInvalido() throws UsuarioExistenteException, InternalErrorException, FormatoEmailInvalidoException {
				
		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre, apellido, emailIvalido);
		assertThrows(FormatoEmailInvalidoException.class, () -> {  usuarioService.crearUsuario(request); });
		//assertDoesNotThrow(() -> { usuarioService.crearUsuario(request); });
		
	}
	
	@Test
	public void testModificarUsuarioActivoOk() {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.ACTIVO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre_otro, apellido_otro, email);		
		assertDoesNotThrow(() -> { usuarioService.modificarUsuario(request); });
		
		verify(usuarioRepository, times(1)).findById(email);
		verify(usuarioRepository, times(1)).save(any(Usuario.class));
		verify(usuarioRepository).save(usuarioCaptor.capture());
		
		Usuario capturado = usuarioCaptor.getValue();

	    assertEquals(email, capturado.getMail());
	    assertEquals(nombre_otro, capturado.getNombre());
	    assertEquals(apellido_otro, capturado.getApellido());
	    assertEquals(UsuarioEstado.ACTIVO, capturado.getEstado());
		
	}
	
	@Test
	public void testModificarUsuarioBorrado() {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.BORRADO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);

		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre_otro, apellido_otro, email);		
		assertThrows(UsuarioNoEncontradoException.class, () -> {  usuarioService.modificarUsuario(request); });

		verify(usuarioRepository, times(1)).findById(email);
		
	}
	
	@Test
	public void testModificarUsuarioInexistente() {
		
		Optional<Usuario> usuarioOpcional = Optional.empty(); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);

		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre_otro, apellido_otro, email);		
		assertThrows(UsuarioNoEncontradoException.class, () -> {  usuarioService.modificarUsuario(request); });

		verify(usuarioRepository, times(1)).findById(email);
		
	}
	
	@Test
	public void testModificarUsuarioMailVacio() {

		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre_otro, apellido_otro, "");		
		assertThrows(InternalErrorException.class, () -> {  usuarioService.modificarUsuario(request); });
		
	}
	
	@Test
	public void testModificarUsuarioMailNull() {

		UsuarioDomainDTO request = new UsuarioDomainDTO(nombre_otro, apellido_otro, null);		
		assertThrows(InternalErrorException.class, () -> {  usuarioService.modificarUsuario(request); });
		
	}
	
	@Test
	public void testEliminarUsuarioActivoOk() {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.ACTIVO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

		assertDoesNotThrow(() -> { usuarioService.eliminarUsuario(email); });
		
		verify(usuarioRepository, times(1)).findById(email);
		verify(usuarioRepository, times(1)).save(any(Usuario.class));
		verify(usuarioRepository).save(usuarioCaptor.capture());
		
		Usuario capturado = usuarioCaptor.getValue();

	    assertEquals(email, capturado.getMail());
	    assertEquals(nombre, capturado.getNombre());
	    assertEquals(apellido, capturado.getApellido());
	    assertEquals(UsuarioEstado.BORRADO, capturado.getEstado());
		
	}
	
	@Test
	public void testEliminarUsuarioBorradoOk() {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.BORRADO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		when(usuarioRepository.save(any(Usuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

		assertDoesNotThrow(() -> { usuarioService.eliminarUsuario(email); });
		
		verify(usuarioRepository, times(1)).findById(email);
		verify(usuarioRepository, times(1)).save(any(Usuario.class));
		verify(usuarioRepository).save(usuarioCaptor.capture());
		
		Usuario capturado = usuarioCaptor.getValue();

	    assertEquals(email, capturado.getMail());
	    assertEquals(nombre, capturado.getNombre());
	    assertEquals(apellido, capturado.getApellido());
	    assertEquals(UsuarioEstado.BORRADO, capturado.getEstado());
		
	}
	
	@Test
	public void testEliminarUsuarioInexsistente() {
		
		Optional<Usuario> usuarioOpcional = Optional.empty(); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);

		assertThrows(UsuarioNoEncontradoException.class, () -> {  usuarioService.eliminarUsuario(email); });

		verify(usuarioRepository, times(1)).findById(email);
		
	}
	
	@Test
	public void testEliminarUsuarioMailVacio() {

		assertThrows(InternalErrorException.class, () -> {  usuarioService.eliminarUsuario(""); });

	}
	
	@Test
	public void testEliminarUsuarioMailNull() {

		assertThrows(InternalErrorException.class, () -> {  usuarioService.eliminarUsuario(null); });

	}
	
	@Test
	public void testObtenerUsuarioExsistente() {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.ACTIVO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		
		assertDoesNotThrow(() -> { 
			
			Usuario user  = usuarioService.obtenerUsuarioByID(email); 
			
			assertEquals(email, user.getMail());
			assertEquals(nombre, user.getNombre());
			assertEquals(apellido, user.getApellido());
			assertEquals(UsuarioEstado.ACTIVO, user.getEstado());
		
		});

	}
	
	@Test
	public void testObtenerUsuarioBorrado() {
		
		Usuario usuarioBD = new Usuario(nombre, apellido, email, UsuarioEstado.BORRADO);
		Optional<Usuario> usuarioOpcional = Optional.of(usuarioBD); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		
		assertThrows(UsuarioNoEncontradoException.class, () -> {  usuarioService.obtenerUsuarioByID(email); });

	}
	
	@Test
	public void testObtenerUsuarioInexistente() {
		
		Optional<Usuario> usuarioOpcional = Optional.empty(); 
		when(usuarioRepository.findById(email)).thenReturn(usuarioOpcional);
		
		assertThrows(UsuarioNoEncontradoException.class, () -> {  usuarioService.obtenerUsuarioByID(email); });

	}
	
	
	

	public UsuarioRepository getUsuarioRepository() {
		return usuarioRepository;
	}

	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	public UsuarioServiceImpl getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioServiceImpl usuarioService) {
		this.usuarioService = usuarioService;
	}



}
