package br.edu.games.service;

import br.edu.games.model.Game;
import br.edu.games.model.GeneroGame;
import br.edu.games.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository repository;

    @InjectMocks
    private GameService service;

    @Test
    void deveVenderGameQuandoTemEstoque() throws Exception {
        Game game = novoGame(1L, 3);

        when(repository.findById(1L)).thenReturn(Optional.of(game));
        when(repository.save(any(Game.class))).thenReturn(game);

        Game resultado = service.vender(1L);

        assertEquals(2, resultado.getEstoque());
    }

    @Test
    void naoDeveVenderGameSemEstoque() {
        Game game = novoGame(1L, 0);

        when(repository.findById(1L)).thenReturn(Optional.of(game));

        assertThrows(Exception.class, () -> service.vender(1L));

        verify(repository, never()).save(any(Game.class));
    }

    @Test
    void naoDeveVenderGameInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.vender(99L));

        verify(repository, never()).save(any(Game.class));

    }

    @Test
    void deveReporGameAumentandoEstoque() throws Exception {
        Game game = novoGame(1L, 2);

        when(repository.findById(1L)).thenReturn(Optional.of(game));
        when(repository.save(any(Game.class))).thenReturn(game);

        Game resultado = service.repor(1L);

        assertEquals(3, resultado.getEstoque());
    }

    @Test
    void naoDeveReporGameInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.repor(99L));

        verify(repository, never()).save(any(Game.class));
    }

    @Test void deveChamarRepositoryUmaUnicaVezAoVender() throws Exception {
        Game game = novoGame(1L, 5);

        when(repository.findById(1L)).thenReturn(Optional.of(game));
        when(repository.save(any(Game.class))).thenReturn(game);

        service.vender(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository,times(1)).save(any(Game.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 10, 50})
    void deveDiminuirEstoqueEmUmAoVender(int estoqueInicial) throws Exception {
        Game game = novoGame(1L, estoqueInicial);

        when(repository.findById(1L)).thenReturn(Optional.of(game));
        when(repository.save(any(Game.class))).thenReturn(game);

        Game resultado = service.vender(1L);

        assertEquals(estoqueInicial - 1, resultado.getEstoque());
    }

    private Game novoGame(Long id, int estoque) {
        Game game = new Game();
        game.setId(id);
        game.setTitulo("GTA");
        game.setGenero(GeneroGame.ACAO);
        game.setEstoque(estoque);
        game.setValorGame(new BigDecimal("9.90"));
        return game;
    }
}
