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
    public static boolean force_login = false;
    public static boolean edit_without_login = false;
    public static String issue_file_location = "Issues.csv";
    public static String account_store_location = "accounts";

    public static void loadProperties()
    {
        Properties properties = new Properties();

        Path propertiesFile = Paths.get("Config.conf");

        try
        {
            properties.load(Files.newInputStream(propertiesFile));

            login_on_start = properties.getProperty("login_on_start", "false").equals("true");
            load_existing = properties.getProperty("load_existing", "prompt");
            force_login = properties.getProperty("force_login", "false").equals("true");
            edit_without_login = properties.getProperty("edit_without_login", "false").equals("true");
            issue_file_location = properties.getProperty("issue_file_location", "Issues.csv");
            account_store_location = properties.getProperty("account_store_location", "accounts");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
