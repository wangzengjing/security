package com.wzj.security.config;

import com.wzj.security.config.service.UserDetailsServiceImpl;
import com.wzj.security.filter.MyFilterInvocationSecurityMetadataSource;
import com.wzj.security.handler.AuthenctiationSuccessHandler;
import com.wzj.security.manager.MyAccessDecisionManager;
import com.wzj.security.validatecode.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Autowired
    private AuthenctiationSuccessHandler authenctiationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Autowired
    private MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;

    @Autowired
    private MyAccessDecisionManager myAccessDecisionManager;


    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                                             @Override
                                             public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                                                 o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
                                                 o.setAccessDecisionManager(myAccessDecisionManager);
                                                 return o;
                                             }
                                         })
                //固定权限设置
//                .antMatchers("/login","/register/**","/validCode").permitAll()
//                .antMatchers("/user/**").hasRole("USER")
//                .antMatchers("/sys/**").hasRole("SYS")
               .anyRequest().authenticated()
        .and().formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .successHandler(authenctiationSuccessHandler)
        .failureHandler(authenticationFailureHandler)
        .and().logout()
        .logoutUrl("/logout")
        ;
      //  http.formLogin().successHandler(authenctiationSuccessHandler);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//内存中储存账号密码
//        auth.inMemoryAuthentication().passwordEncoder(bCryptPasswordEncoder())
//                .withUser("wzj").password(bCryptPasswordEncoder().encode("123456")).roles("USER");

   //JDBC储存账号密码验证
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring() .antMatchers(
                HttpMethod.GET,
                "/*.html",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
        )
        .antMatchers("/login","/register/**","/validCode","/favicon.ico");
    }
}
