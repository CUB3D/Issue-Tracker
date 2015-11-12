package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Callum on 11/11/2015.
 */
public class IssueTrackerMain
{
    private JTabbedPane tabbedPane1;
    private JPanel content;
    private JTable table1;
    private JButton addIssueButton;
    private JLabel userLabel;
    private JButton viewButton;

    public int currentLine = 0;

    public LoginInfo currentUser = new LoginInfo("NO USER", null);

    public Map<String, IssueInfo> issues = new HashMap<>();

    public static IssueTrackerMain instance;

    public IssueTrackerMain()
    {
        JFrame frame = new JFrame();

        frame.setMinimumSize(new Dimension(400, 400));

        JMenuBar bar = new JMenuBar();

        JMenu user = new JMenu("User");

        JMenuItem login = new JMenuItem("Login");
        login.addActionListener((a) -> onLogin());

        user.add(login);

        bar.add(user);

        frame.setJMenuBar(bar);


        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();

        addIssueButton.addActionListener((a) -> onAddIssue());
        viewButton.addActionListener((a) -> onView());
    }

    private void onView()
    {
        String hash = table1.getValueAt(table1.getSelectedRow(), 0).toString();

        new IssuesTrackerViewIssue(hash);
    }

    public void onLogin()
    {
        new IssueTrackerLogin();
    }

    public void login(LoginInfo loginInfo)
    {
        currentUser = loginInfo;
        userLabel.setText("User: " + loginInfo.username);
    }

    private void onAddIssue()
    {
        new IssueTrackerAddIssue();
    }

    public void addIssue(String title, String description)
    {
        table1.setValueAt(UUID.randomUUID().toString(), currentLine, 0);
        table1.setValueAt(currentUser.username, currentLine, 1);
        table1.setValueAt(title, currentLine, 2);

        issues.put(table1.getValueAt(currentLine, 0).toString(), new IssueInfo(title, description));

        currentLine++;
    }

    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        instance = new IssueTrackerMain();
    }

    private void createUIComponents()
    {
        String data[][] = new String[20][4];
        String[] names = new String[] {"ID", "Author", "Title", "View"};

        DefaultTableModel model = new DefaultTableModel(data, names);

        table1 = new JTable(model);
    }

}
