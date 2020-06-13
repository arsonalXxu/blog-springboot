package hello.controller;

import hello.entity.LoginResult;
import hello.entity.User;
import hello.service.UserService;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.Map;
import java.util.regex.Pattern;

@RestController
public class AuthController {
    private UserService userService;
    private AuthenticationManager authenticationManager;
    Pattern usernamePattern = Pattern.compile("[\\w\\u4e00-\\u9fa5]{1,15}");
    Pattern passwordPattern = Pattern.compile(".{6,16}");

    @Inject
    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping("/auth")
    @ResponseBody
    public Object auth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(authentication == null ? null : authentication.getName());
        if (user != null) {
            return LoginResult.success(null, user);
        } else {
            return LoginResult.returnOk("用户没有登录");
        }
    }

    @PostMapping("/auth/register")
    @ResponseBody
    public Object register(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();
        boolean usernameMatch = usernamePattern.matcher(username).matches();
        boolean passwordMatch = passwordPattern.matcher(password).matches();
        if (!usernameMatch) {
            return LoginResult.failure("用户名长度须为1到15个字符，只能是数字字母下划线中文");
        }
        if (!passwordMatch) {
            return LoginResult.failure("密码长度要求6到16个字符");
        }
        try {
            userService.save(username, password);
        } catch (DuplicateKeyException e) {
            // 防止并发访问时，同时注册相同的用户名出现异常，数据库层面用户名做了一个unique处理
            return LoginResult.failure("用户名已经存在");
        }
        return LoginResult.returnOk("注册成功");
    }

    @GetMapping("/auth/logout")
    public LoginResult logout() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return LoginResult.failure("用户尚未登录");
        } else {
            SecurityContextHolder.clearContext();
            return LoginResult.returnOk("注销成功");
        }
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public LoginResult login(@RequestBody Map<String, Object> usernameAndPassword) {
        String username = usernameAndPassword.get("username").toString();
        String password = usernameAndPassword.get("password").toString();
        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            return LoginResult.failure("用户不存在");
        }
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        try {

            authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(token);
            return LoginResult.success("登录成功", userService.getUserByUsername(username));
        } catch (BadCredentialsException e) {
            return LoginResult.failure("密码不正确");
        }
    }

}
