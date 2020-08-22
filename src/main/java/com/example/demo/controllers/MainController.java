package com.example.demo.controllers;

import com.example.demo.interfaces.*;
import com.example.demo.models.*;
import com.example.demo.utilities.ExcelFileType;
import com.example.demo.utilities.ExcelReadWrite;
import com.example.demo.utilities.GenPdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

@Controller
public class MainController {
    private final String FILE_PATH = "C:\\Users\\Sue\\Documents\\";

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CRNRecordRepository crnRecordRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    RegisterCourseRepository registerCourseRepository;

    GenPdf genPdf = new GenPdf();

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
        ArrayList<CRNRecord> allcrns = new ArrayList<>();
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
        for (CRNRecord one : allcrns){
            if (!one.isEmpty()) {
                mainCourse = one.getMainCourseNo();
                tmpC = courseRepository.findCourseByCourseId(mainCourse);
                one.setCourse(tmpC);
                crnRecordRepository.save(one);
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


    @RequestMapping("/registrationForm/{id}")
    public String registrationForm(@PathVariable("id") long id, Model model) {

        Student student = new Student();
        CRNRecord tmp1 = crnRecordRepository.findById(id).get();

        RegisterCourse reg1 = new RegisterCourse();
        reg1.setCrnNo(tmp1.getCrn());
        reg1.setCourseNo(tmp1.getCourseNo());
        reg1.setBase(tmp1.getBase());
        reg1.setFee(tmp1.getFee());
        reg1.setNmr(tmp1.getNmr());
        reg1.setTitle(tmp1.getTitle());
        //reg1.setStartDate(tmp1.getStartDate());

        Random random = new Random();
        long tempid = (1 + random.nextInt()) * -1;
        reg1.setOrgstudent(tempid);
        registerCourseRepository.save(reg1);

        System.out.println("\n---------------- in registrationForm: reg id="+ reg1.getId());

        student.setId(tempid);   //temp place holder


        ArrayList<RegisterCourse> allcourses = new ArrayList<>();
        allcourses.add(reg1);
        student.setRegisterCourses(allcourses);
        model.addAttribute("student", student);
        model.addAttribute("allcourses", allcourses);

        return "registrationForm";
    }

@PostMapping("/processForm")
public String processForm(@Valid @ModelAttribute("student") Student student,
                          @ModelAttribute("allcourses") ArrayList<RegisterCourse> allcourses,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registrationForm";
        } else {
            long tempid = student.getId();
            student.setId((long)0);
            Collection<RegisterCourse> allregcourses = registerCourseRepository.findAllByOrgstudentEquals(tempid);

            // need to compute total based on student info
            long tution;
            long sum = 0;
            for (RegisterCourse regcourse : allregcourses) {

                System.out.println("\n---------------- in processForm: regcourse id="+ regcourse.getId());

                tution = regcourse.getBase();
                if (!student.isMdRes())
                    tution += regcourse.getNmr();

                if (!student.isSixtyPlus())
                    tution += regcourse.getFee();

                regcourse.setTotal(tution);
                sum += tution;
            }
            student.setTotalTuition(sum);
            studentRepository.save(student);

            for (RegisterCourse regcourse : allregcourses) {
                System.out.println("\n-------------------student id=" + student.getId() + " regcourse old="+ regcourse.getOrgstudent());

                regcourse.setOrgstudent(student.getId());

                System.out.println(("----------------- regcourse id=" + regcourse.getId())+ " new regcourse old="+ regcourse.getOrgstudent());

                regcourse.setStudent(student);
                registerCourseRepository.save(regcourse);
            }

            student.setRegisterCourses(allregcourses);
            model.addAttribute("student", student);
            return "previewForm";
        }
    }

    @PostMapping("/printForm")
    public String printForm(){
        System.out.println("In printForm");
        return "redirect:/";
    }

    //Displays the Registration Forms filled in
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public @ResponseBody
    String download(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("currStudent") Student student) throws IOException {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            genPdf.makeRegPDF(student, baos);
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                    "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        studentRepository.delete(student);
        return "printerPage";
    }

}
