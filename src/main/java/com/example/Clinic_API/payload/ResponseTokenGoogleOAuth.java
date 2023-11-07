package com.example.Clinic_API.payload;

import lombok.Data;

@Data
public class ResponseTokenGoogleOAuth {
    String access_token;
    Long expires_in;
    String refresh_token;
    String scope;
    String token_type;
    String id_token;

}