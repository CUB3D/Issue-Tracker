package uk.co.cub3d.issuetracker.main;

import sun.nio.fs.BasicFileAttributesHolder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Created by Callum on 13/11/2015.
 */
public class IssueIO
{
    public static void writeIssues(Map<String, IssueInfo> issues)
    {
        Path outputFile = Paths.get("issues.csv");

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

            output.write("Hash,Title,Description" + System.lineSeparator());

            for(IssueInfo info : issues.values())
            {
                output.write(info.hash + "," + info.title + ",\"" + info.description.replace("\n", System.lineSeparator()) + "\"" + System.lineSeparator());
            }

            output.flush();
            output.close();

        } catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
