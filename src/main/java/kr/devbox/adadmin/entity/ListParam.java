package kr.devbox.adadmin.entity;

import kr.devbox.adadmin.dto.ListParamDTO;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ListParam {
    @Builder
    public ListParam(Integer offset,Integer limit, String sort, String order) {
        this.offset = offset;
        this.limit = limit;
        this.sort = sort;
        this.order = order;
    }
    private Integer     offset;
    private Integer     limit;
    private String      sort;
    private String      order;
}
