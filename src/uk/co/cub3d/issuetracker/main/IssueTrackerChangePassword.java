package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.WindowConstants;
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
 * Created by Callum on 24/11/2015.
 */
public class IssueTrackerChangePassword
{
    private JPasswordField password;
    private JButton changePasswordButton;
    private JPanel content;
    private JButton cancelButton;

    private String username;

    private JFrame frame;

    public IssueTrackerChangePassword(String username)
    {
        frame = new JFrame("Change password");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        cancelButton.addActionListener((a) -> frame.dispose());
        changePasswordButton.addActionListener((a) -> onChange());

        this.username = username;
    }

    private void onChange()
    {
        Path userFile = Paths.get(IssueProperties.account_store_location, username);

        try
        {
            Files.delete(userFile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

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

        frame.dispose();
    }
}
