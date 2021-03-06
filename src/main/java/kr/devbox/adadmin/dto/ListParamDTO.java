package kr.devbox.adadmin.dto;

import lombok.Data;

@Data
public class ListParamDTO {
    private Integer     offset;
    private Integer     limit;
    private Integer     start;
    private Integer     end;
    private String      sort;
    private String      order;
}
