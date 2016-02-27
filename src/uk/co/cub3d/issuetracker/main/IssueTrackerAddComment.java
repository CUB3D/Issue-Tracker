package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import java.awt.Dimension;

/**
 * Created by Callum on 31/01/2016.
 */
public class IssueTrackerAddComment
{
    private JPanel content;
    private JTextArea textArea1;
    private JButton addCommentButton;

    public IssueInfo info;

    public JFrame frame;

    public IssueTrackerViewIssue issueViewer;

    public IssueTrackerAddComment(String hash, IssueTrackerViewIssue issueViewer)
    {
        frame = new JFrame("Issue Commenter");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setMinimumSize(new Dimension(900, 400));

        frame.setVisible(true);

        frame.pack();

        info = IssueTrackerMain.instance.issues.get(hash);

        addCommentButton.addActionListener((a) -> onAddComment());

        this.issueViewer = issueViewer;
    }

    private void onAddComment()
    {
        info.comments.add(textArea1.getText());
        IssueTrackerMain.instance.issues.put(info.hash.toString(), info);
        issueViewer.updateComments(textArea1.getText());
        frame.dispose();
    }
}
