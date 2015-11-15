package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
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
    private JButton editButton;

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
        editButton.addActionListener((a) -> onEdit());

        onLogin();
    }

    private void onEdit()
    {
        String hash = table1.getValueAt(table1.getSelectedRow(), 0).toString();

        new IssueTrackerAddIssue(true, hash);
    }

    private void onView()
    {
        String hash = table1.getValueAt(table1.getSelectedRow(), 0).toString();

        new IssueTrackerViewIssue(hash);
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
        new IssueTrackerAddIssue(false, "");
    }

    public void addIssue(IssueInfo info)
    {
        info.hash = UUID.randomUUID();

        issues.put(info.hash.toString(), info);

        addIssue_lam(info);
    }

    public void updateIssues()
    {
        ((DefaultTableModel)table1.getModel()).setRowCount(0);
        table1.revalidate();
        ((DefaultTableModel)table1.getModel()).setRowCount(20);
        currentLine = 0;

        issues.values().forEach(this::addIssue_lam);
    }

    public void addIssue_lam(IssueInfo info)
    {
        table1.setValueAt(info.hash, currentLine, 0);
        table1.setValueAt(currentUser.username, currentLine, 1);
        table1.setValueAt(info.title, currentLine, 2);

        currentLine++;

        IssueIO.writeIssues(issues);
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

        IssueIO.attemptLoadPreviousIssues();
    }

    private void createUIComponents()
    {
        String data[][] = new String[20][4];
        String[] names = new String[] {"ID", "Author", "Title", "View"};

        DefaultTableModel model = new DefaultTableModel(data, names);

        table1 = new JTable(model);
    }

}
