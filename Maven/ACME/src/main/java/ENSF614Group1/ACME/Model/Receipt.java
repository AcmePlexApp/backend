package ENSF614Group1.ACME.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("receipt")
public class Receipt extends Email {
	
	public static String ReceiptKey = "receipt";
			    
    public Receipt() {
    	super();
    }
    
    public Receipt(Email email) {
    	super(email);
    }
    
    public Receipt(
    		String title,
    		String body,
    		LocalDateTime sentAt,
    		String to
    		)
    {
    	super(title, body, sentAt, to);    	
    }
    
    
}
