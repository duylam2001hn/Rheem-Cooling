package io.spring.boot.Entity;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class Login {

    @NotEmpty(message = "email is not null")
    @Email(message = "Please input true email format ")
    private String email;

    @NotEmpty(message = "password is not null")
    private String password;

    public Login() {
    }

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {

        PasswordHash passwordHash = new PasswordHash();
        return passwordHash.PasswordHash(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer checkSession(HttpSession httpSession, Model mav, String form){


        if(form.equals("admin")){
            if(httpSession.getAttribute("UserLogin") != null){

                mav.addAttribute("userLog",httpSession.getAttribute("UserLogin"));
                mav.addAttribute("userRole",httpSession.getAttribute("UserRole"));
                return 1;
            }
        }else{
            if(httpSession.getAttribute("CustomerLogin") != null){

                mav.addAttribute("customerLog",httpSession.getAttribute("CustomerLogin"));
                return 1;
            }
        }


        return 0;
    }


    public Integer checkSessionAPI(HttpSession httpSession, String form){


        if(form.equals("admin")){
            if(httpSession.getAttribute("UserLogin") != null){

                return 1;
            }
        }else{
            if(httpSession.getAttribute("CustomerLogin") != null){

                return 1;
            }
        }


        return 0;
    }
}
