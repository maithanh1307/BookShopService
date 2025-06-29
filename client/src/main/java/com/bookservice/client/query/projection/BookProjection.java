package com.bookservice.client.query.projection;

import com.bookservice.client.command.data.Book;
import com.bookservice.client.command.data.BookRepository;
import com.bookservice.client.query.model.BookResponseModel;
import com.bookservice.client.query.queries.GetAllBookQuery;
import com.bookservice.client.query.queries.GetBookDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookProjection {
    @Autowired
    private BookRepository bookRepository;

    @QueryHandler
    public List<BookResponseModel> handle(GetAllBookQuery query) {
        List<Book> list = bookRepository.findAll();
        List<BookResponseModel> listBookResponse = new ArrayList<>();
        list.forEach(book -> {
            BookResponseModel bookResponseModel = new BookResponseModel();
            BeanUtils.copyProperties(book, bookResponseModel);
            listBookResponse.add(bookResponseModel);
        });
        return listBookResponse;
    }

    @QueryHandler
    public BookResponseModel handle(GetBookDetailQuery query) throws Exception {
        BookResponseModel bookResponseModel = new BookResponseModel();

        Book book = bookRepository.findById(query.getId()).orElseThrow(() -> new Exception("Not found book with Book ID: " + query.getId()));

        BeanUtils.copyProperties(book, bookResponseModel);
        return bookResponseModel;
    }
}
