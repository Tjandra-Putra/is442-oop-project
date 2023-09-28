package controller;

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
import gs.common.InputModel;
import gs.common.RequestModel;
import gs.controller.UserController;
import gs.service.user.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BackendApplication.class)
@ActiveProfiles("test")
public class UserControllerTest {
    @MockBean
    RequestModel requestModel = new RequestModel();
    InputModel inputModel = new InputModel();
    
    @MockBean
    UserService userService;

    @Autowired
    UserController userController;
    MockMvc mvc;


    public void init(){
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void getUser() throws Exception {
        List<InputModel> userList = new ArrayList<>();
        InputModel inputModel = new InputModel();
        inputModel.setCodeDesc(null);
        inputModel.setCodeValue(null);
        inputModel.setData(null);
        userList.add(inputModel);

        when(userService.getUser()).thenReturn(userList);
        mvc.perform(get("/api/user/getUser"))
            .andExpect(status().isOk());
    }
}