package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String title;
    private String publisher;



    @OneToMany(mappedBy = "book" , cascade = CascadeType.ALL)
    private Set<Publish> publishes = new HashSet<>();



    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }


    public Set<Publish> getPublish() {
        return publishes;
    }

    public void setPublish(Set<Publish> publishes) {
        this.publishes = publishes;
    }
}
