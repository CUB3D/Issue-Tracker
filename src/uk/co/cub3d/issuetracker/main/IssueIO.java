package uk.co.cub3d.issuetracker.main;

import sun.nio.fs.BasicFileAttributesHolder;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Callum on 13/11/2015.
 */
public class IssueIO
{
    public static void writeIssues(Map<String, IssueInfo> issues)
    {
        Path outputFile = Paths.get(IssueProperties.issue_file_location);

        try
        {
            Files.deleteIfExists(outputFile);
            Files.createFile(outputFile);
        } catch(IOException e)
        {
            e.printStackTrace();
        }

        try
        {
            BufferedWriter output = Files.newBufferedWriter(outputFile);

            output.write(IssueProperties.VERSION_ID + System.lineSeparator());

            output.write("Hash,Title,Description,Priority" + System.lineSeparator());

            for(IssueInfo info : issues.values())
            {
                String commentData = "";

                for(IssueInfo.Comment data : info.comments)
                {
                    commentData += data.username + ",\"" + data.content.replace("\n", System.lineSeparator()) + "\",";
                }

                if(commentData.length() > 0)
                {
                    commentData = commentData.substring(0, commentData.length() - 1);
                }

                output.write(info.hash + "," + info.title + "," + info.author + "," + (info.done ? "1" : "0") + "," + info.priority + "," + (info.description.replace("\n", System.lineSeparator()).length() - 4) + ",\"" + info.description.replace("\n", System.lineSeparator()) + "\"," +  + info.comments.size() + "," + commentData.length() + System.lineSeparator());
            }

            output.flush();
            output.close();

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void attemptLoadPreviousIssues()
    {
        Path outputFile = Paths.get(IssueProperties.issue_file_location);

        if(Files.exists(outputFile))
        {
            int i = 0;

            switch(IssueProperties.load_existing)
            {
                case "prompt":
                    i = JOptionPane.showConfirmDialog(null, "Previous issue save found, use?");
                    break;
                case "true":
                    i = JOptionPane.OK_OPTION;
                    break;
                case "false":
                    i = JOptionPane.CANCEL_OPTION;
                    break;
                default:
                    i = JOptionPane.showConfirmDialog(null, "Previous issue save found, use?");
                    break;
            }

            if(i == JOptionPane.OK_OPTION)
            {
                try
                {
                    BufferedReader reader = Files.newBufferedReader(outputFile);

                    String s = "";

                    String version_ID = reader.readLine();

                    reader.readLine(); // skip the line with the column headers for excel

                    while((s = reader.readLine()) != null)
                    {
                        String[] records = s.split(",");

                        UUID hash = UUID.fromString(records[0]);

                        String title = records[1];
                        String author = records[2];
                        boolean done = Integer.parseInt(records[3]) == 1;
                        String priority = records[4];

                        String description = "";

                        if(version_ID.equals("ISU_1_1"))
                        {
                            description = records[5];

                            if (!s.endsWith("\"")) {
                                description += "\n";

                                while ((s = reader.readLine()) != null && !s.endsWith("\"")) {
                                    description += s;
                                    description += "\n"; // re-add newlines
                                }

                                if (s != null) {
                                    description += s;
                                }
                            }
                        }
                        else
                        {
                            int descriptionLength = Integer.parseInt(records[5]);

                            description = records[6];

                            if(!description.endsWith("\""))
                            {
                                descriptionLength -= description.length();

                                while (descriptionLength > 0)
                                {
                                    s = reader.readLine();
                                    description += "\n";

                                    if (descriptionLength - s.length() < 0)
                                    {
                                        description += s.substring(0, descriptionLength);
                                        descriptionLength = 0;
                                    } else
                                    {
                                        description += s;
                                        descriptionLength -= s.length();
                                    }
                                }

                                String[] recordsNew = s.split(",");

                                records = new String[6 + recordsNew.length];

                                for (int ii = 0; ii < records.length; ii++)
                                {
                                    records[ii] = "";
                                }

                                for (int ii = 6; ii < records.length; ii++)
                                {
                                    records[ii] = recordsNew[ii - 6];
                                }
                            }
                        }

                        if (version_ID.equals("ISU_1_3"))
                        {
                            int commentCount = Integer.parseInt(records[7]);

                            System.out.println(commentCount);

                            for(int ii = 0; i < 0; i++)
                            {

                            }
                        }

                        // remove the quotes that allow excel to put the whole description in a multiline cell
                        description = description.replaceAll("\"", "");

                        IssueInfo info = new IssueInfo(title, description, author, priority);

                        info.hash = hash;
                        info.done = done;

                        IssueTrackerMain.instance.issues.put(hash.toString(), info);
                        IssueTrackerMain.instance.addIssue_lam(info);
                    }

                    reader.close();
                } catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
