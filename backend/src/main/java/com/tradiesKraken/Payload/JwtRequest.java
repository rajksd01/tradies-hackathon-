package com.tradiesKraken.Payload;

import lombok.Data;

@Data
public class JwtRequest {

    private String username;
    private String password;
}
