package com.example.demo.interfaces;

import com.example.demo.models.Course;
import com.example.demo.models.Topic;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
    Iterable<Course> findAllByTopic(Topic topic);
    Course findCourseByCourseId(String courseid);
}
