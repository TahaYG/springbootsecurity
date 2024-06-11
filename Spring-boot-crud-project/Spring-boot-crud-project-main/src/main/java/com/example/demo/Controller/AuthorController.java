package com.example.demo.Controller;

import com.example.demo.Model.Author;
import com.example.demo.Services.AuthorAlreadyExistsException;
import com.example.demo.Services.AuthorNotFoundException;
import com.example.demo.Services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @GetMapping("/pindex")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/Author")
    public String showAuthorList(Model model) {
        List<Author> authorList = authorService.listAll();
        model.addAttribute("authorList", authorList);
        return "Author/AuthorList";
    }

    @PostMapping("/Author/save")
    public String saveAuthor(@ModelAttribute Author author, @RequestParam("image") MultipartFile file, BindingResult result, RedirectAttributes ra) throws AuthorAlreadyExistsException {
        String fileName = file.getOriginalFilename();

        // Dosya adını güvenli hale getirme
        fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        // Dosya yolunu oluşturma
        author.setImgUrl(fileName);
        String uploadDir = "Spring-boot-crud-project-main/src/main/resources/static/images/";
        Path uploadPath = Paths.get(uploadDir, fileName);

        // Hedef klasörün varlığını kontrol et ve yoksa oluştur
        try {
            if (!Files.exists(uploadPath.getParent())) {
                Files.createDirectories(uploadPath.getParent());
                System.out.println("Directory created: " + uploadPath.getParent().toAbsolutePath());
            }
        } catch (IOException ex) {
            System.out.println("Failed to create directory! " + ex.toString());
            ra.addFlashAttribute("errorMessage", "Failed to create directory! Please try again.");
            return "redirect:/Student";
        }

        // Dosyayı yükleme
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File saved to: " + uploadPath.toAbsolutePath());

            // Dosya gerçekten var mı kontrol et
            if (Files.exists(uploadPath)) {
                System.out.println("File exists at: " + uploadPath.toAbsolutePath());
            } else {
                System.out.println("File does not exist at: " + uploadPath.toAbsolutePath());
                ra.addFlashAttribute("errorMessage", "File not found after saving! Please check your configuration.");
                return "redirect:/Student";
            }
        } catch (IOException ex) {
            System.out.println("File saving error! " + ex.toString());
            ra.addFlashAttribute("errorMessage", "File saving error! Please try again.");
            return "redirect:/Student";
        }
       try {
           authorService.save(author);
           ra.addFlashAttribute("message", "The author has been saved successfully");

       }catch (AuthorAlreadyExistsException e)
       {
          ra.addFlashAttribute("message",e.getMessage());
       }
        return "redirect:/Author";
    }

    @GetMapping("/Author/create")
    public String showNewForm(Model model) {
        model.addAttribute("author", new Author());
        return "Author/AuthorAdd";
    }

    @GetMapping("/Author/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Author author = authorService.get(id);
            model.addAttribute("author", author);
        } catch (AuthorNotFoundException e) {
            ra.addFlashAttribute("message", "Author not found :(");
            return "redirect:/Author";
        }
        return "Author/AuthorEdit";
    }

    @PostMapping("/Author/edit/{id}")
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute Author author, RedirectAttributes ra) {
        try {
            authorService.updateAuthor(id, author);
            ra.addFlashAttribute("message", "The author has been updated successfully");
        } catch (AuthorNotFoundException e) {
            ra.addFlashAttribute("message", "Author not found");
        }
        return "redirect:/Author";
    }


    @GetMapping("/Author/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id, RedirectAttributes ra) {
        try {
            authorService.delete(id);
            ra.addFlashAttribute("message", "The author with ID " + id + " has been deleted");
        } catch (AuthorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/Author";
    }
}
