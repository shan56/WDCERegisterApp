package com.example.demo.models;

import org.apache.poi.ss.usermodel.DateUtil;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;


@Entity
@Table(name="CRNRecord")
public class CRNRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String mainCourseNo;
    private String title;
    private String courseNo;
    private long crn;
    private String location;
    private Date startDate;
    private Date endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private long sessions;
    private String weekdays;
    private long base;
    private long fee;
    private long nmr;
    private long total;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="course_id")
    private Course course;

    public CRNRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMainCourseNo() {
        return mainCourseNo;
    }

    public void setMainCourseNo(String mainCourseNo) {
        this.mainCourseNo = mainCourseNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public long getCrn() {
        return crn;
    }

    public void setCrn(long crn) {
        this.crn = crn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStartDate(double startDate) {
        this.startDate = DateUtil.getJavaDate(startDate);

    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(double endDate) {
        this.endDate = DateUtil.getJavaDate(endDate);

    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        Duration tmpTime = Duration.ofSeconds(startTime);
        int hr = (int)tmpTime.toHours();
        int min = (int)tmpTime.toMinutes();

        this.startTime = LocalTime.of(hr, min);
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }


    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        Duration tmpTime = Duration.ofSeconds(endTime);
        this.endTime = LocalTime.of((int)tmpTime.toHours(), (int)tmpTime.toMinutes());
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public long getSessions() {
        return sessions;
    }

    public void setSessions(long sessions) {
        this.sessions = sessions;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public long getBase() {
        return base;
    }

    public void setBase(long base) {
        this.base = base;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getNmr() {
        return nmr;
    }

    public void setNmr(long nmr) {
        this.nmr = nmr;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isEmpty(){
        if ((this.courseNo == null) && (this.crn == 0))
            return true;
        else
            return false;
    }
}
