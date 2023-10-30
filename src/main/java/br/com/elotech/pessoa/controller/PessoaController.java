package br.com.elotech.pessoa.controller;

import br.com.elotech.pessoa.entity.Pessoa;
import br.com.elotech.pessoa.service.PessoaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/pessoa")
@RequiredArgsConstructor
public class PessoaController {
    private final PessoaService pessoaService;

    @GetMapping("{id}")
    public ResponseEntity<Pessoa> read(@PathVariable @Positive Long id) {
        Pessoa pessoa = pessoaService.read(id).orElse(null);
        if (pessoa == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping
    public Page<Pessoa> list(@PageableDefault Pageable pageable) {
        return pessoaService.list(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa create(@RequestBody @Valid Pessoa pessoa) {
        return pessoaService.create(pessoa);
    }

    @PutMapping
    public Pessoa update(@RequestBody @Valid Pessoa pessoa) {
        return pessoaService.update(pessoa);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable @Positive Long id) {
        pessoaService.delete(id);
    }
}
