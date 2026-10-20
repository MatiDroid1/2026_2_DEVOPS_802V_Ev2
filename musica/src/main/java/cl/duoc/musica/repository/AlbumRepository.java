package cl.duoc.musica.repository;

import cl.duoc.musica.model.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}