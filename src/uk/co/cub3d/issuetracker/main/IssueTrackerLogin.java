package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
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
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        loginButton.addActionListener((a) -> onLogin());
    }

    private void onLogin()
    {
        String passwordHash = "";

        try {
            CharBuffer chars = CharBuffer.wrap(password.getPassword());

            ByteBuffer bytebuffer = Charset.forName("UTF-8").encode(chars);

            byte[] a = MessageDigest.getInstance("SHA-256").digest(bytebuffer.array());

            //TODO: salt password

            passwordHash = new BigInteger(1, a).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8 == password

        if(passwordHash.equals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"))
        {
            System.out.println("LOGIN [OK]");
        }

        System.out.println(passwordHash);

        IssueTrackerMain.instance.login(new LoginInfo(username.getText(), passwordHash));

        frame.dispose();
    }
}
