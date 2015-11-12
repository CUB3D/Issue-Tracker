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
public class IssuesTrackerViewIssue
{
    private JPanel content;
    private JTextArea descriptionTextArea;
    private JLabel titleLabel;
    private JButton doneButton;

    public JFrame frame;

    public IssuesTrackerViewIssue(String hash)
    {
        frame = new JFrame("Issue Viewer");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setMinimumSize(new Dimension(300, 300));

        frame.setVisible(true);

        frame.pack();

        IssueInfo info = IssueTrackerMain.instance.issues.get(hash);

        descriptionTextArea.setText(info.description);
        titleLabel.setText("Title: " + info.title);


        doneButton.addActionListener((a) -> frame.dispose());
    }
}
