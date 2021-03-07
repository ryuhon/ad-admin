package kr.devbox.adadmin.dto;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ListParamDTO {
    private Integer     offset;
    private Integer     limit;
    private String      sort;
    private String      order;
}
