package uk.co.cub3d.issuetracker.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Callum on 02/03/2016.
 */
public class CSVParser
{
    public static void main(String[] args) throws IOException
    {
        parseCSV(Files.readAllLines(Paths.get("Issues.csv")));
    }

    public static void parseCSV(List<String> fileData)
    {
        for(int i = 0; i < fileData.size(); i++)
        {
            List<String> recordsForThisLine = new ArrayList<>();

            String data = fileData.get(i);
            String[] records = data.split(",");

            for (int x = 0; x < records.length - 1; x++)
            {
                recordsForThisLine.add(records[x]);
            }

            if(records.length > 1)
            {
                while(true)
                {
                    String lastRecord = records[records.length - 1];

                    if (lastRecord.startsWith("\""))
                    {
                        // multiline record
                        while (true)
                        {
                            i++;
                            data = fileData.get(i);
                            records = data.split(",");

                            lastRecord += records[0];

                            // if the first record ends with a quote then we have reached the end of a
                            // multiline record
                            if (records[0].endsWith("\""))
                            {
                                break;
                            }
                        }
                    }
                    recordsForThisLine.add(lastRecord);
                    lastRecord = "";

                    if(!lastRecord.startsWith("\"")) // if the last record does not starts with a quote then the end of the line has been reached
                    {
                        break;
                    }
                }
            }

            for(String s : recordsForThisLine)
            {
                System.out.println(s);
            }

            System.out.println("----------------");
        }
    }
}
