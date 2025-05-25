package com.arqsoft.medici.application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.arqsoft.medici.domain.Usuario;
import com.arqsoft.medici.domain.dto.UsuarioDomainDTO;
import com.arqsoft.medici.domain.exceptions.UsuarioExistenteException;
import com.arqsoft.medici.domain.exceptions.UsuarioNoEncontradoException;
import com.arqsoft.medici.domain.utils.UsuarioEstado;
import com.arqsoft.medici.infrastructure.persistence.UsuarioRepository;
import com.arqsoft.medici.infrastructure.rest.dto.UsuarioDTO;

import java.util.Optional;

@SpringBootTest
public class UsuarioServiceIntegrationTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testCrearUsuarioNuevo() throws Exception {
    	UsuarioDomainDTO usuarioDTO = new UsuarioDomainDTO();
        usuarioDTO.setNombre("Carlos");
        usuarioDTO.setApellido("Gonzalez");
        usuarioDTO.setMail("carlos.gonzalez1@example.com");
        
        usuarioService.crearUsuario(usuarioDTO);
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioDTO.getMail());
        assertTrue(usuarioOpt.isPresent());
        Usuario usuario = usuarioOpt.get();
        assertEquals("Carlos", usuario.getNombre());
        assertEquals("Gonzalez", usuario.getApellido());
        assertEquals(UsuarioEstado.ACTIVO, usuario.getEstado());
    }

    @Test
    public void testCrearUsuarioExistenteActivo() throws Exception {
        Usuario usuarioExistente = new Usuario("Ana", "Lopez", "ana.lopez1@example.com");
        usuarioExistente.setEstado(UsuarioEstado.ACTIVO);
        usuarioRepository.insert(usuarioExistente);

        UsuarioDomainDTO request = new UsuarioDomainDTO();
        request.setMail("ana.lopez1@example.com");
        request.setNombre("Ana");
        request.setApellido("Lopez");
        
        assertThrows(UsuarioExistenteException.class, () -> usuarioService.crearUsuario(request));
    }

    @Test
    public void testCrearUsuarioExistenteInactivo() throws Exception {
        Usuario usuarioExistente = new Usuario("Mario", "Perez", "mario.perez1@example.com");
        usuarioExistente.setEstado(UsuarioEstado.BORRADO);
        usuarioRepository.insert(usuarioExistente);

        UsuarioDomainDTO request = new UsuarioDomainDTO();
        request.setMail("mario.perez1@example.com");
        request.setNombre("Mario");
        request.setApellido("Perez");

        usuarioService.crearUsuario(request);
        
        Optional<Usuario> usuarioOpt = usuarioRepository.findById("mario.perez1@example.com");
        assertTrue(usuarioOpt.isPresent());
        Usuario usuario = usuarioOpt.get();
        assertEquals(UsuarioEstado.ACTIVO, usuario.getEstado());
        assertEquals("Mario", usuario.getNombre());
        assertEquals("Perez", usuario.getApellido());
    }

    @Test
    public void testModificarUsuarioExistente() throws Exception {
        Usuario usuarioExistente = new Usuario("Laura", "Gomez", "laura.gomez1@example.com");
        usuarioExistente.setEstado(UsuarioEstado.ACTIVO);
        usuarioRepository.insert(usuarioExistente);

        UsuarioDomainDTO request = new UsuarioDomainDTO();
        request.setMail("laura.gomez1@example.com");
        request.setNombre("Laura Modificada");
        request.setApellido("Gomez Modificada");

        usuarioService.modificarUsuario(request);

        Optional<Usuario> usuarioOpt = usuarioRepository.findById("laura.gomez1@example.com");
        assertTrue(usuarioOpt.isPresent());
        Usuario usuario = usuarioOpt.get();
        assertEquals("Laura Modificada", usuario.getNombre());
        assertEquals("Gomez Modificada", usuario.getApellido());
    }

    @Test
    public void testModificarUsuarioNoEncontrado() {
    	UsuarioDomainDTO request = new UsuarioDomainDTO();
        request.setMail("no.existe@example.com");
        request.setNombre("No Existe");
        request.setApellido("Usuario");

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioService.modificarUsuario(request));
    }

    @Test
    public void testEliminarUsuarioExistente() throws Exception {
        Usuario usuarioExistente = new Usuario("Pedro", "Alvarez", "pedro.alvarez1@example.com");
        usuarioExistente.setEstado(UsuarioEstado.ACTIVO);
        usuarioRepository.insert(usuarioExistente);

        usuarioService.eliminarUsuario("pedro.alvarez1@example.com");

        Optional<Usuario> usuarioOpt = usuarioRepository.findById("pedro.alvarez1@example.com");
        assertTrue(usuarioOpt.isPresent());
        Usuario usuario = usuarioOpt.get();
        assertEquals(UsuarioEstado.BORRADO, usuario.getEstado());
    }

    @Test
    public void testEliminarUsuarioNoEncontrado() {
        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioService.eliminarUsuario("inexistente@example.com"));
    }

    @Test
    public void testObtenerUsuarioActivo() throws Exception {
        Usuario usuarioExistente = new Usuario("Sofia", "Ramirez", "sofia.ramirez1@example.com");
        usuarioExistente.setEstado(UsuarioEstado.ACTIVO);
        usuarioRepository.insert(usuarioExistente);

        Usuario usuario = usuarioService.obtenerUsuarioByID("sofia.ramirez1@example.com");
        assertNotNull(usuario);
        assertEquals("Sofia", usuario.getNombre());
    }

    @Test
    public void testObtenerUsuarioNoEncontrado() {
        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioService.obtenerUsuarioByID("desconocido@example.com"));
    }
}
