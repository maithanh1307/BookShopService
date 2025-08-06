package com.bookservice.borrowing.command.aggregate;

import com.bookservice.borrowing.command.command.CreateBorrowingCommand;
import com.bookservice.borrowing.command.command.DeleteBorrowingCommand;
import com.bookservice.borrowing.command.event.BorrowingCreatedEvent;
import com.bookservice.borrowing.command.event.BorrowingDeleteEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Aggregate
public class BorrowingAggregate {
    @AggregateIdentifier
    private String id;
    private String bookId;
    private String employeeId;
    private Date borrowingDate;
    private Date returnDate;

    public BorrowingAggregate(){}

    @CommandHandler
    public BorrowingAggregate(CreateBorrowingCommand command) {
        BorrowingCreatedEvent event = new BorrowingCreatedEvent();
        BeanUtils.copyProperties(command, event);
        AggregateLifecycle.apply(event);

    }

    @CommandHandler
    public void handle(DeleteBorrowingCommand command) {
        BorrowingDeleteEvent event = new BorrowingDeleteEvent(command.getId());
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void on(BorrowingCreatedEvent event) {
        this.id = event.getId();
        this.bookId = event.getBookId();
        this.employeeId = event.getEmployeeId();
        this.borrowingDate = event.getBorrowingDate();

    }

    @EventSourcingHandler
    public void on(BorrowingDeleteEvent event) {
        this.id = event.getId();
    }
}
