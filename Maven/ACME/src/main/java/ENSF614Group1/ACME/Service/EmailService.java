package ENSF614Group1.ACME.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ENSF614Group1.ACME.Repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import ENSF614Group1.ACME.Model.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmailService {
	
	static String OUR_EMAIL = "acmemovieplex@gmail.com";
	
	@Autowired
    private JavaMailSender mailSender;
	
    public void sendEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getToEmail());
        message.setSubject(email.getTitle());
        message.setText(email.getBody());
        message.setFrom(OUR_EMAIL);
        mailSender.send(message);
    }
    
}