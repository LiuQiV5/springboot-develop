//package com.uni.common.springboot.config;
//
//import com.uni.common.springboot.filter.RequestTraceFilter;
//import com.uni.common.springboot.service.JwtUserDetailsService;
//import com.uni.common.springboot.util.JwtUtils;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.firewall.StrictHttpFirewall;
//
//import javax.servlet.Filter;
//
//@Configuration
//@EnableWebSecurity
//@AllArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    private final CustomAccessDeniedHandler accessDeniedHandler;
//
//    private final JwtUserDetailsService jwtUserDetailsService;
//
//    private final JwtUtils jwtUtils;
//
//    @Autowired
//    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//        authenticationManagerBuilder
//                // 设置UserDetailsService
//                .userDetailsService(this.jwtUserDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//                .exceptionHandling().accessDeniedHandler(accessDeniedHandler).and()
//                .csrf().disable()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .authorizeRequests()
//                .antMatchers(
//                        "/swagger-ui.html",
//                        "/swagger-resources/**",
//                        "/*/api-docs",
//                        "/webjars/**",
//                        "/",
//                        "/csrf",
//                        "/favicon.ico").permitAll()
//                .anyRequest().authenticated();
//
//        // 添加JWT filter
//        httpSecurity.addFilterBefore(new JwtAuthenticationTokenFilter(jwtUserDetailsService, jwtUtils), UsernamePasswordAuthenticationFilter.class);
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers(
//                "/swagger-ui.html",
//                "/swagger-resources/**",
//                "/*/api-docs",
//                "/webjars/**",
//                "/",
//                "/csrf",
//                "/favicon.ico",
//                "/**/*.css",
//                "/**/*.js",
//                "/**/*.png",
//                "/**/*.gif",
//                "/**/*.ttf");
//
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        //去掉";"
//        firewall.setAllowSemicolon(true);
//        //加入自定义的防火墙
//        web.httpFirewall(firewall);
//        super.configure(web);
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Bean
//    public FilterRegistrationBean<Filter> filterRegistrationBean() {
//        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean();
//        Filter mdcFilter = new RequestTraceFilter();
//
//        registrationBean.setFilter(mdcFilter);
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    }
//
//}
