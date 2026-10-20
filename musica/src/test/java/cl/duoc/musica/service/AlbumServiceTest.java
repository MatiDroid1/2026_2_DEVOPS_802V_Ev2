package cl.duoc.musica.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import cl.duoc.musica.model.Album;
import cl.duoc.musica.repository.AlbumRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    @Mock
    AlbumRepository repo;

    @InjectMocks
    AlbumService service;

    private Album album(Long id, String titulo, Integer precio, Integer stock) {
        return new Album(id, titulo, "Artista", "Rock", 2020, precio, stock);
    }

    @Test
    void listarDevuelveTodosLosAlbumes() {
        when(repo.findAll()).thenReturn(List.of(album(1L, "A", 1000, 1), album(2L, "B", 2000, 2)));
        assertEquals(2, service.listar().size());
    }

    @Test
    void obtenerExistenteDevuelveAlbum() {
        when(repo.findById(1L)).thenReturn(Optional.of(album(1L, "A", 1000, 1)));
        assertEquals("A", service.obtener(1L).getTitulo());
    }

    @Test
    void obtenerInexistenteLanzaNoSuchElement() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.obtener(99L));
    }

    @Test
    void crearAlbumValidoLoGuarda() {
        Album a = album(null, "Clancy", 15000, 5);
        when(repo.save(a)).thenReturn(a);
        assertEquals("Clancy", service.crear(a).getTitulo());
        verify(repo).save(a);
    }

    @Test
    void crearSinTituloLanzaIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.crear(album(null, " ", 15000, 5)));
        verify(repo, never()).save(any());
    }

    @Test
    void crearConPrecioCeroLanzaIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.crear(album(null, "X", 0, 5)));
        verify(repo, never()).save(any());
    }

    @Test
    void crearConStockNegativoLanzaIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.crear(album(null, "X", 1000, -1)));
        verify(repo, never()).save(any());
    }

    @Test
    void comprarDescuentaStock() {
        Album a = album(1L, "Clancy", 15000, 5);
        when(repo.findById(1L)).thenReturn(Optional.of(a));
        when(repo.save(a)).thenReturn(a);
        assertEquals(3, service.comprar(1L, 2).getStock());
    }

    @Test
    void comprarMasDelStockLanzaIllegalState() {
        when(repo.findById(1L)).thenReturn(Optional.of(album(1L, "Clancy", 15000, 3)));
        assertThrows(IllegalStateException.class, () -> service.comprar(1L, 10));
        verify(repo, never()).save(any());
    }

    @Test
    void comprarCantidadCeroLanzaIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () -> service.comprar(1L, 0));
        verifyNoInteractions(repo);
    }

    @Test
    void comprarAlbumInexistenteLanzaNoSuchElement() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> service.comprar(99L, 1));
    }

    @Test
    void eliminarExistenteBorra() {
        when(repo.existsById(1L)).thenReturn(true);
        service.eliminar(1L);
        verify(repo).deleteById(1L);
    }

    @Test
    void eliminarInexistenteLanzaNoSuchElement() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(NoSuchElementException.class, () -> service.eliminar(99L));
        verify(repo, never()).deleteById(any());
    }
}