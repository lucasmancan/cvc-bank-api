package br.com.cvcbank.configurations.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials implements Serializable {
    private String document;
    private String password;

    public String getDocument() {
        if (document != null) {
            return document.trim().replaceAll("\\D", "");
        }
        return document;
    }
}