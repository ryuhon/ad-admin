package kr.devbox.adadmin.entity;

import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Builder
    public Member(int mid, String member_id, String passwd, String name) {
        this.mid = mid;
        this.member_id = member_id;
        this.password = passwd;
        this.name = name;
    }
    private int         mid;
    private String      member_id;
    private String      password;
    private String      name;
}
