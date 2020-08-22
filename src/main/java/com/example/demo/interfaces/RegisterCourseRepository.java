package com.example.demo.interfaces;

import com.example.demo.models.RegisterCourse;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface RegisterCourseRepository extends CrudRepository<RegisterCourse, Long> {
    Collection<RegisterCourse> findAllByOrgstudentEquals(long id);
}
