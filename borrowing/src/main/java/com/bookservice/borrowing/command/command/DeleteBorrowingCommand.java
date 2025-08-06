package com.bookservice.borrowing.command.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.awt.print.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteBorrowingCommand {
    @TargetAggregateIdentifier
    private String id;
}
