package kr.devbox.adadmin.mapper;

import kr.devbox.adadmin.dto.ListParamDTO;
import kr.devbox.adadmin.entity.Ad;
import kr.devbox.adadmin.entity.ListParam;
import kr.devbox.adadmin.entity.Member;
import kr.devbox.adadmin.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdMapper {
    public List<Ad>                     adList(ListParam param);
    public Integer                      adCount(ListParam param);
    public Ad                           adGetOne(@Param("aid") String aid);
    public Boolean                      adCreate(Ad ad);
    public Boolean                      adDelete(@Param("aid") String aid);
    public Boolean                      adUpdate(Ad ad);
    public List<Report>                       adReport();

}
