package com.example.demo.utilities;

import com.example.demo.models.CRNRecord;
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
import java.util.Collection;

public class GenPdf {
    /*Coordinates for the boxes are taken at the bottom left corner of where the box will be.
    IText 7 uses a coordinate system based on computer pts. For Example an 8.5 by 11 paper
    is 792 by 612 pts
     */


    public static void makeRegPDF (Student student, ByteArrayOutputStream stream) {
        String src = "static/forms/wdce-registration-form.pdf";
        float height = 16;
        float line2 = 655;
        float onebox = 17;
        float y_current;

        try {
            //Creates a new pdf doc
            PdfDocument pdfDoc;
            pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(stream));
            // Adds content to page

            //student id
            y_current = 685;
            makeTextBox(pdfDoc,student.getStudentId(),140, y_current, onebox*7, height,1);
            makeTextBox(pdfDoc,student.getMonth(),328, y_current, onebox*2-2, height,1);
            makeTextBox(pdfDoc,student.getDay(),370, y_current, onebox*2-2, height,1);
            makeTextBox(pdfDoc,student.getYear(),413, y_current, onebox*4-2, height,1);
            //makeTextBox(pdfDoc,"",465, y_current, 3, 6,1);//female
            //makeTextBox(pdfDoc,"",485, y_current, 3, 6,1);//male
            makeCheckBox(pdfDoc,true,515,y_current+6,4, 4,1);  //female
            makeCheckBox(pdfDoc,true,555,y_current+6,4, 4,1);  //male

            y_current -= 30;
            makeTextBox(pdfDoc,student.getLastname(),23, y_current, onebox*14, height,1); //240
            makeTextBox(pdfDoc,student.getFirstname(),280, y_current, onebox*14, height,1);//241
            makeTextBox(pdfDoc,student.getMiddleInitial(),547, y_current, onebox-2, height,1);//19

            y_current -= 34;
            makeTextBox(pdfDoc,student.getAddress(),57,y_current,onebox*25,height,1);
            makeTextBox(pdfDoc,student.getAptnum(),519,y_current,onebox*4,height,1);

            y_current -= 34;
            makeTextBox(pdfDoc,student.getCity(),57,y_current,onebox*16,height,1);
            makeTextBox(pdfDoc,student.getState(),361,y_current,onebox*2-2,height,1);
            makeTextBox(pdfDoc,student.getZipcode(),426,y_current,onebox*5-1,height,1);
            makeTextBox(pdfDoc,student.getZipcode2(),520,y_current,onebox*4-1,height,1);

            y_current -= 26;
            makeTextBox(pdfDoc,student.getHomephone(),57,y_current,onebox*10,height,1);
            makeTextBox(pdfDoc,student.getWorkphone(),263,y_current,onebox*10,height,1);

            y_current -= 25;
            makeTextBox(pdfDoc,student.getCellphone(),57,y_current,onebox*10,height,1);
            makeTextBox(pdfDoc,student.getEmail(),263,y_current,onebox*18,height,1);

            y_current -= 15;
//            if (student.isAttenMcb4())
//
//            else
//
            makeCheckBox(pdfDoc,true,140,y_current,4, 4,1);
            makeCheckBox(pdfDoc,true,168,y_current,4, 4,1);

            y_current -= 15;
//            switch (student.getHearMC()) {
//                case "Mail brochure":
//                    makeCheckBox(pdfDoc, true, 130, y_current, 4, 4, 1);
//                    break;
//                case "Website":
//                    makeCheckBox(pdfDoc, true, 200, y_current, 4, 4, 1);
//                    break;
//                case "Social media":
//                    makeCheckBox(pdfDoc, true, 300, y_current, 4, 4, 1);
//                    break;
//                case "Advertisement":
//                    makeCheckBox(pdfDoc, true, 400, y_current, 4, 4, 1);
//                    break;
//                case "On campus":
//                    makeCheckBox(pdfDoc, true, 500, y_current, 4, 4, 1);
//                    break;
//                case "other":
//                    makeCheckBox(pdfDoc, true, 600, y_current, 4, 4, 1);
//                    break;
//            }
            makeCheckBox(pdfDoc, true, 127, y_current, 4, 4, 1);
            makeCheckBox(pdfDoc, true, 238, y_current, 4, 4, 1);
            makeCheckBox(pdfDoc, true, 285, y_current, 4, 4, 1);
            makeCheckBox(pdfDoc, true, 350, y_current, 4, 4, 1);
            makeCheckBox(pdfDoc, true, 418, y_current, 4, 4, 1);
            makeCheckBox(pdfDoc, true, 477, y_current, 4, 4, 1);

            y_current -= 33;
            makeTextBox(pdfDoc,student.getDigits(),140,y_current,onebox*4,height,1);

            // ethnicity
            y_current -= 27;
            makeCheckBox(pdfDoc,true,25,y_current,4,4,1);
            makeCheckBox(pdfDoc,true,122,y_current,4, 4, 1);

            // race
            y_current -= 21;
            makeCheckBox(pdfDoc,true,25,y_current,4,4,1);
            makeCheckBox(pdfDoc,true,155,y_current,4,4,1);
            makeCheckBox(pdfDoc,true,195,y_current,4,4,1);
            makeCheckBox(pdfDoc,true,296,y_current,4,4,1);
            makeCheckBox(pdfDoc,true,449,y_current,4,4,1);




//            //Us Citizen
            y_current -= 19;
            makeCheckBox(pdfDoc,true,25,y_current,4,4,1);
            makeCheckBox(pdfDoc,true,84,y_current,4,4,1);
            makeCheckBox(pdfDoc,true,294,y_current,4,4,1);
            makeTextBox(pdfDoc,student.getOtherPermanent(),380,y_current,onebox*3,height,1);


//            //Md res
            y_current -= 26;
            makeCheckBox(pdfDoc,true,25,y_current,4,4,1);  //MD resident
            y_current -= 10;
            makeCheckBox(pdfDoc,true,25,y_current,4,4,1);   // 60+
            y_current -= 10;
            makeCheckBox(pdfDoc,true,25,y_current,4,4,1);  // national guard


            y_current -= 39;
            float x_width = 45;
            int counter=0;
            Collection<RegisterCourse> crns;
            crns = student.getRegisterCourses();
            for (RegisterCourse crn : crns) {
                makeTextBox(pdfDoc, String.format("%s", crn.getCrnNo()), 25, y_current, x_width, 15, 1);
                makeTextBox(pdfDoc, crn.getCourseNo(), 80, y_current, x_width, 15, 1);
                makeTextBox(pdfDoc, crn.getTitle(), 138, y_current, 170, 15, 1);
                //makeTextBox(pdfDoc, crn.getStartDate().toString(), 500, y_current, 95, 13, 1);
                makeTextBox(pdfDoc, "2020/12/12", 315, y_current, 60, 15, 1);
                makeTextBox(pdfDoc, String.format("%s", crn.getBase()), 380, y_current, x_width, 15, 1);
                makeTextBox(pdfDoc, String.format("%s", crn.getFee()), 430, y_current, x_width, 15, 1);
                makeTextBox(pdfDoc, String.format("%s", crn.getNmr()), 480, y_current, x_width, 15, 1);
                makeTextBox(pdfDoc, String.format("%s", crn.getTotal()), 540, y_current, x_width, 15, 1);
                counter++;
                if (counter == 4) {
                    break;
                }
                y_current -= 15;
            }

            makeTextBox(pdfDoc, String.format("%s", student.getTotalTuition()), 540, 235, x_width, 15, 1);
            pdfDoc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //COnversion method to change mm measurements to pts
    public static double mm2pt(int mm){
        return mm*2.83;
    }

    //Makes a text box, takes in a pdfdoc, statement, x x-coord, y y-coord, w width,h height, and page number
    private static void makeTextBox(PdfDocument pdfDoc,String paragraph,float x, float y, float w, float h, int pageNum ){
        if(paragraph==null){
            paragraph="";
        }
        Paragraph p = new Paragraph(paragraph);
        // p.setBorder(new SolidBorder(Color.CYAN,1,1));
        p.setVerticalAlignment(VerticalAlignment.MIDDLE);
        //  p.setHorizontalAlignment(HorizontalAlignment.CENTER);
        p.setFontSize(9);
        Rectangle r = new Rectangle( x,y,w,h);
        PdfCanvas pdfc = new PdfCanvas(pdfDoc.getPage(pageNum));
        //Comment out dark gray line when not testing
        pdfc.saveState().setFillColor(Color.DARK_GRAY).rectangle(r).fill().restoreState();
        //pdfc.saveState().setFillColor(Color.WHITE).rectangle(r).fill().restoreState();
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

    /*Makes a check appear for a yes location an no location

     */
    private static void makeYesNoCheck(PdfDocument pdfDoc,boolean bool,float yesX, float yesY, float yesW, float yesH,float noX, float noY, float noW, float noH, int pageNum){
       //Yes Box
        if(bool){
            makeCheckBox(pdfDoc,bool,yesX,yesY,yesW,yesH,pageNum);
        //no box
        }else{
            makeCheckBox(pdfDoc,true,noX,noY,noW,noH,pageNum);
        }
    }
}

