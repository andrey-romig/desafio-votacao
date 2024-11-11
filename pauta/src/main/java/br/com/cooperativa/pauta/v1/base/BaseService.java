package br.com.cooperativa.pauta.v1.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService<T, R extends JpaRepository<T, Long>> {

    @Autowired
    protected R repository;

    public R getRepository() {
        return repository;
    }
}
