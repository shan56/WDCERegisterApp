package com.example.demo.controllers;

import com.example.demo.interfaces.*;
import com.example.demo.models.CRN;
import com.example.demo.models.Course;
import com.example.demo.models.Topic;
import com.example.demo.utilities.ExcelFileType;
import com.example.demo.utilities.ExcelReadWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Controller
public class MainController {
    private final String FILE_PATH = "C:\\Users\\Sue\\Documents\\";

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CRNRepository crnRepository;

    @RequestMapping("/")
    public String homepg(Model model) {
        model.addAttribute("topics", topicRepository.findAll());
        return "homepg";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login?logout=true";
    }

    @RequestMapping("/loadfiles")
    public String admin() {
        return "loadfiles";
    }


    @PostMapping("/processUpload")
    public String uploadExcelFiles(@RequestParam("topicfilename") MultipartFile topicfilename,
                                   @RequestParam("coursefilename") MultipartFile coursefilename,
                                   @RequestParam("crnfilename") MultipartFile crnfilename) {
        ArrayList<Topic> alltopics = new ArrayList<>();
        ArrayList<Course> allcourses = new ArrayList<>();
        ArrayList<CRN> allcrns = new ArrayList<>();
        ExcelReadWrite excelReadWrite = new ExcelReadWrite();

        if (!topicfilename.isEmpty()) {
            // read excel file for topics
            String fullTopicFileName = FILE_PATH + topicfilename.getOriginalFilename();
            excelReadWrite.read(fullTopicFileName, ExcelFileType.TOPIC, alltopics, null, null);
        }

        if (!coursefilename.isEmpty()) {
            // read excel file for topics
            String fullCourseFileName = FILE_PATH + coursefilename.getOriginalFilename();
            excelReadWrite.read(fullCourseFileName, ExcelFileType.COURSE, null, allcourses, null);
        }

        if (!crnfilename.isEmpty()) {
            // read excel file for topics
            String fullCrnFileName = FILE_PATH + crnfilename.getOriginalFilename();
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
        for (CRN one : allcrns){
            if (!one.isEmpty()) {
                mainCourse = one.getMainCourseNo();
                tmpC = courseRepository.findCourseByCourseId(mainCourse);
                one.setCourse(tmpC);
                crnRepository.save(one);
            }
        }

        return "redirect:/";
    }

    @RequestMapping("/showCourses/{id}")
    public String showTopics(@PathVariable("id") long id, Model model){

        Topic current = topicRepository.findById(id).get();
        model.addAttribute("courses", current.getCourses());
        return "showCourses";
    }

    /*
    id = crn id
     */
    @RequestMapping("/addCart/{id}")
    public String addCart(@PathVariable("id") long id, Model model){

            System.out.println("addCart with id=" + id);

        //return "addCart";
        return "redirect:/";
    }

}
