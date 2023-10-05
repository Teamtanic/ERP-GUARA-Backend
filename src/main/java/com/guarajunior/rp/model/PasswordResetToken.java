package com.guarajunior.rp.model;


import java.util.Date;
import java.util.Calendar;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class PasswordResetToken {
	private static final int EXPIRATION_MINUTES  = 60 * 24;
	 
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_user")
    private User user;

    private Date expiryDate;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
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
