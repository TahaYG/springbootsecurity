package com.example.demo.Repository;
import com.example.demo.Model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface BookRepository extends CrudRepository<Book, Long> {
    public Long countById(Long id);
    Book findByName(String name);

    Book getById(int id);
}
