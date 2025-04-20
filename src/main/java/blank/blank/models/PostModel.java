package blank.blank.models;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class PostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;

    @Column(length = 5000)
    private String image;

    private LocalDate date;

    @Column(length = 5000)
    private String contents; 
    public PostModel() {
    }

    // Constructor with contents
    public PostModel(Long id, String title, String category, String image, LocalDate date, String contents) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.image = image;
        this.date = date;
        this.contents = contents;
    }

    // ✅ Getters
    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getContents() {
        return contents;
    }

    // ✅ Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", date=" + date +
                ", contents='" + contents + '\'' +  // Include contents in toString
                '}';
    }
}
