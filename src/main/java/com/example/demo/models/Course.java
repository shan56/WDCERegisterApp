package com.example.demo.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String courseId;
    private String title;
    private String option;
    private long hour;
    private String materials;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String description;
    private String prerequisites;

    @Column(columnDefinition = "NVARCHAR(MAX)")
    private String objectives;   // use ; as a delimiter

    private long orgTopicId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="topic_id")
    private Topic topic;

    @OneToMany(mappedBy="course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<CRN> crns;

    public Course() {
        this.crns = new HashSet<CRN>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public long getHour() {
        return hour;
    }

    public void setHour(long hour) {
        this.hour = hour;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public long getOrgTopicId() {
        return orgTopicId;
    }

    public void setOrgTopicId(long orgTopicId) {
        this.orgTopicId = orgTopicId;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public boolean isEmpty(){
        if ((this.courseId == null) && (this.title == null))
            return true;
        else
            return false;
    }
}
