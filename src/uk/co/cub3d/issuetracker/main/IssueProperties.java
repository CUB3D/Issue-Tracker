package uk.co.cub3d.issuetracker.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by cub3d on 22/11/15.
 */
public class IssueProperties
{
    public static boolean login_on_start = false;
    public static String load_existing = "prompt";

    public static void loadProperties()
    {
        Properties properties = new Properties();

        Path propertiesFile = Paths.get("Config.conf");

        try
        {
            properties.load(Files.newInputStream(propertiesFile));

            login_on_start = properties.getProperty("login_on_start", "false").equals("true");
            load_existing = properties.getProperty("load_existing");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
