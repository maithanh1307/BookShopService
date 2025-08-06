package com.bookservice.client.query.controller;

import com.bookservice.client.query.model.BookResponseModel;
import com.bookservice.client.query.queries.GetAllBookQuery;
import com.bookservice.commonservice.model.BookResponseCommonModel;
import com.bookservice.commonservice.queries.GetBookDetailQuery;
import com.bookservice.commonservice.services.KafkaService;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {

    @Autowired
    private QueryGateway queryGateway;

    @Autowired
    private KafkaService kafkaService;

    @GetMapping
    public List<BookResponseModel> getAllBooks() {
        GetAllBookQuery query = new GetAllBookQuery();
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class)).join();
    }

    @GetMapping("/{bookId}")
    public BookResponseCommonModel getBookById(@PathVariable String bookId) {
        GetBookDetailQuery query = new GetBookDetailQuery(bookId);
        return queryGateway.query(query, ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
    }

    @PostMapping("/sendMessage")
    public void sendMessage(@RequestBody String message) {
        kafkaService.sendMessage("test", message);
    }


}
