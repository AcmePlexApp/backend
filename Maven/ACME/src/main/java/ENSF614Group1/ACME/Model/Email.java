package ENSF614Group1.ACME.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "email_type", discriminatorType = DiscriminatorType.STRING)
public class Email {
	
	static int BODY_LENGTH = 255;
		
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String body;
    private LocalDateTime sentAt;
    private String toEmail;
    
    public Long getID() {return id;}
    public String getTitle() {return title;}
    public String getBody() {return body;}
    public LocalDateTime getSentAt() {return sentAt;}
    public String getToEmail() {return toEmail;}
    
    public void setTitle(String title) {this.title = title;}
    public void setBody(String body) {
    	String longBody = body;
    	this.body = longBody.length() > BODY_LENGTH ? longBody.substring(0, BODY_LENGTH) : longBody;
    			;}
    public void setSentAt(LocalDateTime sentAt) {this.sentAt = sentAt;}
    public void setToEmail(String toEmail) {this.toEmail = toEmail;}

    public Email() {
    	sentAt = LocalDateTime.now();
    }
    
    public Email(
    		String title,
    		String body,
    		LocalDateTime sentAt,
    		String toEmail
    	)
    {
    	this.title = title;
    	String longBody = body;
    	this.body = longBody.length() > BODY_LENGTH ? longBody.substring(0, BODY_LENGTH) : longBody;
    	this.sentAt = sentAt;
    	this.toEmail = toEmail;    	
    }
    
    
    public Email(Email email) {
    	this.id = email.id;
    	this.title = email.title;
    	this.body = email.body;
    	this.sentAt = email.sentAt;
    	this.toEmail = email.toEmail;
    }
    

}
