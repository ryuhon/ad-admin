package kr.devbox.adadmin.dto;

import kr.devbox.adadmin.entity.Ad;
import lombok.Data;

import java.util.Date;

@Data
public class AdParamDTO {
    private int     aid;
    private int     mid;
    private String  title;
    private String  description;
    private int     bannerSize;
    private String  bannerUrl;
    private int     platformType;
    private String  impressionTrackingUrl;
    private String  redirectUrl;
    private Date    regDate;


}
