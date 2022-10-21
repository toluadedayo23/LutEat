package com.practise.luteat.service.impl;

import com.practise.luteat.exceptions.EmailVerificationException;
import com.practise.luteat.model.NotificationEmail;
import com.practise.luteat.utils.MailContentBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("LutEat@gmail.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };


        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent");
        } catch (MailException e) {
            log.error("Failure when sending Activation email to this email: {}", notificationEmail.getRecipient());
            throw new EmailVerificationException("Exception occured when sending mail to " + notificationEmail.getRecipient());
        }
    }
}
