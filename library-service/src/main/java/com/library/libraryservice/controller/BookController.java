package com.library.libraryservice.controller;

import com.library.libraryservice.db1.entity.Book;
import com.library.libraryservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/addAll")
    public List<Book> addMultipleBooks(List<Book> books) {
        return bookService.addMultipleBooks(books);
    }

    @PostMapping("/add")
    public Book addBook(Book book) {
        return bookService.addBook(book);
    }

    @GetMapping("/getAll")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }
}
