package br.com.elotech.pessoa.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @CPF
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotNull
    @PastOrPresent
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @NotEmpty
    @OneToMany(cascade = CascadeType.ALL)
    private List<@Valid Contato> contatos;
}

