package uk.co.cub3d.issuetracker.main;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Callum on 02/03/2016.
 */
public class CSVParser
{
    public static void main(String[] args) throws IOException
    {
        parseCSV("B:\\Google Drive\\Programs\\Issue Tracker\\Issues.csv");
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

                System.out.println(hash);
                System.out.println(title);
                System.out.println(author);
                System.out.println(priority);
                System.out.println(description);

                IssueInfo info = new IssueInfo(title, description, author, priority);
                info.hash = UUID.fromString(hash);
                info.done = done;

                IssueTrackerMain.instance.issues.put(hash.toString(), info);
                IssueTrackerMain.instance.addIssue_lam(info);

                System.out.println("-----");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
