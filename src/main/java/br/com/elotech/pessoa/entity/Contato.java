package br.com.elotech.pessoa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Pattern(regexp = "\\d{11}")
    @Column(nullable = false)
    private String telefone;

    @NotNull(message = "EMAIL_REQUERIDO")
    @Email(message = "EMAIL_INVALIDO")
    @Column(nullable = false)
    private String email;

}
