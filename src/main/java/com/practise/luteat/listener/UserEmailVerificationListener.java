package com.practise.luteat.listener;

import com.practise.luteat.event.UserRegistrationEvent;
import com.practise.luteat.exceptions.UserNotificationEmailException;
import com.practise.luteat.model.User;
import com.practise.luteat.service.UserEmailVerificationService;
import com.practise.luteat.utils.MailContentBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserEmailVerificationListener implements ApplicationListener<UserRegistrationEvent> {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    private final UserEmailVerificationService userEmailVerificationService;

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        User user = event.getUser();
        String email = user.getEmail();
        String token = userEmailVerificationService.generateVerificationTokenByUsername(user.getUsername());
        sendMail(email, token);
    }


    private void sendMail(String email, String token){
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("LutEat@gmail.com");
            messageHelper.setTo(email);
            messageHelper.setSubject("Please activate your LutEat account to use the app");
            messageHelper.setText(mailContentBuilder.build("Thank you for signing up on LutEat Application, " +
                    "please click on the below url to activate your account: " +
                    "http://localhost:" +
                    "8099/api/auth/accountVerification/" + token));
        };

        try{
            mailSender.send(messagePreparator);
            log.info("Activation email sent");
        }catch(MailException e){
            log.error("Failure when sending Activation email to this email: {}", email);
            throw new UserNotificationEmailException("Exception occurred when sending verifiation link to this email: " + email);
        }
    }

}
