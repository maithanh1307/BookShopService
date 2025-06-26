package com.bookservice.client.command.controller;

import com.bookservice.client.command.command.CreateBookCommand;
import com.bookservice.client.command.command.DeleteBookCommand;
import com.bookservice.client.command.command.UpdateBookCommand;
import com.bookservice.client.command.data.Book;
import com.bookservice.client.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{bookId}")
    private String updateBook(@PathVariable String bookId, @RequestBody BookRequestModel bookRequestModel) {
        UpdateBookCommand updateBookCommand = new UpdateBookCommand(bookId, bookRequestModel.getName(), bookRequestModel.getAuthor(), bookRequestModel.getIsReady());
        return commandGateway.sendAndWait(updateBookCommand);
    }

    @DeleteMapping("/{bookId}")
    private String deleteBook(@PathVariable String bookId) {
        DeleteBookCommand deleteBookCommand = new DeleteBookCommand(bookId);
        return commandGateway.sendAndWait(deleteBookCommand);
    }
}
