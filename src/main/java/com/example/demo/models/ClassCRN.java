package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

public class ClassCRN {
    private long id;
    private String crnno;
    private String courseno;
    private String title;
    private LocalDate start;
    private long tuition;
    private long fee;
    private long nonMdfee;
    private long courseTotal;

    public ClassCRN() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCrnno() {
        return crnno;
    }

    public void setCrnno(String crnno) {
        this.crnno = crnno;
    }

    public String getCourseno() {
        return courseno;
    }

    public void setCourseno(String courseno) {
        this.courseno = courseno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public long getTuition() {
        return tuition;
    }

    public void setTuition(long tuition) {
        this.tuition = tuition;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getNonMdfee() {
        return nonMdfee;
    }

    public void setNonMdfee(long nonMdfee) {
        this.nonMdfee = nonMdfee;
    }

    public long getCourseTotal() {
        return courseTotal;
    }

    public void setCourseTotal(long courseTotal) {
        this.courseTotal = courseTotal;
    }
}
