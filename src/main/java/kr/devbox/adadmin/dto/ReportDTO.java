package kr.devbox.adadmin.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportDTO {


    private int     aid;
    private String  title;
    private int     request;
    private int     impression;
    private int     click; 
}
