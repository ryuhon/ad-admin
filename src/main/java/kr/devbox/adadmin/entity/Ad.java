package kr.devbox.adadmin.entity;

import lombok.*;

import java.util.Date;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ad {

    @Builder
    public Ad(int aid, int mid, String title, String description, int banner_size, String banner_url,
              int platform_type, String impression_tracking_url, String redirect_url) {

        this.aid = aid;
        this.mid = mid;
        this.title = title;
        this.description = description;
        this.banner_size = banner_size;
        this.banner_url = banner_url;
        this.platform_type = platform_type;
        this.impression_tracking_url = impression_tracking_url;
        this.redirect_url = redirect_url;

    }
    private int     aid;
    private int     mid;
    private String  title;
    private String  description;
    private int     banner_size;
    private String  banner_url;
    private int     platform_type;
    private String  impression_tracking_url;
    private String  redirect_url;
    private Date    reg_date;
}
