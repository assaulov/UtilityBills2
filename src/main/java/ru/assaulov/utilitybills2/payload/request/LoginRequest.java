package ru.assaulov.utilitybills2.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String password;
}
