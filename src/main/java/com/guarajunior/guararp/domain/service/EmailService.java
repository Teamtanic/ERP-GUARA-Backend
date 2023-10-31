package com.guarajunior.guararp.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetEmail(String toEmail, String token, HttpServletRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Redefinição de Senha");
        mailMessage.setText("Use o seguinte link para redefinir sua senha: " + generateResetPasswordLink(token, request));
        javaMailSender.send(mailMessage);
    }

    public void sendCreateNewAccountEmail(String toEmail, String token, HttpServletRequest request) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Crie uma nova conta");
        mailMessage.setText("Use o seguinte link para criar sua conta: " + generateRegisterLink(token, request));
        javaMailSender.send(mailMessage);
    }

    private String generateResetPasswordLink(String token, HttpServletRequest request) {
        String appUrl = getAppUrl(request);

        return appUrl + "/reset-password?token=" + token;
    }

    private String generateRegisterLink(String token, HttpServletRequest request) {
        String appUrl = getAppUrl(request);
        return appUrl + "/auth/registro?token=" + token;
    }

    private String getAppUrl(HttpServletRequest request) {
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

        return appUrl.toString();
    }
}
