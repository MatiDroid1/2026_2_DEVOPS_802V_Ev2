package cl.duoc.musica.service;

import cl.duoc.musica.model.Album;
import cl.duoc.musica.repository.AlbumRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository repo;

    public List<Album> listar() {
        return repo.findAll();
    }

    public Album obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Álbum no encontrado: " + id));
    }

    public Album crear(Album a) {
        if (a.getTitulo() == null || a.getTitulo().isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (a.getPrecio() == null || a.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }
        if (a.getStock() == null || a.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        return repo.save(a);
    }

    public Album comprar(Long id, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
        }
        Album a = obtener(id);
        if (a.getStock() < cantidad) {
            throw new IllegalStateException("Stock insuficiente. Disponible: " + a.getStock());
        }
        a.setStock(a.getStock() - cantidad);
        return repo.save(a);
    }

    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            throw new NoSuchElementException("Álbum no encontrado: " + id);
        }
        repo.deleteById(id);
    }
}