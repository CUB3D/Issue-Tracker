package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

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
        IssueTrackerMain.instance.login(new LoginInfo(username.getText(), password.getPassword()));

        frame.dispose();
    }
}
