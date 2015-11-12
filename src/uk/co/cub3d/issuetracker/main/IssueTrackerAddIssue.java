package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.Dimension;

/**
 * Created by Callum on 11/11/2015.
 */
public class IssueTrackerAddIssue
{
    private JTextField textFieldTitle;
    private JTextArea descriptionTextArea;
    private JButton addButton;
    private JButton cancelButton;
    private JPanel content;

    public JFrame frame;

    public IssueTrackerAddIssue()
    {
        frame = new JFrame();

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setMinimumSize(new Dimension(300, 300));

        frame.setVisible(true);

        frame.pack();

        cancelButton.addActionListener((a) -> frame.dispose());
        addButton.addActionListener((a) -> onAdd());
    }

    private void onAdd()
    {
        IssueTrackerMain.instance.addIssue(textFieldTitle.getText(), descriptionTextArea.getText());
        frame.dispose();
    }
}
