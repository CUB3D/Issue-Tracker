package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.Dimension;

/**
 * Created by Callum on 12/11/2015.
 */
public class IssueTrackerViewIssue
{
    private JPanel content;
    private JTextArea descriptionTextArea;
    private JLabel titleLabel;
    private JButton doneButton;
    private JLabel priorityLabel;

    public JFrame frame;

    public IssueTrackerViewIssue(String hash)
    {
        frame = new JFrame("Issue Viewer");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setMinimumSize(new Dimension(900, 400));

        frame.setVisible(true);

        frame.pack();

        IssueInfo info = IssueTrackerMain.instance.issues.get(hash);

        descriptionTextArea.setText(info.description);
        titleLabel.setText("Title: " + info.title);
        priorityLabel.setText("Priority: " + info.priority);


        doneButton.addActionListener((a) -> frame.dispose());
    }
}
