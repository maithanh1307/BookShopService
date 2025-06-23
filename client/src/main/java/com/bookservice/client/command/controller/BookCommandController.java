package com.bookservice.client.command.controller;

import com.bookservice.client.command.command.CreateBookCommand;
import com.bookservice.client.command.data.Book;
import com.bookservice.client.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    @Autowired
    private CommandGateway commandGateway;

    @PostMapping
    private String addBook(@RequestBody BookRequestModel bookRequestModel) {
        CreateBookCommand createBookCommand = new CreateBookCommand(UUID.randomUUID().toString(), bookRequestModel.getName(), bookRequestModel.getAuthor(), true);
        return commandGateway.sendAndWait(createBookCommand);
    }
}
