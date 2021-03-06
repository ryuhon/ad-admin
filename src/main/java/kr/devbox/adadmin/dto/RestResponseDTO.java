package kr.devbox.adadmin.dto;

import lombok.Getter;

@Getter
public class RestResponseDTO {
    private boolean success;
    private Object data;
    private String message;

    public RestResponseDTO(boolean success, Object data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;

    }
}
