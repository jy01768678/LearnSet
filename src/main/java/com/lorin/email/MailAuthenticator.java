package com.lorin.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;



public class MailAuthenticator extends Authenticator{
    private String userName=null;   
    private String password=null;   

    public MailAuthenticator(String username, String password) {    
        this.userName = username;    
        this.password = password;    
    }
    
    protected PasswordAuthentication getPasswordAuthentication(){   
        return new PasswordAuthentication(userName, password);   
    }   
}
