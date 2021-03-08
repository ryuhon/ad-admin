package kr.devbox.adadmin.dto;

import lombok.Getter;

@Getter
public class RestDTO {
    private boolean success;
    private Object data;
    private Object message;
    private Integer total;

    public RestDTO(boolean success, Object data, Object message) {
        this.success = success;
        this.data = data;
        this.message = message;

    }
    public RestDTO(Object data, Object message) {
        this.success = true;
        this.data = data;
        this.message = message;

    }
    public RestDTO(Object data, Integer total) {
        this.success = true;
        this.data = data;
        this.message = "";
        this.total = total;

    }
}
