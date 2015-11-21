package uk.co.cub3d.issuetracker.main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Callum on 11/11/2015.
 */
public class LoginInfo
{
    public String username;
    public String password;

    public LoginInfo(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
