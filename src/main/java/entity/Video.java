package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Video")
public class Video {
    @Id
    @Column(length = 50)
    private String id;

    @Column(columnDefinition = "nvarchar(255)", nullable = false)
    private String title;

    @Column(length = 200)
    private String poster;

    @Column(nullable = false)
    private int views = 0;

    @Column(columnDefinition = "nvarchar(max)")
    private String description;

    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 500)
    private String link;

    public Video() {}

    public Video(String id, String title, String poster, int views, String description, boolean active, String link) {
        this.id = id;
        this.title = title;
        this.poster = poster;
        this.views = views;
        this.description = description;
        this.active = active;
        this.link = link;
    }

    // Getter và Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }

    public int getViews() { return views; }
    public void setViews(int views) { this.views = views; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean getActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
}
