package com.example.demo.Services;

import com.example.demo.Model.Book;
import com.example.demo.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository crepo;

    public List<Book> listAll() {
        return (List<Book>) crepo.findAll();
    }

    public Book findByName1(String name) {
        return crepo.findByName(name);
    }

    public void save(Book customer) throws BookAlreadyExistsException {
        Long count = crepo.countById(customer.getId());
        if (count != null && count > 0) {
            throw new BookAlreadyExistsException("A book with ID " + customer.getId() + " already exists.");
        }
        crepo.save(customer);
    }

    public Book get(Long id) throws BookNotFoundException {
        Optional<Book> result = crepo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new BookNotFoundException("Could not find any book with ID " + id);
    }

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.crepo = bookRepository;
    }

    public Book findById(Long id) {
        return crepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    public String findBookNameById(Long id) throws BookNotFoundException {
        Optional<Book> bookOptional = crepo.findById(id);
        if (bookOptional.isPresent()) {
            return bookOptional.get().getName();
        } else {
            return "Book Not Found";
        }
    }

    public void updateBook(Long id, Book book) throws BookNotFoundException {
        Optional<Book> optionalBook = crepo.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setName(book.getName());
            existingBook.setTitle(book.getTitle());
            existingBook.setPublisher(book.getPublisher());
            crepo.save(existingBook);
        } else {
            throw new BookNotFoundException("Could not find any book with ID " + id);
        }
    }



    public void delete(Long id) throws BookNotFoundException {
        Long count = crepo.countById(id);
        if (count == null || count == 0) {
            throw new BookNotFoundException("Could not find any book with ID " + id);
        }
        crepo.deleteById(id);
    }
}
