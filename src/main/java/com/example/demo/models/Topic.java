package com.example.demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="topic")
public class Topic {
    @Id
    private long id;

    private String title;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;

    @OneToMany(mappedBy="topic", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Course> courses;

    public Topic() {
        this.courses = new HashSet<Course>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public boolean isEmpty(){
        if ((this.description == null) && (this.title == null))
            return true;
        else
            return false;
    }
}
