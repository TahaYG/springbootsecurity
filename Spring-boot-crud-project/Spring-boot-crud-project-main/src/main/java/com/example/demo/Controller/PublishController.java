package com.example.demo.Controller;

import com.example.demo.DTOs.Publishview;
import com.example.demo.Model.Author;
import com.example.demo.Model.Book;
import com.example.demo.Model.Publish;
import com.example.demo.Repository.AuthorRepository;
import com.example.demo.Repository.BookRepository;
import com.example.demo.Repository.PublishRepository;
import com.example.demo.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class PublishController {

    private PublishService publishService;
    private AuthorService authorService;
    private BookService bookService;
    private PublishRepository publishRepository;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;


    @Autowired
    public PublishController(PublishService publishService, AuthorRepository authorRepository, AuthorService authorService, BookService bookService, PublishRepository publishRepository, BookRepository bookRepository) {
        this.publishService = publishService;
        this.authorService = authorService;
        this.bookService = bookService;
        this.publishRepository = publishRepository;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }


    @GetMapping("/oindex")
    public String getIndexPage() {
        return "index";
    }


    @GetMapping("/Author/apublishlist/{id}")
    public String showApublishListPage(Model model, @PathVariable("id") Long id, RedirectAttributes ra) {

        Publish p = new Publish();
        Author author= authorService.findById(id);
        List<Book> books= (List<Book>) bookRepository.findAll();

        model.addAttribute("publish", p);
        model.addAttribute("author", author);
        model.addAttribute("books",books);

        return "Author/apublishlist";
    }


    @PostMapping("/Author/createa/{id}")
    public String createPublish(Publish publish, RedirectAttributes ra)throws AuthorNotFoundException {

        publish.setId(publish.getId());
        publish.setDate(publish.getDate());
        publish.setEdition(publish.getEdition());
        publish.setBook(publish.getBook());
        publish.setAuthor(publish.getAuthor());
        try {
            publishService.savePublish(publish);
            ra.addFlashAttribute("message", "The publish was successfully added");
        } catch (PublishAlreadyExistsException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/publishes";
    }

    @GetMapping("/publish/add")
    public String showAddPublishForm(Model model) {
        model.addAttribute("publish", new Publishview());
        return "publish/publishAdd";
    }
    @PostMapping("/publish/add")
    public String addPublish(Publishview publishv, RedirectAttributes ra)
    {
        try {
            publishService.createPublish(publishv);
            ra.addFlashAttribute("message", "The publish was successfully added");
        } catch (PublishAlreadyExistsException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/publishes";
    };






    @GetMapping("/publishes")
    public String showpublishList(Model model) {
        Iterable<Publish> publish = publishRepository.findAll();
        model.addAttribute("publish", publish);
        return "publish/publishList";
    }


    @GetMapping("/publish/update/{id}")
    public String getpublishById(@PathVariable Long id, Model model) throws PublishNotFoundException {
        Publish publish = publishService.getPublishById(id);
        model.addAttribute("publish", publish);
        return "publish/publishEdit";
    }

    @PostMapping("/publish/update/{id}")
    public String updatePublish(Publish publishv, RedirectAttributes ra) throws PublishNotFoundException {
        publishService.updatePublish(publishv);
        ra.addFlashAttribute("message", "The publish was successfully updated");
        return "redirect:/publishes";
    }


    @GetMapping("/Book/bpublishlist/{id}")
    public String showBpublishListPage(Model model, @PathVariable("id") Long id, RedirectAttributes ra) {
        Publish p = new Publish();
        Book book = bookService.findById(id);
        List<Author> authors= (List<Author>) authorRepository.findAll();

        model.addAttribute("publish", p);
        model.addAttribute("book", book);
        model.addAttribute("authors",authors);
        return "Book/bpublishlist";
    }




    @PostMapping("/Book/createb/{id}")
    public String createPublishb(Publish publish, RedirectAttributes ra) throws BookNotFoundException {

        publish.setId(publish.getId());
        publish.setDate(publish.getDate());
        publish.setEdition(publish.getEdition());
        publish.setBook(publish.getBook());
        publish.setAuthor(publish.getAuthor());
        try {
            publishService.savePublish(publish);
            ra.addFlashAttribute("message", "The publish was successfully added");
        } catch (PublishAlreadyExistsException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/publishes";
    }









    @GetMapping("/publishes/delete/{id}")
    public String deletePublish(@PathVariable("id") Long id , RedirectAttributes ra) {
        try {
            publishService.deletePublish(id);
            ra.addFlashAttribute("message", "Publish deleted successfully");
        } catch (PublishNotFoundException e) {
            ra.addFlashAttribute("error", "Failed to delete publish: " + e.getMessage());

        }
        return "redirect:/publishes";
    }
}

