package com.example.demo.Repository;

import com.example.demo.Model.Publish;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublishRepository extends CrudRepository<Publish, Long> {
    Long countById(Long id);

    Publish getById(int id);
}

