package com.bookservice.commonservice.services;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import freemarker.template.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;


@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration config;

    public void sendEmail(String to, String subject, String text, boolean isHtml, File attachment) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, isHtml);

//            add attachment if provided
            if (attachment != null) {
                FileSystemResource fileSystemResource = new FileSystemResource(attachment);
                helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            }

            javaMailSender.send(message);
            log.info("Email sent successfully to{}", to);

        }
        catch(MessagingException e) {
            log.error("Failed to send email", to, e);
        }

    }

    public void senEmailWithTemplate(String to, String subject, String templateName, Map<String, Object> placeholder, File attachment) {
        try{
            Template t = config.getTemplate(templateName);
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, placeholder);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

//            add attachment if provided
            if (attachment != null) {
                FileSystemResource fileSystemResource = new FileSystemResource(attachment);
                helper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            }

            javaMailSender.send(message);
            log.info("Email sent successfully to{}", to);
        } catch(MessagingException | IOException e) {
            log.error("Failed to send email", to, e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
