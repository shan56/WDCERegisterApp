package com.example.demo.services;

import com.example.demo.interfaces.*;
import com.example.demo.models.*;
import com.example.demo.utilities.ExcelFileType;
import com.example.demo.utilities.ExcelReadWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CRNRecordRepository crnRecordRepository;

    public void run(String... args) {
        User admin = new User("super", "super@domain.com", "", "Super", "Hero", true);
        Role adminRole = new Role("super", "ROLE_ADMIN");
        userRepository.save(admin);
        roleRepository.save(adminRole);

        uploadExcelFiles("WDCEAppTopics.xlsx", "WDCEAppCourses.xlsx","WDCEAppCRNs.xlsx");
    }

    public void uploadExcelFiles(String topicfilename,
                                   String coursefilename,
                                   String crnfilename) {
        final String FILE_PATH = "C:\\Users\\Sue\\Documents\\";
        ArrayList<Topic> alltopics = new ArrayList<>();
        ArrayList<Course> allcourses = new ArrayList<>();
        ArrayList<CRNRecord> allcrns = new ArrayList<>();
        ExcelReadWrite excelReadWrite = new ExcelReadWrite();

        if (!topicfilename.isEmpty()) {
            // read excel file for topics
            String fullTopicFileName = FILE_PATH + topicfilename;
            excelReadWrite.read(fullTopicFileName, ExcelFileType.TOPIC, alltopics, null, null);
        }

        if (!coursefilename.isEmpty()) {
            // read excel file for topics
            String fullCourseFileName = FILE_PATH + coursefilename;
            excelReadWrite.read(fullCourseFileName, ExcelFileType.COURSE, null, allcourses, null);
        }

        if (!crnfilename.isEmpty()) {
            // read excel file for topics
            String fullCrnFileName = FILE_PATH + crnfilename;
            excelReadWrite.read(fullCrnFileName, ExcelFileType.CRN, null, null, allcrns);
        }

        for (Topic one : alltopics){
            if (!one.isEmpty())
                topicRepository.save(one);
        }

        Topic tmpT;
        long tmpId;
        for (Course one : allcourses){
            if (!one.isEmpty()) {
                tmpId = one.getOrgTopicId();

                tmpT = topicRepository.findById(tmpId).get();
                one.setTopic(tmpT);
                courseRepository.save(one);
            }
        }

        Course tmpC;
        String mainCourse;
        for (CRNRecord one : allcrns){
            if (!one.isEmpty()) {
                mainCourse = one.getMainCourseNo();
                tmpC = courseRepository.findCourseByCourseId(mainCourse);
                one.setCourse(tmpC);
                crnRecordRepository.save(one);
            }
        }
    }

}
