package br.edu.games.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "games")
@Getter
@Setter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Informe o título")
    @Size(min = 2, max = 80, message = "O título deve conter de 2 a 80 caracteres")
    @Column(nullable = false, length = 80)
    private String titulo;

    @NotNull(message = "Informe o gênero")
    @Enumerated(EnumType.STRING)
    private GeneroGame genero;

    @NotNull(message = "Informe o preço do jogo")
    @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
    private BigDecimal valorGame;

    @NotNull(message = "A quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "A quantidade não pode ser negativa")
    private Integer estoque;

}
