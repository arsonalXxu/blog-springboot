package hello.service;

import hello.entity.User;
import hello.dao.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Inject
    public UserService(UserMapper userMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userMapper = userMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void save(String username, String password) {
        userMapper.save(username, bCryptPasswordEncoder.encode(password));
    }

    public User getUserById(long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " 不存在！");
        }

        String encodedPassword = getUserByUsername(username).getEncryptedPassword();

        return new org.springframework.security.core.userdetails.User(username, encodedPassword, Collections.emptyList());
    }

    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }
}
