package com.library.libraryservice.service;

import com.library.libraryservice.db1.entity.Book;

import java.util.List;

public interface BookService {
    List<Book> addMultipleBooks(List<Book> books);

    Book addBook(Book book);

    List<Book> getAllBooks();
}
