package com.example.demo.Controller;

import com.example.demo.Model.Book;
import com.example.demo.Services.BookAlreadyExistsException;
import com.example.demo.Services.BookNotFoundException;
import com.example.demo.Services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BookController {
    @Autowired private BookService service;
    @GetMapping("/index")
    public String getIndexPage() {
        return "index";
    }
    @GetMapping("/Book")
    public String showBookList(Model model)
    {
        List<Book> listBook =service.listAll();
        model.addAttribute("listBook" , listBook);
        return "Book/BookList";
    }
    @PostMapping("/Book/save")
    public String saveBook(@ModelAttribute Book book, RedirectAttributes ra) throws BookAlreadyExistsException {
        try {
            service.save(book);
            ra.addFlashAttribute("message", "The book has been saved successfully");
        } catch (BookAlreadyExistsException e) {
            ra.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/Book";
    }

    @GetMapping("/Book/create")
    public String showNewForm(Model model) {
        model.addAttribute("book", new Book());
        return "Book/BookAdd";
    }
    @GetMapping("/Book/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Book book = service.get(id);
            model.addAttribute("book", book);
        } catch (BookNotFoundException e) {
            ra.addFlashAttribute("message", "Book not found");
            return "redirect:/Book";
        }
        return "Book/BookEdit";
    }

    @PostMapping("/Book/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book book, RedirectAttributes ra) {
        try {
            service.updateBook(id, book);
            ra.addFlashAttribute("message", "The book has been updated successfully");
        } catch (BookNotFoundException e) {
            ra.addFlashAttribute("message", "Book not found");
        }
        return "redirect:/Book";
    }

    @GetMapping("/Book/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id,  RedirectAttributes ra){
        try {
            service.delete(id);
            ra.addFlashAttribute("message","The book with ID " +id+" has been deleted");


        }
        catch (BookNotFoundException e){
            ra.addFlashAttribute("message", e.getMessage());

        }
        return "redirect:/Book";

    }




}
