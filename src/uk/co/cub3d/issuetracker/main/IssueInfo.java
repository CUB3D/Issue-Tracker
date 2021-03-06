package uk.co.cub3d.issuetracker.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Callum on 12/11/2015.
 */
public class IssueInfo
{
    public String title;
    public String description;
    public UUID hash;
    public boolean done;
    public String author;
    public String priority;
    public List<Comment> comments;

    public IssueInfo(String title, String description, String author, String priority)
    {
        this.title = title;
        this.description = description;
        this.author = author;
        this.priority = priority;
        this.comments = new ArrayList<>();
    }

    public class Comment
    {
        public String username;
        public String content;
    }
}
