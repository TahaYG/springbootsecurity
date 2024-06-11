package com.example.demo.Model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String Address;
    @Column(name="imgUrl")
    private String imgUrl;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Publish> publishes = new HashSet<>();

    public Author() {
    }

    public Author(String name, String Address) {
        this.name = name;
        this.Address = Address;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public Set<Publish> getPublish() {
        return publishes;
    }

    public void setPublish(Set<Publish> publishes) {
        this.publishes = publishes;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}