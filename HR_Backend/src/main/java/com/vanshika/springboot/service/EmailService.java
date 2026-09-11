package com.vanshika.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vanshika.springboot.model.EmailDetails;
import com.vanshika.springboot.model.User;
import com.vanshika.springboot.repository.UserRepo;

@Service
public class EmailService {
	
	@Value("${spring.mail.username}")
    private String sender;
	
	@Autowired
    private JavaMailSender javaMailSender;

    // Send simple mail
    public String sendSimpleMail(EmailDetails details) {

        try {

            SimpleMailMessage mailMessage =
                    new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);

            return "Mail Sent Successfully";

        } catch (Exception e) {

            return "Error while sending mail";
        }
    }
}
