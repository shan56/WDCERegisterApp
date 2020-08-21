package com.example.demo.controllers;

import com.example.demo.interfaces.*;
import com.example.demo.models.*;
import com.example.demo.utilities.ExcelFileType;
import com.example.demo.utilities.ExcelReadWrite;
import com.example.demo.utilities.GenPdf;
import org.apache.poi.ss.formula.functions.Now;
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
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class MainController {
    private final String FILE_PATH = "C:\\Users\\Sue\\Documents\\";

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    CRNRepository crnRepository;

    @Autowired
    StudentRepository studentRepository;

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


    @GetMapping("/studentform")
    public String studentform(Model model) {
        ArrayList<ClassCRN> allclasses = new ArrayList<ClassCRN>();

        //need to get crns from CART
        //collect info from CRN & COURSE tables to fill CLASSCRN
        ClassCRN temp = new ClassCRN();
        LocalDate current = LocalDate.of(2020, 8, 20);
        temp.setCourseno("CMP 111");
        temp.setCrnno("123123");
        temp.setTuition(330);
        temp.setFee(100);
        temp.setNonMdfee(99);
        temp.setCourseTotal(499);
        temp.setStart(current);
        temp.setTitle("Testing CRN");

        allclasses.add(temp);

        model.addAttribute("student", new Student());
        model.addAttribute("classes", allclasses);
        return "studentform";
    }

    @PostMapping("/studentform")
    public String displayform(@Valid @ModelAttribute("student") Student student, BindingResult result, Model model) {
        //String name= student.getFirstname()+student.getLastname();
        if (result.hasErrors()) {
            //model.addAttribute("actCourses", courseRepository.findCoursesByActiveIsTrue());
            return "studentform";
        } else {
            studentRepository.save(student);
            //model.addAttribute(student);
            return "printRegistration";
        }
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
