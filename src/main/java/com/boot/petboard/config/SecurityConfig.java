package com.boot.petboard.config;

import com.boot.petboard.service.KakaoOAuth2UserService;
import com.boot.petboard.service.KakaoUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final KakaoOAuth2UserService oAuth2UserService;
    private final KakaoUserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.formLogin((it) -> it
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/login/error")
        );

        http.oauth2Login(oauth2Configurer -> oauth2Configurer
                .loginPage("/kakao/login")
                .successHandler(successHandler())
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserService)));


        http.authorizeHttpRequests((req) -> {req
                .requestMatchers(antMatcher("/")).permitAll()
                .requestMatchers(antMatcher("/login")).permitAll()
                .requestMatchers(antMatcher("/create_account")).permitAll()
                .requestMatchers(antMatcher("/kakao/**")).permitAll()
                .requestMatchers(antMatcher("/members/**")).permitAll()
                .requestMatchers(antMatcher("/css/**")).permitAll()
                .requestMatchers(antMatcher("/favicon.ico")).permitAll()
                .requestMatchers(antMatcher("/checkDuplicateId")).permitAll()
                .anyRequest().authenticated(); });http.authorizeHttpRequests(config -> config.anyRequest().permitAll());

        http.logout((it) -> it
                .logoutRequestMatcher(antMatcher("/kakao/logout"))
                .logoutSuccessUrl("/")
        );
        http.exceptionHandling( (it) ->
                it.authenticationEntryPoint(new CustomAuthenticationEntryPoint("/123123"))
        );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

            String id = defaultOAuth2User.getAttributes().get("id").toString();
            String email = defaultOAuth2User.getAttributes().get("kakao_account").toString();

            String regex = "\\b[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\\b";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            // 이메일 주소를 찾았을 때만 값을 추출하여 사용
            if (matcher.find()) {
                email = matcher.group();
            }
            String body = """
                    {"id":"%s",
                     "email":"%s"}
                    """.formatted(id,email);

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());

            //db에 저장
            userService.saveUserFromJson(email);

            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("userId", id);
            session.setAttribute("userEmail", email);

            // 로그인 성공시 메인페이지 이동
            response.sendRedirect("/");
        });
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()
                );
    }
}