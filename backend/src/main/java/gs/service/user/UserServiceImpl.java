package gs.service.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;

import gs.common.ApiModel;
import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.ChangePasswordRequest;
import gs.entity.User;
import gs.inputModel.UserInputModel;
import gs.repository.UserRepo;
import gs.service.passwordReset.PasswordResetTokenService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceImpl implements UserService{
    
    @Resource
    protected UserRepo userRepo;
    
    private NoOpPasswordEncoder noOpPasswordEncoder;
    
    // inputModel fitting methood
    private UserInputModel inputModel(User data){
        UserInputModel inputModel = new UserInputModel();
        inputModel.setId((Long) (data.getUserId()));
        inputModel.setEmail(String.valueOf(data.getEmail()));
        inputModel.setUsername(String.valueOf(data.getUsername()));
        
        return inputModel;
    }
    
    public List<UserInputModel> getUser(){
        List<User> userQueryList = userRepo.getUser();
        List<UserInputModel> userList = new ArrayList<>();
        
        for (User data : userQueryList){
            UserInputModel inputModel = inputModel(data);

            userList.add(inputModel);
        }
        return userList;
    }

    public List<UserInputModel> getUserById(String id){
        User userQueryList = userRepo.getUserById(id);
        List<UserInputModel> userList = new ArrayList<>();

        UserInputModel inputModel = inputModel(userQueryList);
        userList.add(inputModel);
        
        return userList;
    }
    
    public ApiModel addUser(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel) throws DataAccessException{
            try {
                User newUser = new User();
                for (DataRequestModel fe : requestModel.getData()){    

                    if (fe.getFieldName().equalsIgnoreCase("email")){
                        newUser.setEmail(fe.getValue());
                    }

                    if (fe.getFieldName().equalsIgnoreCase("password")){
                        newUser.setPassword(fe.getValue());
                    }
                    
                    if (fe.getFieldName().equalsIgnoreCase("username")){
                        newUser.setUsername(fe.getValue());
                    }
                }

                // save to db
                userRepo.save(newUser);

                // get ID
                UserInputModel inputModel = new UserInputModel();

                inputModel.setId(newUser.getUserId());
                inputModel.setEmail(newUser.getEmail());
                inputModel.setUsername(newUser.getUsername());

                apiModel.setMessage("Data saved successfully.");
                apiModel.setData(inputModel);
            }
            
            catch (DataAccessException ex) {
            // Log the exception for debugging
                apiModel.setMessage("An error occurred while performing the database operation.");
            }

        apiModel.setStatus(String.valueOf(response.getStatus()));

        return apiModel;
    }

    // CHANGE PASSWORD - START 
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = ((User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());

        // Check if the current password is correct 
        if (!noOpPasswordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong Password");
        }

        // Check if new password is the same as confirmation password
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }
        
        // Update the password
        user.setPassword(noOpPasswordEncoder.encode(request.getNewPassword()));
        
        // Saving the new password
        userRepo.save(user);
    }
    // CHANGE PASSWORD - END 

    // FORGET PASSWORD - START
    private PasswordResetTokenService passwordResetTokenService;

    public Optional<User> getUserByEmail(String email) {
        User user = userRepo.getUserByEmail(email);
        Optional<User> optionalUser = Optional.ofNullable(user);
        
        return optionalUser;
    }

    public void createPasswordResetTokenForUser(User user, String passwordToken) {
        passwordResetTokenService.createPasswordResetTokenForUser(user, passwordToken);
    }

    @Override
    public String validatePasswordResetToken(String passwordResetToken) {
        
        return passwordResetTokenService.validatePasswordResetToken(passwordResetToken);
    }

    @Override
    public User findUserByPasswordToken(String passwordResetToken) {
    
        return passwordResetTokenService.findUserByPasswordToken(passwordResetToken).get();
    }

    // Save user new password to db
    @Override
    public void resetUserPassword(User user, String newPassword) {
        user.setPassword(noOpPasswordEncoder.encode(newPassword));

        userRepo.save(user);
        
    }
    // FORGET PASSWORD - END

}