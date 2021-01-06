package com.dawid.services;

public interface EmailService {

    void sendEmail(String to, String subject, String content);

}
