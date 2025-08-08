package com.bookservice.borrowing.command.saga;

import com.bookservice.borrowing.command.command.DeleteBorrowingCommand;
import com.bookservice.borrowing.command.event.BorrowingCreatedEvent;
import com.bookservice.borrowing.command.event.BorrowingDeleteEvent;
import com.bookservice.commonservice.command.RollbackStatusBookCommand;
import com.bookservice.commonservice.command.UpdateStatusBookCommand;
import com.bookservice.commonservice.event.BookRollBackStatusEvent;
import com.bookservice.commonservice.event.BookUpdateStatusEvent;
import com.bookservice.commonservice.model.BookResponseCommonModel;
import com.bookservice.commonservice.model.EmployeeResponseCommonModel;
import com.bookservice.commonservice.queries.GetBookDetailQuery;
import com.bookservice.commonservice.queries.GetDetailEmployeeQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Saga
public class BorrowingSaga {
    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handler(BorrowingCreatedEvent event) {
        log.info("Borrowing created event in saga for Book ID: " + event.getBookId() + " : Employee ID: " + event.getEmployeeId());
        try {
            GetBookDetailQuery getBookDetailQuery = new GetBookDetailQuery(event.getBookId());
            BookResponseCommonModel bookResponseCommonModel = queryGateway.query(getBookDetailQuery,
                                    ResponseTypes.instanceOf(BookResponseCommonModel.class)).join();
            if(!bookResponseCommonModel.getIsReady()) {
                throw new Exception("Sach da co nguoi muon");
            }
            else {
                SagaLifecycle.associateWith("bookId", event.getBookId());
                // bookservice xu li va update lai
//                UpdateStatusBookCommand command = new UpdateStatusBookCommand(event.getBookId(), false,
//                                                                        event.getId(), event.getEmployeeId());
                UpdateStatusBookCommand command = new UpdateStatusBookCommand(
                        event.getBookId(), false, event.getEmployeeId(), event.getId());

                commandGateway.sendAndWait(command);
            }
        }
        catch (Exception e) {
            rollbackBorrowingRecord(event.getId());
            log.error(e.getMessage());
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookUpdateStatusEvent event) {
        log.info("Book update status event in saga for Book ID: " + event.getBookId());
        log.info("BookUpdateStatusEvent - employeeId: {}", event.getEmployeeId());
        try {
            GetDetailEmployeeQuery query = new GetDetailEmployeeQuery(event.getEmployeeId());
            EmployeeResponseCommonModel employeeModel = queryGateway.query(query,
                            ResponseTypes.instanceOf(EmployeeResponseCommonModel.class)).join();
            if (employeeModel.getIsDisciplined()) {
                throw new Exception("Nhan vien bi ki luat");
            }
            else {
                log.info("Da muon sach thanh cong");
                SagaLifecycle.end();
            }
        }
        catch(Exception e) {
            rollBackBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowingId());
            log.error(e.getMessage());
        }
        SagaLifecycle.end();
    }


    private void rollbackBorrowingRecord(String id) {
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        commandGateway.sendAndWait(command);

    }

    private void rollBackBookStatus(String bookId, String employeeId, String borrowingId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollbackStatusBookCommand rollbackStatusBookCommand = new RollbackStatusBookCommand(bookId, true, employeeId,
                                                                borrowingId);
        commandGateway.sendAndWait(rollbackStatusBookCommand);
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookRollBackStatusEvent event) {
        log.info("Book rollback status event in saga for Book ID: " + event.getBookId());
        rollbackBorrowingRecord(event.getBookId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    private void handle(BorrowingDeleteEvent event) {
        log.info("Borrowing delete event in saga for borrowing id: " + event.getId());
        SagaLifecycle.end();
    }

}
