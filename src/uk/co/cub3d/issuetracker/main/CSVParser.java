package uk.co.cub3d.issuetracker.main;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Callum on 02/03/2016.
 */
public class CSVParser
{
    //For testing the loading and saving of the issue files
    public static void main(String[] args) throws IOException
    {
        //set the instance so that the issue hashmap is not null
        IssueTrackerMain.instance = new IssueTrackerMain();

        parseCSV("B:\\Google Drive\\Programs\\Issue Tracker\\Issues.csv");
        writeCSV("B:\\Google Drive\\Programs\\Issue Tracker\\Issues-new.csv");
    }

    public static void parseCSV(String fileName)
    {
        try
        {
            CSVReader reader = new CSVReader(new FileReader(fileName));
            List<String[]> entries = reader.readAll();

            String ISUVersion = entries.get(0)[0];

            // start at 2 to skip the column headers and the version
            for(int i = 2; i < entries.size(); i++)
            {
                String[] issueData = entries.get(i);

                String hash = "";
                String title = "";
                String author = "";
                boolean done = false;
                String priority = "";
                String description = "";

                if(ISUVersion.equals("ISU_1_3"))
                {
                    hash = issueData[0];
                    title = issueData[1];
                    author = issueData[2];
                    done = Integer.parseInt(issueData[3]) == 1;
                    priority = issueData[4];
                    description = issueData[6];
                }

                if(ISUVersion.equals("ISU_1_4"))
                {
                    hash = issueData[0];
                    title = issueData[1];
                    author = issueData[2];
                    done = Integer.parseInt(issueData[3]) == 1;
                    priority = issueData[4];
                    description = issueData[5];
                }

                IssueInfo info = new IssueInfo(title, description, author, priority);
                info.hash = UUID.fromString(hash);
                info.done = done;

                if(ISUVersion.equals("ISU_1_4"))
                {
                    int commentCount = Integer.parseInt(issueData[6]);

                    int commentIndex = 0;

                    for (int j = 0; j < commentCount; j++)
                    {
                        IssueInfo.Comment comment = info.new Comment();

                        comment.username = issueData[7 + commentIndex++];
                        comment.content = issueData[7 + commentIndex++];

                        System.out.println("Name: " + comment.username);
                        System.out.println("Content: " + comment.content);

                        info.comments.add(comment);
                    }
                }

                System.out.println(hash);
                System.out.println(title);
                System.out.println(author);
                System.out.println(priority);
                System.out.println(description);

                IssueTrackerMain.instance.issues.put(hash.toString(), info);
                IssueTrackerMain.instance.addIssue_lam(info);

                System.out.println("-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeCSV(String fileName)
    {
        try
        {
            CSVWriter writer = new CSVWriter(new FileWriter(fileName));

            writer.writeNext(new String[] {"ISU_1_4"});
            writer.writeNext(new String[] {"Hash", "Title", "Description", "Priority"});

            for(IssueInfo info : IssueTrackerMain.instance.issues.values())
            {
                String[] data = new String[7 + 2 * info.comments.size()];

                data[0] = info.hash.toString();
                data[1] = info.title;
                data[2] = info.author;
                data[3] = info.done ? "1" : "0";
                data[4] = info.priority;
                data[5] = info.description;

                data[6] = "" + info.comments.size();

                int location = 0;

                for(IssueInfo.Comment c : info.comments)
                {
                    data[7 + location++] = c.username;
                    data[7 + location++] = c.content;
                }

                writer.writeNext(data);
            }

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
