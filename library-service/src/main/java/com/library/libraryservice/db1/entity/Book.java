package com.library.libraryservice.db1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {

//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String bookName;
    private int pages;
    private double price;
}
