package com.example.demo.Services;

import com.example.demo.Model.Author;
import com.example.demo.Model.Author;
import com.example.demo.Repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> listAll() {
        return (List<Author>) authorRepository.findAll();
    }

    public Author get(Long id) throws AuthorNotFoundException {
        Optional<Author> result = authorRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        throw new AuthorNotFoundException("Could not find any author with ID " + id);

    }
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));
    }

    public Author findByName2(String name) {
        return authorRepository.findByName(name);
    }
    public String findAuthorNameById(Long id) throws AuthorNotFoundException {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if (authorOptional.isPresent()) {
            Author author = authorOptional.get();
            return author.getName();
        } else {
            throw new AuthorNotFoundException("Could not find any author with ID " + id);
        }
    }

    public void save(Author author) throws AuthorAlreadyExistsException {
        Long count = authorRepository.countById(author.getId());
        if (count != null && count > 0) {
            throw new AuthorAlreadyExistsException("An author with ID " + author.getId() + " already exists.");
        }
        authorRepository.save(author);
    }

    public void updateAuthor(Long id, Author author) throws AuthorNotFoundException {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        if (optionalAuthor.isPresent()) {
            Author existingAuthor = optionalAuthor.get();
            existingAuthor.setName(author.getName());
            existingAuthor.setAddress(author.getAddress());
            authorRepository.save(existingAuthor);
        } else {
            throw new AuthorNotFoundException("Could not find any author with ID " + id);
        }
    }

    public void delete(Long id) throws AuthorNotFoundException {
        Long count = authorRepository.countById(id);
        if (count == null || count == 0) {
            throw new AuthorNotFoundException("Could not find any author with ID " + id);
        }
        authorRepository.deleteById(id);
    }
}
