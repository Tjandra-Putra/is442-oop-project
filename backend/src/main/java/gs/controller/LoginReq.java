package gs.controller;

public class LoginReq {
    private String email;
    private String password;

    public LoginReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

   public void setEmail(String email) {
       this.email = email;
   }

    public void setPassword(String password) {
        this.password = password;
    }
}
