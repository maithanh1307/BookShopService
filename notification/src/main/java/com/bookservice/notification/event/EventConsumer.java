package com.bookservice.notification.event;

import com.bookservice.commonservice.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.RetriableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EventConsumer {
    @Autowired
    private EmailService emailService;

    @RetryableTopic(
            attempts = "4", // 3 topic retry + 1 topic DLQ
            backoff = @Backoff(delay = 1000, multiplier = 2),
            autoCreateTopics = "true",
            dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR,
            include = {RetriableException.class, RuntimeException.class}

    )
    @KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")
    public void listen(String message) {
        log.info("Receive message: " + message);
        throw new RuntimeException("Error test");
    }

    @DltHandler
    void processDltMessage(@Payload String message) {
        log.info("DLT received message: " + message);
    }

    @KafkaListener(topics = "testEmail", containerFactory = "kafkaListenerContainerFactory")
    public void testEmail(String message) {
        log.info("Receive message: " + message);

        String template = """
            <html>
              <body style="font-family: Arial, sans-serif; background-color: #f6f6f6; padding: 20px;">
                <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                  <h2 style="color: #333;">Hello %s,</h2>
                  <p style="font-size: 16px; color: #555;">
                    We have received your message:
                  </p>
                  <blockquote style="border-left: 4px solid #4CAF50; margin: 20px 0; padding-left: 15px; color: #333;">
                    %s
                  </blockquote>
                  <p style="font-size: 16px; color: #555;">
                    Thank you for contacting us. We will get back to you as soon as possible.
                  </p>
                  <p style="font-size: 16px; color: #555;">Sincerely,<br/>The BookService Team</p>
                </div>
              </body>
            </html>
            """;

        String filled = String.format(template, "Mai Thanh", message);
        emailService.sendEmail(message,"Thanks you for your mail", filled, true, null);
    }
}
