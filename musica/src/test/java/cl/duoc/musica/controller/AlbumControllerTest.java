package cl.duoc.musica.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cl.duoc.musica.model.Album;
import cl.duoc.musica.service.AlbumService;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class AlbumControllerTest {

    @Mock
    AlbumService service;

    @InjectMocks
    AlbumController controller;

    private Album album() {
        return new Album(1L, "Clancy", "Artista", "Rock", 2020, 15000, 5);
    }

    @Test
    void listarDelegaEnElServicio() {
        when(service.listar()).thenReturn(List.of(album()));
        assertEquals(1, controller.listar().size());
    }

    @Test
    void obtenerDelegaEnElServicio() {
        when(service.obtener(1L)).thenReturn(album());
        assertEquals("Clancy", controller.obtener(1L).getTitulo());
    }

    @Test
    void crearDelegaEnElServicio() {
        Album a = album();
        when(service.crear(a)).thenReturn(a);
        assertSame(a, controller.crear(a));
    }

    @Test
    void comprarDelegaEnElServicio() {
        when(service.comprar(1L, 2)).thenReturn(album());
        assertEquals(1L, controller.comprar(1L, 2).getId());
    }

    @Test
    void eliminarDelegaEnElServicio() {
        controller.eliminar(1L);
        verify(service).eliminar(1L);
    }

    @Test
    void manejadoresDevuelvenElCodigoHttpCorrecto() {
        assertEquals(HttpStatus.NOT_FOUND, controller.noEncontrado(new NoSuchElementException("x")).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, controller.invalido(new IllegalArgumentException("x")).getStatusCode());
        assertEquals(HttpStatus.CONFLICT, controller.conflicto(new IllegalStateException("x")).getStatusCode());
    }
}