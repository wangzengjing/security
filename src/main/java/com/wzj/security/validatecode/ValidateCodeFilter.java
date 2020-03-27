package com.wzj.security.validatecode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//       HttpServletRequest req = (HttpServletRequest) request;
//
//       ServletWebRequest servletWebRequest = new ServletWebRequest(req);
//        try {
//            validate(req,servletWebRequest);
//        }catch (ValidateCodeException e){
//            authenticationFailureHandler.onAuthenticationFailure();
//        }
//
//
//        chain.doFilter(request,response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse rep, FilterChain filterChain) throws ServletException, IOException {


       ServletWebRequest servletWebRequest = new ServletWebRequest(req);
        try {
            validate(req,servletWebRequest);
        }catch (ValidateCodeException e){
            authenticationFailureHandler.onAuthenticationFailure(req,rep,e);
            return;
        }


        filterChain.doFilter(req,rep);
    }

    private void validate(HttpServletRequest req,ServletWebRequest servletWebRequest){

        if("POST".equalsIgnoreCase(req.getMethod()) && "/login".equals(req.getServletPath())) {
            //从内存中获取相应的验证码对象
            ImageCode codeInSession = (ImageCode) sessionStrategy.getAttribute(servletWebRequest, Constant.SESSION_KEY);

            String codeInRequest = req.getParameter("validCode");

            if(StringUtils.isBlank(codeInRequest)){
                throw new ValidateCodeException("验证码的值不能为空");
            }

            if (codeInSession.isExpired()) {
                sessionStrategy.removeAttribute(servletWebRequest, Constant.SESSION_KEY);
                throw new ValidateCodeException("验证码已过期");
            }

            if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
                throw new ValidateCodeException("验证码不匹配");
            }

            log.info("验证码:" + req.getParameter("validCode"));
            log.info("验证码匹配成功，删除验证码缓存");

            sessionStrategy.removeAttribute(servletWebRequest,Constant.SESSION_KEY);
        }

    }
}
