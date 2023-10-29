package com.guarajunior.guararp.infra.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class RegisterToken {
	private static final int EXPIRATION_MINUTES  = 60 * 24;
	 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private String email;
    
    private Date expiryDate;
    
    public RegisterToken(String email, String token) {
    	this.email = email;
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION_MINUTES);
    }
    
    private Date calculateExpiryDate(int expiryMinutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, expiryMinutes);
        return calendar.getTime();
    }
    
    public Boolean isExpired() {
    	Calendar now = Calendar.getInstance();
    	now.setTime(new Date());
        Calendar expiry = Calendar.getInstance();
        expiry.setTime(expiryDate);
        if(now.compareTo(expiry) < 0) {
        	return false;
        } else {
        	return true;
        }
    }
}
