package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Callum on 11/11/2015.
 */
public class IssueTrackerLogin
{
    private JPanel content;
    private JButton loginButton;
    private JTextField username;
    private JPasswordField password;

    public JFrame frame;

    public IssueTrackerLogin()
    {
        frame = new JFrame();

        frame.setContentPane(content);

        if(IssueProperties.force_login)
        {
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
        else
        {
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }

        frame.setVisible(true);
        frame.pack();

        loginButton.addActionListener((a) -> onLogin());
    }

    private void onLogin()
    {
        String passwordHash = "";

        Path p = Paths.get("accounts", username.getText());

        String hash = "";
        String salt = "";

        try
        {
            BufferedReader r = Files.newBufferedReader(p);
            hash = r.readLine();
            salt = r.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            char[] totalPassword = new char[password.getPassword().length + salt.toCharArray().length];

            for(int i = 0; i < password.getPassword().length; i++)
            {
                totalPassword[i] = password.getPassword()[i];
            }

            for(int i = 0; i < salt.toCharArray().length; i++)
            {
                totalPassword[i + password.getPassword().length] = salt.toCharArray()[i];
            }

            CharBuffer chars = CharBuffer.wrap(totalPassword);

            ByteBuffer bytebuffer = Charset.forName("UTF-8").encode(chars);

            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            messageDigest.update(bytebuffer);

            passwordHash = new BigInteger(1, messageDigest.digest()).toString(16);


            // cleanup for extra security

            for(int i = 0; i < totalPassword.length; i++)
            {
                totalPassword[i] = ' ';
            }

            password.setText("");
            username.setText("");

            chars.clear();

            chars = null;

            bytebuffer.clear();

            bytebuffer = null;

            messageDigest.reset();
            messageDigest = null;

            // no point clearing the password hash string as it is immutable
            // same goes for hash and salt

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(passwordHash.equals(hash))
        {
            IssueTrackerMain.instance.login(new LoginInfo(username.getText()));

            frame.dispose();
        }
        else
        {
            if(!IssueProperties.force_login)
            {
                frame.dispose();
            }
        }
    }
}
