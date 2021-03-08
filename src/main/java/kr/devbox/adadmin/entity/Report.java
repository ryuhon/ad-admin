package kr.devbox.adadmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {


    private int     aid;
    private String  title;
    private int     request;
    private int     impression;
    private int     click;
}
