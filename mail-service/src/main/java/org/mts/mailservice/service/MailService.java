package org.mts.mailservice.service;

import org.mts.mailservice.configs.EmailSenderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService implements IMailService{

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailSenderInfo emailSenderInfo;


    /**
     * @param to
     * @param subject
     * @param htmlBody
     */
    @Override
    public void sendVerificationEmail(String to, String subject, String htmlBody) {
    }
}
