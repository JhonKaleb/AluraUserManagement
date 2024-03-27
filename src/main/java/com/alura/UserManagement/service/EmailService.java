package com.alura.UserManagement.service;

public class EmailService {
    public static void send(String recipientEmail, String subject, String body) {
        System.out.printf("Sending Feedback email to [%s]:\n%n", recipientEmail);
        System.out.printf("""
                Subject: New low rate to your course %s
                Body: Comment in the rate was: \n%s
                %n""", subject, body);
    }
}
