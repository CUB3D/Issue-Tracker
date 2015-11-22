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
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
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
    private JButton doneButton;
    private JCheckBox doneCheckBox;

    public int currentLine = 0;

    public LoginInfo currentUser = new LoginInfo("NO USER");

    public Map<String, IssueInfo> issues = new HashMap<>();

    public TableRowSorter<DefaultTableModel> sorter;

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
        frame.setMinimumSize(new Dimension(500, 500));
        frame.pack();

        addIssueButton.addActionListener((a) -> onAddIssue());
        viewButton.addActionListener((a) -> onView());
        editButton.addActionListener((a) -> onEdit());
        doneButton.addActionListener((a) -> onDone());

        doneCheckBox.addActionListener((a) -> onFilter());

        if(!IssueProperties.edit_without_login)
        {
            tabbedPane1.setEnabled(false);
            table1.setEnabled(false);
            addIssueButton.setEnabled(false);
            userLabel.setEnabled(false);
            viewButton.setEnabled(false);
            editButton.setEnabled(false);
            doneButton.setEnabled(false);
            doneCheckBox.setEnabled(false);
        }

        if(IssueProperties.login_on_start)
            onLogin();
    }

    private void onFilter()
    {
        if(doneCheckBox.isSelected())
        {
            applyTableFilter("false", 3);
        }
        else
        {
            applyTableFilter("false|true", 3);
        }
    }

    private void onDone()
    {
        String hash = table1.getValueAt(table1.getSelectedRow(), 0).toString();

        IssueInfo info = issues.get(hash);

        info.done = true;

        issues.put(hash, info);

        updateIssues();

        IssueIO.writeIssues(issues);
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

        if(!IssueProperties.edit_without_login)
        {
            tabbedPane1.setEnabled(true);
            table1.setEnabled(true);
            addIssueButton.setEnabled(true);
            userLabel.setEnabled(true);
            viewButton.setEnabled(true);
            editButton.setEnabled(true);
            doneButton.setEnabled(true);
            doneCheckBox.setEnabled(true);
        }
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

        IssueIO.writeIssues(issues);
    }

    public void updateIssues()
    {
        ((DefaultTableModel)table1.getModel()).setRowCount(0);
        table1.revalidate();
        currentLine = 0;

        issues.values().forEach(this::addIssue_lam);

        IssueIO.writeIssues(issues);
    }

    public void addIssue_lam(IssueInfo info)
    {
        ((DefaultTableModel) table1.getModel()).addRow(new Object[]{info.hash, info.author, info.title, info.done});

        currentLine++;
    }

    private void applyTableFilter(String status, int column)
    {
        RowFilter<DefaultTableModel, Object> rf = null;

        rf = RowFilter.regexFilter(status, column);

        sorter.setRowFilter(rf);
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

        if(args != null && args.length >= 1 && args[0].equals("-admin"))
        {
            new IssueTrackerAdminMain();
        }
        else
        {
            IssueProperties.loadProperties();

            instance = new IssueTrackerMain();

            IssueIO.attemptLoadPreviousIssues();
        }
    }

    private void createUIComponents()
    {
        String data[][] = new String[0][4];
        String[] names = new String[] {"ID", "Author", "Title", "Done"};

        DefaultTableModel model = new DefaultTableModel(data, names);

        sorter = new TableRowSorter<>(model);

        table1 = new JTable(model);

        table1.setRowSorter(sorter);
    }

}
