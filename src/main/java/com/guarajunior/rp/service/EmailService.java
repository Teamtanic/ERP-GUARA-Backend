package com.guarajunior.rp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailService {
	 @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String toEmail, String token, HttpServletRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Redefinição de Senha");
        mailMessage.setText("Use o seguinte link para redefinir sua senha: " + generateResetPasswordLink(token, request));
        javaMailSender.send(mailMessage);
    }

    private String generateResetPasswordLink(String token, HttpServletRequest request) {
    	String scheme = request.getScheme(); // "http" ou "https"
	    String serverName = request.getServerName(); // nome do servidor (por exemplo, "localhost" ou "www.exemplo.com")
	    int serverPort = request.getServerPort(); // porta do servidor (por exemplo, 80 ou 443 para HTTP/HTTPS)
	    String contextPath = request.getContextPath(); // contexto da aplicação (por exemplo, "/minha-aplicacao")

	    StringBuilder appUrl = new StringBuilder();
	    appUrl.append(scheme).append("://").append(serverName);

	    // Se o servidor estiver em uma porta diferente da padrão, adicione a porta à URL
	    if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
	        appUrl.append(":").append(serverPort);
	    }

	    // Adicione o contexto da aplicação à URL
	    appUrl.append(contextPath);

	    System.out.println(appUrl.toString());
	    return appUrl.toString() + "/reset-password?token=" + token;
    }
}
