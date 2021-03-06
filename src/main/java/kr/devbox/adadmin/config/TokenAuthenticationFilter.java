package kr.devbox.adadmin.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import kr.devbox.adadmin.dto.SessionDTO;
import kr.devbox.adadmin.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.GenericFilterBean;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class TokenAuthenticationFilter extends GenericFilterBean {
    private AuthService authService;
    private static final Long DAY = 3600L * 24;
    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {


        final HttpServletRequest httpRequest = (HttpServletRequest) request;

        //extract token from header
        boolean tokenExist = false;

        final String accessBearerToken = httpRequest.getHeader("Authorization");
        String accessToken = "";
        if (accessBearerToken != null ) {
            accessToken = accessBearerToken.substring(7, accessBearerToken.length());
        }
        if (!accessToken.equals("")) {
            tokenExist = true;
        }
        if (tokenExist) {

            try {


                JWTVerifier verifier = JWT.require(Algorithm.HMAC256("rhkdrhtptus20210307"))
                        .withIssuer("ad-admin.devbox.kr")
                        .acceptExpiresAt(DAY * 1) // 만료일 -1일
                        .build();

                DecodedJWT jwt = verifier.verify(accessToken);
                String memberID = jwt.getClaim("memberID").asString();

                ServletContext servletContext = request.getServletContext();
                WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
                authService = webApplicationContext.getBean(AuthService.class);
                final SessionDTO member = authService.getSessionByMemberID(memberID);


                if (member != null) {
                    final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(member, null, member.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

                chain.doFilter(request, response);
            } catch (JWTVerificationException verificationEx) {
                logger.info("Token VerifyFb Error");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

            }

        } else {

            if ((
                            !((HttpServletRequest) request).getRequestURI().equals("/") &&
                            !((HttpServletRequest) request).getRequestURI().equals("/swagger-ui.html") &&
                            ((HttpServletRequest) request).getRequestURI().indexOf("/webjars") == -1 &&
                            ((HttpServletRequest) request).getRequestURI().indexOf("/swagger-resources") == -1 &&
                            !((HttpServletRequest) request).getRequestURI().equals("/v2/api-docs") &&
                            !((HttpServletRequest) request).getRequestURI().equals("/api/login") &&
                            !((HttpServletRequest) request).getRequestURI().equals("/api/logout")

            ) && !((HttpServletRequest) request).getMethod().equals("OPTIONS")) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token is not valid.");
            } else {
                chain.doFilter(request, response);
            }

        }


    }

}