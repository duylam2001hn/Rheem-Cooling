package io.spring.boot.Entity;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordHash {

//    private String password;
    public PasswordHash() {
    }

//    public String getPassword() {
//
//        PasswordHash();
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public String PasswordHash(String password){

        String salt ="jgkas;@abc%bcd";
        String result = null;
        password = password+salt;
        try {
            byte[] dataBytes = password.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            result = Base64.encodeBase64String(md.digest(dataBytes));

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
