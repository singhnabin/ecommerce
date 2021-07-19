package com.nabinsingh34.ecommerc.service;

import com.nabinsingh34.ecommerc.exception.SpringEcommerceException;
import com.nabinsingh34.ecommerc.model.NotificationEmail;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class MailService {


    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailContentBuilder mailContentBuilder;


    public void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator;
        messagePreparator = mimeMessage -> {
            MimeMessageHelper mimeMessageHelper= new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("technova@gmail.com");
            mimeMessageHelper.setTo(notificationEmail.getRecipient());
            mimeMessageHelper.setSubject(notificationEmail.getSubject());
            mimeMessageHelper.setText(mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Activation email sent to "+notificationEmail.getRecipient());
        } catch (MailException e){
            throw new SpringEcommerceException("Exception occured when sending mail to "+notificationEmail.getRecipient());
        }
    }
}
