package br.com.cooperativa.pauta.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public abstract class BaseService<T, R extends JpaRepository<T, Long>> {

    @Autowired
    protected R repository;

    @Transactional
    public T salvar(T entidade) {
        return repository.save(entidade);
    }

    @Transactional
    public T atualizar(T entidade) {
        return repository.save(entidade);
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public Optional<T> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Iterable<T> buscarTodos() {
        return repository.findAll();
    }

    public R getRepository() {
        return repository;
    }
}
