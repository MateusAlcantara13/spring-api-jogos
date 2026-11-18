package br.edu.games.repository;

import br.edu.games.model.Game;
import br.edu.games.model.GeneroGame;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByTituloContainingIgnoreCase(String titulo);

    List<Game> findByGenero(GeneroGame genero);
}
