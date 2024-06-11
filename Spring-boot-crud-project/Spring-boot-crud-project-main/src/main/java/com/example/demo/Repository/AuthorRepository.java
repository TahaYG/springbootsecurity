package com.example.demo.Repository;

import com.example.demo.Model.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    Long countById(Long id);
    Author findByName(String name);


}
