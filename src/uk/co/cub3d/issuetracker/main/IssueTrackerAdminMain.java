package uk.co.cub3d.issuetracker.main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by cub3d on 22/11/15.
 */
public class IssueTrackerAdminMain
{
    private JPanel content;
    private JTabbedPane tabbedPane1;
    private JButton addUserButton;
    private JButton removeSelectedButton;
    private JButton changePasswordButton;
    private JTable table1;

    public IssueTrackerAdminMain()
    {
        JFrame frame = new JFrame("Issue tracker admin");

        frame.setMinimumSize(new Dimension(400, 400));

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.pack();

        removeSelectedButton.addActionListener((a) -> onRemove());
        addUserButton.addActionListener((a) -> onAddUser());
        changePasswordButton.addActionListener((a) -> onChangePassword());

        loadUsers();
    }

    private void onChangePassword()
    {
        String selectedName = table1.getValueAt(table1.getSelectedRow(), 0).toString();

        new IssueTrackerChangePassword(selectedName);
    }

    public void onAddUser()
    {
        new IssueTrackerAdminAddUser();
    }

    private void onRemove()
    {
        String selectedName = table1.getValueAt(table1.getSelectedRow(), 0).toString();

        Path userFile = Paths.get("accounts", selectedName);
        try
        {
            Files.delete(userFile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        loadUsers();
    }

    private void loadUsers()
    {
        ((DefaultTableModel)table1.getModel()).setRowCount(0);
        table1.revalidate();

        File accounts = new File("accounts");

        for(File f : accounts.listFiles())
        {
            ((DefaultTableModel)table1.getModel()).addRow(new Object[] {f.getName()});
        }
    }

    private void createUIComponents()
    {
        String data[][] = new String[0][0];
        String[] names = new String[] {"Name"};

        DefaultTableModel model = new DefaultTableModel(data, names);

        table1 = new JTable(model);
    }
}
