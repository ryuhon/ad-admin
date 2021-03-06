package kr.devbox.adadmin.service;

import kr.devbox.adadmin.dto.SessionDTO;
import kr.devbox.adadmin.entity.Member;
import kr.devbox.adadmin.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {






    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final  MemberMapper memberMapper;
    public AuthService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String memberID) throws UsernameNotFoundException {
        Member member = memberMapper.memberGetOne(memberID);
        SessionDTO session = new SessionDTO();
        session.setMid(member.getMid());
        session.setMemberID(member.getMember_id());
        session.setName(member.getName());
        session.setPassword(member.getPassword());
        session.setAuthorities(getAuthorities(memberID));
        return session;
    }

    public Collection<GrantedAuthority> getAuthorities(String memberID) {
        List<GrantedAuthority> authorities = memberMapper.authorityList(memberID);
        return authorities;
    }

    public SessionDTO getSessionByMemberID(String memberID) throws UsernameNotFoundException {
        Member member = memberMapper.memberGetOne(memberID);
        SessionDTO session = new SessionDTO();
        session.setMid(member.getMid());
        session.setMemberID(member.getMember_id());
        session.setName(member.getName());
        session.setPassword(member.getPassword());
        session.setAuthorities(getAuthorities(memberID));
        return session;
    }
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }


}
