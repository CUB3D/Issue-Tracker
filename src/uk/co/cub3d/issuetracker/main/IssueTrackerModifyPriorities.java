package uk.co.cub3d.issuetracker.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by Callum on 29/03/2016.
 */
public class IssueTrackerModifyPriorities
{
    private JList list1;
    private JPanel content;
    private JButton addButton;
    private JButton removeButton;

    public JFrame frame;

    public IssueTrackerModifyPriorities()
    {
        frame = new JFrame("Priorities");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setMinimumSize(new Dimension(900, 400));

        frame.setVisible(true);

        frame.pack();

        list1.setListData(IssueTrackerMain.instance.priorities.toArray());

        addButton.addActionListener(a -> onAddPriority());
        removeButton.addActionListener(a -> onRemovePriority());

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                CSVParser.writeCSVToIssues();
            }
        });
    }

    private void onRemovePriority()
    {
        int currentSelection = list1.getSelectedIndex();

        IssueTrackerMain.instance.priorities.remove(currentSelection);

        list1.setListData(IssueTrackerMain.instance.priorities.toArray());
    }

    private void onAddPriority()
    {
        String newPriority = JOptionPane.showInputDialog(frame, "Enter the new priority identifier", "Add priority", JOptionPane.PLAIN_MESSAGE);

        IssueTrackerMain.instance.priorities.add(newPriority);

        list1.setListData(IssueTrackerMain.instance.priorities.toArray());
    }
}
