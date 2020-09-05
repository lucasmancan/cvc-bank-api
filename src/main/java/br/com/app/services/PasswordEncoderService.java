package br.com.app.services;

public interface PasswordEncoderService {
    String encode(String rawPassword);

    boolean equals(String rawPassword, String hash);
}
