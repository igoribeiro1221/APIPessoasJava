package br.com.elotech.pessoa.service;

import br.com.elotech.pessoa.entity.Pessoa;
import br.com.elotech.pessoa.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PessoaService {
    private final PessoaRepository pessoaRepository;

    public Optional<Pessoa> read(long id) {
        return pessoaRepository.findById(id);
    }

    public Page<Pessoa> list(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public Pessoa create(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa update(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public void delete(long id) {
        pessoaRepository.deleteById(id);
    }
}
