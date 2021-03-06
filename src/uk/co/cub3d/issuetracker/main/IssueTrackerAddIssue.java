package uk.co.cub3d.issuetracker.main;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private JComboBox comboBoxPriority;

    public JFrame frame;

    public boolean isEditing = false;
    public String hash;

    public IssueTrackerAddIssue(boolean editing, String hash)
    {
        frame = new JFrame(editing ? "Issue Editor" : "Issue Logger");

        frame.setContentPane(content);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setMinimumSize(new Dimension(900, 400));

        frame.setVisible(true);

        frame.pack();

        cancelButton.addActionListener((a) -> frame.dispose());
        addButton.addActionListener((a) -> onAdd());

        if(editing)
        {
            addButton.setText("Edit");

            IssueInfo info = IssueTrackerMain.instance.issues.get(hash);

            textFieldTitle.setText(info.title);
            descriptionTextArea.setText(info.description);
            comboBoxPriority.setSelectedItem(info.priority);

            this.isEditing = true;
            this.hash = hash;
        }
    }

    private void onAdd()
    {
        if(isEditing)
        {
            IssueInfo info = IssueTrackerMain.instance.issues.get(hash);

            info.title = textFieldTitle.getText();
            info.description = descriptionTextArea.getText();
            info.priority = comboBoxPriority.getSelectedItem().toString();

            IssueTrackerMain.instance.issues.put(info.hash.toString(), info);

            IssueTrackerMain.instance.updateIssues();
        }
        else
        {
            IssueTrackerMain.instance.addIssue(new IssueInfo(textFieldTitle.getText(), descriptionTextArea.getText(), IssueTrackerMain.instance.currentUser.username, comboBoxPriority.getSelectedItem().toString()));
        }

        frame.dispose();
    }
}
