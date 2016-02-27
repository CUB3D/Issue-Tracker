package uk.co.cub3d.issuetracker.main;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
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
    private JButton addCommentButton;
    private JTextPane textPane1;
    private JTree tree1;

    public IssueInfo info;

    public JFrame frame;

    public DefaultMutableTreeNode comments;

    public IssueTrackerViewIssue(String hash)
    {
        frame = new JFrame("Issue Viewer");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setMinimumSize(new Dimension(900, 400));

        frame.setVisible(true);

        frame.pack();

        info = IssueTrackerMain.instance.issues.get(hash);

        descriptionTextArea.setText(info.description);
        titleLabel.setText("Title: " + info.title);
        priorityLabel.setText("Priority: " + info.priority);

        for(String s : info.comments)
        {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(s);

            comments.add(node);
        }


        doneButton.addActionListener((a) -> frame.dispose());
        addCommentButton.addActionListener(a -> onAddComment());
    }

    public void onAddComment()
    {
        new IssueTrackerAddComment(info.hash.toString(), this);
    }

    public void updateComments(String comment)
    {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(comment);

        comments.add(node);
    }


    private void createUIComponents()
    {
        comments = new DefaultMutableTreeNode();

        tree1 = new JTree(comments);
    }
}
