package kr.devbox.adadmin.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.devbox.adadmin.dto.AuthParamDTO;
import kr.devbox.adadmin.dto.AuthResultDTO;
import kr.devbox.adadmin.dto.SessionDTO;
import kr.devbox.adadmin.mapper.MemberMapper;
import kr.devbox.adadmin.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Slf4j
@Api(tags = {"1. Auth"})
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    AuthService authService;
    @Autowired
    MemberMapper memberMapper;

    @ApiOperation(value = "로그인", notes = "로그인")
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public Object login(
            @RequestBody AuthParamDTO authParamDTO,
            HttpSession httpSession
    )  throws UnsupportedEncodingException {



        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authParamDTO.getMemberID(), authParamDTO.getPassword());


        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (InternalAuthenticationServiceException ex) {
            throw new InternalAuthenticationServiceException("로그인 실패");
        }



        httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());

        SessionDTO sessionDTO  = authService.getSessionByMemberID(authParamDTO.getMemberID());


        log.info("mid : " + sessionDTO.getMid());
        log.info("member id : " + sessionDTO.getMemberID());
        log.info("name : " + sessionDTO.getName());


         String tokenString  =  JWT.create()
                .withIssuer("adadmin")
                .withClaim("mid",sessionDTO.getMid())
                .withClaim("member_id",sessionDTO.getMemberID())
                .withClaim("name",sessionDTO.getName())
                .withClaim("session",httpSession.getId())
                .withExpiresAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.ofHours(9))))
                .sign(Algorithm.HMAC256("rhkdrhtptus20210307"));


        AuthResultDTO authResultDTO = new AuthResultDTO();
        authResultDTO.setMemberID(sessionDTO.getMemberID());
        authResultDTO.setName(sessionDTO.getName());
        authResultDTO.setToken(tokenString);

        return authResultDTO;
    }
    @ApiOperation(value = "로그아웃", notes = "로그아웃")
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(HttpSession session){
        return "{result:'logoutok'}";
    }
}
