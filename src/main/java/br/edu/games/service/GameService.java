package br.edu.games.service;

import br.edu.games.model.Game;
import br.edu.games.model.GeneroGame;
import br.edu.games.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository repository;

    public GameService(GameRepository repository) {
        this.repository = repository;
    }

    public List<Game> listarTodos() {
        return repository.findAll();
    }

    public Game buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Game salvar(Game game) {
        return repository.save(game);
    }

    public boolean existe(Long id) {
        return repository.existsById(id);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public List<Game> buscarPortitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Game> buscarPorGenero(GeneroGame genero) {
        return repository.findByGenero(genero);
    }

    // ---- as operações que tem regra ----

    public Game vender(Long id) throws Exception {
        Game game = repository.findById(id)
                .orElseThrow(() -> new Exception("Jogo não encontrado"));

        if (!game.temEstoque()) {
            throw new Exception("Jogo sem estoque disponível");
        }

        game.vender();
        return repository.save(game);
    }

    public Game repor(Long id) throws Exception {
        Game game = repository.findById(id)
                .orElseThrow(() -> new Exception("Jogo não encontrado"));

        game.repor();
        return repository.save(game);
    }
}
