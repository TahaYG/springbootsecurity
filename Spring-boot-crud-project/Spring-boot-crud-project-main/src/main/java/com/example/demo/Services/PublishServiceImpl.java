package com.example.demo.Services;

import com.example.demo.Controller.PublishAlreadyExistsException;
import com.example.demo.DTOs.Publishview;
import com.example.demo.Model.Author;
import com.example.demo.Model.Book;
import com.example.demo.Model.Publish;
import com.example.demo.Repository.AuthorRepository;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.PublishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    private PublishRepository publishRepository;
    @Autowired
    private BookRepository crepo;
    @Autowired
    private AuthorRepository authorRepository;
    @Override
    public List<Publish> getAllPublish() {
        Iterable<Publish> publishIterable = publishRepository.findAll();
        List<Publish> publishList = new ArrayList<>();
        publishIterable.forEach(publishList::add);
        return publishList;
    }


    @Override
    public void savePublish(Publish publish) throws PublishAlreadyExistsException {
        Long count = publishRepository.countById(publish.getId());
        if (count != null && count > 0) {
            throw new PublishAlreadyExistsException("A publish with ID " + publish.getId() + " already exists.");
        }
        publishRepository.save(publish);

    }



    @Override
    public Publish createPublish(Publishview publishview) throws PublishAlreadyExistsException {
        Long count = publishRepository.countById(publishview.getId());
        if (count != null && count > 0) {
            throw new PublishAlreadyExistsException("A publish with ID " + publishview.getId() + " already exists.");
        }
        Author author = authorRepository.findByName(publishview.getAuthorName());
        Book book = crepo.findByName(publishview.getBookName());
        Publish publish = new Publish();
        publish.setId(publishview.getId());
        publish.setDate(publishview.getDate());
        publish.setEdition(Integer.valueOf(publishview.getEdition()));
        publish.setAuthor(author);
        publish.setBook(book);
        return publishRepository.save(publish);

    }
    public Publish getPublishById(Long id) {
        Optional<Publish> optionalPublish = publishRepository.findById(id);
        if (optionalPublish.isPresent()) {
            Publish publish = optionalPublish.get();
            Publishview publishview = new Publishview();
            publishview.setId(publish.getId());
            publishview.setDate(publish.getDate());
            publishview.setEdition(String.valueOf(publish.getEdition()));
            publishview.setAuthorId(publish.getAuthor().getId());
            publishview.setBookId(publish.getBook().getId());
            return publishRepository.save(publish);
        } else {

            return null;
        }
    }

    public void updatePublish(Publish publish) throws PublishNotFoundException {

        Optional<Publish> optionalPublish = publishRepository.findById(publish.getId());


        if (optionalPublish.isPresent()) {
            Publish existingPublish = optionalPublish.get();
            existingPublish.setEdition(publish.getEdition());
            existingPublish.setDate(publish.getDate());


            Author author = null;
            author = authorRepository.findByName(publish.getAuthor().getName());


            Book book = null;
            book = crepo.findByName(publish.getBook().getName());


            existingPublish.setAuthor(author);
            existingPublish.setBook(book);


            publishRepository.save(existingPublish);
        } else {

            throw new PublishNotFoundException("Publish not found with id: " + publish.getId());
        }
    }








    @Override
    public void deletePublish(Long id) throws PublishNotFoundException {
        if (publishRepository.existsById(id)) {
            publishRepository.deleteById(id);
        } else {
            throw new PublishNotFoundException("Publish not found with id: " + id);
        }
    }
}
