package org.mts.mailservice.service;


public interface IMailService {
    public void sendVerificationEmail(String to, String subject, String htmlBody);
}
