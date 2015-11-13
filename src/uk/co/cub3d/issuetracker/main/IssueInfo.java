package uk.co.cub3d.issuetracker.main;

import java.util.UUID;

/**
 * Created by Callum on 12/11/2015.
 */
public class IssueInfo
{
    public String title;
    public String description;
    public UUID hash;

    public IssueInfo(String title, String description)
    {
        this.title = title;
        this.description = description;
    }
}
