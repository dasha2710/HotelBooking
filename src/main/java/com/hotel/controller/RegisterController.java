package com.hotel.controller;

import com.google.common.collect.Lists;
import com.hotel.domain.Client;
import com.hotel.domain.Role;
import com.hotel.domain.User;
import com.hotel.service.UserService;
import com.hotel.validators.RegisterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dasha on 12.04.2015.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterFormValidator registerFormValidator;
    @Autowired
    @Qualifier("authManager")
    protected AuthenticationManager authManager;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registerFormValidator);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(Model model) {
        Client client = new Client();
        client.setUser(new User());
        model.addAttribute("client", client);
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String savingUser(Model model, @Validated Client client, BindingResult result, HttpServletRequest request) {
        if (!result.hasErrors()) {
            Client savedUser = userService.saveClient(client);
            model.addAttribute("client", savedUser);
            User user = savedUser.getUser();
            autoLogin(user.getLogin(), user.getMatchingPassword(), request);
            return "redirect:/apartments";
        }
        return "register";
    }

    private void autoLogin(String username, String password, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username,
                        password,
                        Lists.newArrayList(Role.CLIENT));

        Authentication authentication = authManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }
}