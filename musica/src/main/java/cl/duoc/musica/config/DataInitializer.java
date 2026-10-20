package cl.duoc.musica.config;

import cl.duoc.musica.model.Album;
import cl.duoc.musica.repository.AlbumRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner cargarDatos(AlbumRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.saveAll(List.of(
                    new Album(null, "Vessel", "Twenty One Pilots", "Alternativo", 2013, 14990, 10),
                    new Album(null, "Blurryface", "Twenty One Pilots", "Alternativo", 2015, 15990, 18),
                    new Album(null, "Trench", "Twenty One Pilots", "Alternativo", 2018, 16990, 26),
                    new Album(null, "Scaled and Icy", "Twenty One Pilots", "Pop alternativo", 2021, 16990, 45),
                    new Album(null, "Clancy", "Twenty One Pilots", "Alternativo", 2024, 17990, 30),
                    new Album(null, "Breach", "Twenty One Pilots", "Alternativo", 2025, 22990, 20),
                    new Album(null, "Messy Mind", "Unlike Pluto", "Electrónica", 2020, 12990, 12),
                    new Album(null, "Technicolor Daydrem", "Unlike Pluto", "Alternativo", 2021, 13990, 15),
                    new Album(null, "Loud Fantasy, Quiet Reality", "Unlike Pluto", "Alternativo", 2021, 19990, 10),
                    new Album(null, "Pixelated Oblivion", "Unlike Pluto", "Alternativo", 2022, 19990, 10),
                    new Album(null, "Cherry Blossom Nightmare", "Unlike Pluto", "Alternativo", 2022, 19990, 10),
                    new Album(null, "Dizzy Bezerko", "Unlike Pluto", "Alternativo", 2023, 19990, 10),
                    new Album(null, "Ghost Gardens", "Unlike Pluto", "Alternativo", 2024, 19990, 10),
                    new Album(null, "Kiss Collapse", "Unlike Pluto", "Alternativo", 2025, 19990, 10),
                    new Album(null, "Double Daze", "Unlike Pluto", "Alternativo", 2026, 19990, 10)
                    
                    
                    
                    
                    
                ));
            }
        };
    }
}