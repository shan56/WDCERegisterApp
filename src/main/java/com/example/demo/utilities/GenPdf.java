package com.example.demo.utilities;

import com.example.demo.models.RegisterCourse;
import com.example.demo.models.Student;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class GenPdf {
    /*Coordinates for the boxes are taken at the bottom left corner of where the box will be.
    IText 7 uses a coordinate system based on computer pts. For Example an 8.5 by 11 paper
    is 612 by 792 pts
     */


    public static void makeRegPDF (Student student, ByteArrayOutputStream stream, String filename) {
        String src = "static/forms/wdce-registration-form.pdf";
        String dest = "static/forms/test.pdf";
        float height = 16, onebox = 17, y_current;
        PdfDocument pdfDoc;

        try {
            //Creates a new pdf doc
            if (stream != null)
                pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(stream));
            else
                pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(filename));

            // Adds content to page

            //student id
            y_current = 690;
            makeTextBox(pdfDoc,student.getStudentId(),140, y_current, onebox*7, height,1, 12);
            makeTextBox(pdfDoc,student.getMonth(),328, y_current, onebox*2-2, height,1, 12);
            makeTextBox(pdfDoc,student.getDay(),370, y_current, onebox*2-2, height,1, 12);
            makeTextBox(pdfDoc,student.getYear(),413, y_current, onebox*4-2, height,1, 12);

            if (student.isMale())
                makeCheckBox(pdfDoc,true,554,691,5, 5,1);  //male 685+6
            else
                makeCheckBox(pdfDoc,true,514,691,5, 5,1);  //female

            y_current -= 31;
            makeTextBox(pdfDoc,student.getLastname(),25, y_current, onebox*14, height,1, 12); //240
            makeTextBox(pdfDoc,student.getFirstname(),282, y_current, onebox*14, height,1, 12);//241
            makeTextBox(pdfDoc,student.getMiddleInitial(),547, y_current, onebox-2, height,1, 12);//19
            y_current -= 34;
            makeTextBox(pdfDoc,student.getAddress(),57,y_current,onebox*25,height,1, 12);
            makeTextBox(pdfDoc,student.getAptnum(),519,y_current,onebox*4,height,1, 12);

            y_current -= 34;
            makeTextBox(pdfDoc,student.getCity(),57,y_current,onebox*16,height,1, 12);
            makeTextBox(pdfDoc,student.getState(),363,y_current,onebox*2,height,1, 12); //onebox*2-2
            makeTextBox(pdfDoc,student.getZipcode(),427,y_current,onebox*5,height,1, 12);//onebox*5-1
            makeTextBox(pdfDoc,student.getZipcode2(),522,y_current,onebox*4,height,1, 12);//onebox*4-1

            y_current -= 30;
            makeTextBox(pdfDoc,student.getHomephone(),57,y_current,onebox*10,height,1, 12);
            makeTextBox(pdfDoc,student.getWorkphone(),263,y_current,onebox*10,height,1, 12);

            y_current -= 25;
            makeTextBox(pdfDoc,student.getCellphone(),57,y_current,onebox*10,height,1, 12);
            makeTextBox(pdfDoc,student.getEmail(),263,y_current,onebox*18,height,1, 12);

            y_current -= 15;
            if (student.isAttenMcb4())
                makeCheckBox(pdfDoc,true,140,y_current,5, 5,1);
            else
                makeCheckBox(pdfDoc,true,168,y_current,5, 5,1);

            y_current -= 15;
            switch (student.getHearMC()) {
                case "Mail brochure":
                    makeCheckBox(pdfDoc, true, 127, y_current, 5, 5, 1);
                    break;
                case "Website":
                    makeCheckBox(pdfDoc, true, 238, y_current, 5, 5, 1);
                    break;
                case "Social media":
                    makeCheckBox(pdfDoc, true, 285, y_current, 5, 5, 1);
                    break;
                case "Advertisement":
                    makeCheckBox(pdfDoc, true, 350, y_current, 5, 5, 1);
                    break;
                case "On campus":
                    makeCheckBox(pdfDoc, true, 418, y_current, 5, 5, 1);
                    break;
                case "other":
                    makeCheckBox(pdfDoc, true, 477, y_current, 5, 5, 1);
                    break;
                default:
                    break;
            }

             y_current -= 33;
            makeTextBox(pdfDoc,student.getDigits(),140,y_current,onebox*4,height,1, 12);

            // ethnicity
            y_current -= 27;
            if (student.isHispanic())
                makeCheckBox(pdfDoc,true,25,y_current,5, 5, 1);
            else
                makeCheckBox(pdfDoc,true,122,y_current,5, 5, 1);

             // race
            y_current -= 21;
            if (student.isAmericanIndian())
                makeCheckBox(pdfDoc,true,25,y_current,5, 5, 1);

            if (student.isAsian())
                makeCheckBox(pdfDoc,true,155,y_current,5, 5, 1);

            if (student.isBlackAfricanAmerican())
                makeCheckBox(pdfDoc,true,195,y_current,5, 5, 1);

            if (student.isHawaiian())
                makeCheckBox(pdfDoc,true,296,y_current,5, 5, 1);

            if (student.isWhite())
                makeCheckBox(pdfDoc,true,449,y_current,5, 5, 1);

//            //Us Citizen
            y_current -= 19;
            if (student.isCitizen())
                makeCheckBox(pdfDoc,true,25,y_current,5, 5, 1);

            if (student.isPermanentResident())
                makeCheckBox(pdfDoc,true,84,y_current,5, 5, 1);

            if (student.isGreenCard())
                makeCheckBox(pdfDoc,true,130,y_current-3,15, 2, 1);

            if (student.isWorkingCard())
                makeCheckBox(pdfDoc,true,200,y_current-3,15, 2, 1);

            if (student.isOtherStatus()) {
                makeCheckBox(pdfDoc,true,294,y_current,15, 2, 1);
                makeTextBox(pdfDoc, student.getOtherPermanent(), 380, y_current, onebox * 3, height, 1, 12);
            }
//            //Md res
            y_current -= 26;
            if (student.isMdRes())
                makeCheckBox(pdfDoc,true,25,y_current,5, 5, 1);  //MD resident

            y_current -= 10;
            if (student.isSixtyPlus())
                makeCheckBox(pdfDoc,true,25,y_current,5, 5, 1);   // 60+

            y_current -= 10;
            if (student.isMdNatGuard())
                makeCheckBox(pdfDoc,true,25,y_current,5, 5, 1);  // national guard

            y_current -= 39;
            float x_width = 45;
            int counter=0;
            Collection<RegisterCourse> crns;
            crns = student.getRegisterCourses();

            for (RegisterCourse crn : crns) {
                makeTextBox(pdfDoc, String.format("%d", crn.getCrnNo()), 25, y_current, x_width, 15, 1, 9);
                makeTextBox(pdfDoc, crn.getCourseNo(), 80, y_current, x_width, 15, 1, 9);
                makeTextBox(pdfDoc, crn.getTitle(), 138, y_current, 170, 15, 1, 9);
                //makeTextBox(pdfDoc, crn.getStartDate().toString(), 500, y_current, 95, 13, 1);
                makeTextBox(pdfDoc, "2020/12/12", 315, y_current, 55, 15, 1, 9);
                makeTextBox(pdfDoc, String.format("%s", crn.getBase()), 378, y_current, x_width, 15, 1, 9);
                makeTextBox(pdfDoc, String.format("%s", crn.getFee()), 430, y_current, x_width, 15, 1, 9);
                makeTextBox(pdfDoc, String.format("%s", crn.getNmr()), 480, y_current, x_width, 15, 1, 9);
                makeTextBox(pdfDoc, String.format("%s", crn.getTotal()), 540, y_current, x_width, 15, 1, 9);
                counter++;
                if (counter == 4) {
                    break;
                }
                y_current -= 15;
            }

            makeTextBox(pdfDoc, String.format("%s", student.getTotalTuition()), 540, 235, x_width, 15, 1, 9);

            // signature
            makeTextBox(pdfDoc, student.getFirstname()+" " + student.getLastname(), 25, 175, onebox*30, height, 1, 14);

            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
            String dateStr = formatter.format(date);
System.out.println(dateStr);
            student.setRegisterDate(date);

            makeTextBox(pdfDoc, dateStr, 315, 175, onebox*12, height, 1, 14);

            pdfDoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeTextBox(PdfDocument pdfDoc,String paragraph, float x, float y,
                                    float w, float h, int pageNum, int fontsize){
        if(paragraph==null){
            paragraph="";
        }
        Paragraph p = new Paragraph(paragraph);
        // p.setBorder(new SolidBorder(Color.CYAN,1,1));
        p.setVerticalAlignment(VerticalAlignment.MIDDLE);
        //  p.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //p.setFontSize(11); //12?
        p.setFontSize(fontsize);
        Rectangle r = new Rectangle(x,y,w,h);
        PdfCanvas pdfc = new PdfCanvas(pdfDoc.getPage(pageNum));
        //Comment out dark gray line when not testing
        //pdfc.saveState().setFillColor(Color.DARK_GRAY).rectangle(r).fill().restoreState();
        Canvas c = new Canvas(pdfc,pdfDoc,r);
        c.add(p);
        c.close();
    }

    //Makes a check box at location, if boolean statement is true fill of box is set to gray
    private static void makeCheckBox(PdfDocument pdfDoc,boolean bool,float x, float y, float w, float h, int pageNum ){
        Rectangle r = new Rectangle(x,y,w,h);
        PdfCanvas pdfc = new PdfCanvas(pdfDoc.getPage(pageNum));
        if(bool)
            pdfc.saveState().setFillColor(Color.DARK_GRAY).rectangle(r).fill().restoreState();
        else
            pdfc.saveState().setFillColor(Color.WHITE).rectangle(r).fill().restoreState();
    }

}

