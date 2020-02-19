package org.acme.jwt;

// POJO
public class User{

    public String username;
    public String secret;

    // Check if needed
    public User(){}

    public User(String username, String secret){
        this.username = username;
        this.secret = secret;
    }

    public String toString(){
        return "username " + this.username + " secret " + secret;
    }
}
