package service;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import gs.BackendApplication;
import gs.common.RequestModel;
import gs.controller.UserController;
import gs.inputModel.UserInputModel;
import gs.repository.UserRepo;
import gs.service.user.UserService;
import gs.service.user.UserServiceImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles("test")
public class UserServiceImplTest {
    @MockBean
    private UserRepo userRepo;
    
    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private HttpServletResponse response;

    MockMvc mvc;


    public void init(){
        mvc = MockMvcBuilders.standaloneSetup(userServiceImpl).build();
    }

    @Test
    void getUser() {
        List<Object[]> userList = new ArrayList<>();
        Object[] obj1 = {"1", "ryan.water@gmail.com", "1234568", "Bob"};
        Object[] obj2 = {"2", "yanyan.water@gmail.com", "asdfghjkl", "LOL"};

        userList.add(obj1);
        userList.add(obj2);


        when(userRepo.getUser()).thenReturn(userList);
        List<UserInputModel> result = userServiceImpl.getUser();
        System.out.println(result);
        assertEquals(2, result.size());
    }
}