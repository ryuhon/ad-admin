package kr.devbox.adadmin.mapper;

import kr.devbox.adadmin.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface MemberMapper {
    public Member                   memberGetOne(@Param("member_id") String memberID);
    public List<GrantedAuthority>   authorityList(@Param("member_id") String memberID);
}
