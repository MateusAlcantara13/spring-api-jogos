package br.edu.games.controller;

import br.edu.games.model.Game;
import br.edu.games.model.GeneroGame;
import br.edu.games.repository.GameRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameRepository repository;

    public GameController(GameRepository repository) {
        this.repository = repository;
    }

    // GET /api/games
    @GetMapping
    public List<Game> listar() {
        return repository.findAll();
    }

    // GET /api/games/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Game> buscarPorId(@PathVariable Long id) {
        Game game = repository.findById(id).orElse(null);

        if (game == null) {
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(game);
    }

    // POST/api/games
    @PostMapping
    public ResponseEntity<Game> cadastrar(@Valid @RequestBody Game game) {
        Game salvo = repository.save(game);

        return ResponseEntity.created(URI.create("/api/games/" + salvo.getId())).body(salvo);
    }

    // PUT /api/games/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Game> atualizar(@PathVariable Long id, @Valid @RequestBody Game game) {
        Game existente = repository.findById(id).orElse(null);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setTitulo(game.getTitulo());
        existente.setGenero(game.getGenero());
        existente.setValorGame(game.getValorGame());
        existente.setEstoque(game.getEstoque());

        return ResponseEntity.ok(repository.save(existente));
    }

    // DELETE /api/games/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/games/busca?titulo=gta
    @GetMapping("/busca")
    public List<Game> buscarPorTitulo(@RequestParam String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    // GET /api/games/genero/ACAO
    @GetMapping("/genero/{genero}")
    public List<Game> buscarPorGenero(@PathVariable GeneroGame genero) { return repository.findByGenero(genero); }
}
