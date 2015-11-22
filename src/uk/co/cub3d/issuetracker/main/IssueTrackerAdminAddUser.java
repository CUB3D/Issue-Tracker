package uk.co.cub3d.issuetracker.main;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
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
import java.security.SecureRandom;

/**
 * Created by cub3d on 22/11/15.
 */
public class IssueTrackerAdminAddUser
{
    private JTextField username;
    private JPasswordField password;
    private JButton addUserButton;
    private JPanel content;

    public IssueTrackerAdminAddUser()
    {
        JFrame frame = new JFrame("Issue tracker - Add User");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        addUserButton.addActionListener((a) -> addUser());
    }

    private void addUser()
    {
        Path userFile = Paths.get("accounts", username.getText());

        try
        {
            Files.createFile(userFile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedWriter writer = Files.newBufferedWriter(userFile);

            // generate a complex salt

            SecureRandom rand = new SecureRandom();

            int bits = 8200; // round 8192 up to nearest hundred

            String salt = new BigInteger(bits, rand).toString(64);



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

            String passwordHash = new BigInteger(1, messageDigest.digest()).toString(16);

            writer.write(passwordHash+"\n");
            writer.write(salt+"\n");
            writer.flush();
            writer.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
