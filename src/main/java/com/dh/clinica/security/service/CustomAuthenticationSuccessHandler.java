package com.dh.clinica.security.service;

import com.dh.clinica.security.entity.AppUser;
import com.dh.clinica.security.enums.AppUserRole;
import com.dh.clinica.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RestController
@RequestMapping("/getuser")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();

        request.getSession().setAttribute("userRole", userRole);

        response.sendRedirect("/");
    }

    @GetMapping("/")
    public boolean esUser(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication1.getPrincipal();

        String username = userDetails.getUsername();

        AppUser appUser = this.userRepository.appUser(username);

        boolean esUsuario = false;

        if (appUser.getAppUserRole() == AppUserRole.USER){
            esUsuario = true;
        }

        return esUsuario;
    }
}