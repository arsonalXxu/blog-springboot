package hello.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.service.UserService;
import org.apache.catalina.session.StandardSessionFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class AuthControllerTest {
    private MockMvc mockMvc;
    @Mock
    AuthenticationManager mockManager;
    @Mock
    UserService mockService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AuthController(mockManager, mockService)).build();
    }

    @Test
    void returnNotLoginByDefault() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/auth"))
                .andExpect(status().isOk())
                .andExpect(result ->
                        Assertions.assertTrue(result.getResponse().getContentAsString().contains("false")));
    }

    @Test
    void testLogin() throws Exception {
        // 未登录是/auth接口返回未登录状态
        mockMvc.perform(MockMvcRequestBuilders.get("/auth"))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("false")));

        // 使用/auth/login登录
        Map<String, String> usernamePassword = new HashMap<>();
        usernamePassword.put("username", "myUser");
        usernamePassword.put("password", "myPassword");
        Mockito.when(mockService.loadUserByUsername("myUser"))
                .thenReturn(new User("myUser", bCryptPasswordEncoder.encode("myPassword"), Collections.emptyList()));
        Mockito.when(mockService.getUserByUsername("myUser"))
                .thenReturn(new hello.entity.User(123L, "myUser", bCryptPasswordEncoder.encode("myPassword")));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(usernamePassword)))
                .andExpect(status().isOk())
                .andReturn();
        HttpSession session = mvcResult.getRequest().getSession();

        // 登录后，/auth接口返回已登录状态
        mockMvc.perform(MockMvcRequestBuilders.get("/auth").session(((MockHttpSession) session)))
                .andExpect(status().isOk())
                .andExpect(result -> Assertions.assertTrue(result.getResponse().getContentAsString().contains("true")));

    }

}