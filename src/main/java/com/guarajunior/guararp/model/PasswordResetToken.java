package com.guarajunior.guararp.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

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
