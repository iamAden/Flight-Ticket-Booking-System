package com.ft.flight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ft.flight.entity.ContactForm;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(ContactForm contactForm) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("22004860@siswa.um.edu.my");
            message.setFrom("everlynyinjiamay@gmail.com"); 
            message.setSubject(contactForm.getSubject());
            message.setText("Name: " + contactForm.getName() + "\n" +
                    "Email: " + contactForm.getEmail() + "\n" +
                    "Message: " + contactForm.getMessage());
            System.out.println(message);
            javaMailSender.send(message);
            // Log success or additional information if needed
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // You can also throw the exception to propagate it to the calling method
            throw new RuntimeException("Error sending email: " + e.getMessage(), e);
        }
    }
    
}
