package gs.model.response;

public class LoginRes {
    private String email;
    private String token;
    private String userId;

    public LoginRes(String email, String token, String userId){
        this.email = email;
        this.token = token;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
