package kr.devbox.adadmin.dto;

import kr.devbox.adadmin.entity.Ad;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.Date;

@Data
public class AdParamDTO {
    private int     aid;
    private int     mid;
    @NotNull(message = "광고명을 입력해 주십시오.")
    @NotEmpty(message = "광고명을 입력해 주십시오.")
    private String  title;
    private String  description;
    @Min(value = 1,message = "배너 사이즈를 입력해 주십시오.")
    @Max(value = 2,message = "배너 사이즈를 입력해 주십시오.")
    private int     bannerSize;
    @NotEmpty(message = "배너 이미지를 업로드해 주십시오.")
    @Pattern(regexp = "(http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?",message = "배너 이미지를 업로드해 주십시오.")
    private String  bannerUrl;
    @NotNull(message = "플랩폼을 선택해주세요.")
    @Pattern(regexp = "^11$|^12$|^21$|^22$|^31$",message = "플랩폼을 선택해주세요.")
    private String     platformType;
    @NotNull(message = "노출 추적 URL을 입력해주십시오.")
    @Pattern(regexp = "(http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?",message = "노출 추적 URL을 입력해주십시오.")
    private String  impressionTrackingUrl;
    @NotNull(message = "리다이렉트 URL을 입력해주십시오.")
    @Pattern(regexp = "(http|https):\\/\\/(\\w+:{0,1}\\w*@)?(\\S+)(:[0-9]+)?(\\/|\\/([\\w#!:.?+=&%@!\\-\\/]))?",message = "리다이렉트 URL을 입력해주십시오.")
    private String  redirectUrl;
    private Date    regDate;


}
