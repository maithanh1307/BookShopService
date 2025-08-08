package com.bookservice.commonservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollbackStatusBookCommand {
    @TargetAggregateIdentifier
    private String bookId;
    private Boolean isReady;
    private String employeeId;
    private String borrowingId;
}
