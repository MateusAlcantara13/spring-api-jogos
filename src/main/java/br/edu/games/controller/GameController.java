package br.edu.games.controller;

import br.edu.games.model.Game;
import br.edu.games.model.GeneroGame;
import br.edu.games.repository.GameRepository;
import br.edu.games.service.GameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    // GET /api/games
    @GetMapping
    public List<Game> listar() {
        return service.listarTodos();
    }

    // GET /api/games/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Game> buscarPorId(@PathVariable Long id) {
        Game game = service.buscarPorId(id);

        if (game == null) {
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(game);
    }

    // POST/api/games
    @PostMapping
    public ResponseEntity<Game> cadastrar(@Valid @RequestBody Game game) {
        Game salvo = service.salvar(game);

        return ResponseEntity.created(URI.create("/api/games/" + salvo.getId())).body(salvo);
    }

    // PUT /api/games/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Game> atualizar(@PathVariable Long id, @Valid @RequestBody Game game) {
        Game existente = service.buscarPorId(id);

        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setTitulo(game.getTitulo());
        existente.setGenero(game.getGenero());
        existente.setValorGame(game.getValorGame());
        existente.setEstoque(game.getEstoque());

        return ResponseEntity.ok(service.salvar(existente));
    }

    // DELETE /api/games/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!service.existe(id)) {
            return ResponseEntity.notFound().build();
        }

        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/games/busca?titulo=gta
    @GetMapping("/busca")
    public List<Game> buscarPorTitulo(@RequestParam String titulo) {
        return service.buscarPortitulo(titulo);
    }

    // GET /api/games/genero/ACAO
    @GetMapping("/genero/{genero}")
    public List<Game> buscarPorGenero(@PathVariable GeneroGame genero) { return service.buscarPorGenero(genero); }

    // ---- as operações novas ---

    @PostMapping("/{id}/vender")
    public ResponseEntity<?> vender(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.vender(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/repor")
    public ResponseEntity<?> repor(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.repor(id));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
