package kr.devbox.adadmin.dto;

import lombok.Data;

@Data
public class AuthResultDTO {
    private String memberID;
    private String token;
    private String name;
}
