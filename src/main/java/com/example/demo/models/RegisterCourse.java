package com.example.demo.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="RegisterCourse")
public class RegisterCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long crnNo;
    private String courseNo;
    private String title;
    private LocalDate startDate;
    private long base;
    private long fee;
    private long nmr;
    private long total;
    private long orgstudent;                  // temp student id during registration
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="student_id")
    private Student student;


    public RegisterCourse() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCrnNo() {
        return crnNo;
    }

    public void setCrnNo(long crnNo) {
        this.crnNo = crnNo;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public long getOrgstudent() {
        return orgstudent;
    }

    public void setOrgstudent(long orgstudent) {
        this.orgstudent = orgstudent;
    }

}
