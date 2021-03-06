package kr.devbox.adadmin.entity;

import kr.devbox.adadmin.dto.ListParamDTO;
import lombok.Data;
import lombok.Getter;

@Getter
public class ListParam {

    public ListParam(Integer offset,Integer limit, Integer start, Integer end, String sort, String order) {
        this.offset = offset;
        this.limit = limit;
        this.start = start;
        this.end = end;
        this.sort = sort;
        this.order = order;
    }
    private Integer     offset;
    private Integer     limit;
    private Integer     start;
    private Integer     end;
    private String      sort;
    private String      order;
}
