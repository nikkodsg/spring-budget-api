package com.nikkodasig.springbudgetapi.email;

public interface EmailSender {
    void send(String to, String email);
}
