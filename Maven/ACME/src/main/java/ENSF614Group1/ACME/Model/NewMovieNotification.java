package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("newMovieNotification")
public class NewMovieNotification extends Email {
	
	public static String NewMovieNotificationKey = "newMovieNotification";
			    
    public NewMovieNotification() {
    	super();
    }
    
    public NewMovieNotification(Email email) {
    	super(email);
    }
    
    public NewMovieNotification(
    		String title,
    		String body,
    		LocalDateTime sentAt,
    		String to
    		)
    {
    	super(title, body, sentAt, to);    	
    }
    
}
