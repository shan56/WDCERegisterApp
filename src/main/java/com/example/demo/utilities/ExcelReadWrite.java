package com.example.demo.utilities;

import com.example.demo.models.CRN;
import com.example.demo.models.Course;
import com.example.demo.models.Topic;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class ExcelReadWrite {
    public void read(String filename, ExcelFileType excelFileType,
                     ArrayList<Topic> topicAL, ArrayList<Course> courseAL, ArrayList<CRN> crnAL) {
        Topic topic = new Topic();
        Course course = new Course();
        CRN crn = new CRN();
        String textBuffer = "";
        double doubleBuffer = 0;

        try {
            FileInputStream excelFile = new FileInputStream(new File(filename));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();

            iterator.next();       // skip header

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();
                int idx = 0;
                boolean modified = false;

                switch (excelFileType) {
                    case TOPIC:
                        topic = new Topic();
                        break;

                    case COURSE:
                        course = new Course();
                        break;

                    case CRN:
                        crn = new CRN();
                        break;
                }

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();
                    switch (currentCell.getCellTypeEnum()) {
                        case STRING:
                            textBuffer = currentCell.getStringCellValue();
                            break;

                        case NUMERIC:
                            doubleBuffer = currentCell.getNumericCellValue();
                            break;

                        case BLANK:
                            textBuffer = " ";
                            break;

                        default:
                            textBuffer = " ";
                            System.out.println("\n********** ExcelReadWrite: idx=" + idx);
                            System.out.println("\n********** ExcelReadWrite: getCellTypeEnumb()=" + currentCell.getCellTypeEnum());
                    }

                    switch (excelFileType) {
                        case TOPIC:
                            if (readTopic(topic, idx, textBuffer, doubleBuffer))
                                modified = true;
                            break;

                        case COURSE:
                            if (readCourse(course, idx, textBuffer, doubleBuffer))
                                modified = true;
                            break;

                        case CRN:
                            if (readCRN(crn, idx, textBuffer, doubleBuffer))
                                modified = true;
                            break;
                    }

                    idx++;
                }


//                if (modTopic && (fileType == FileType.TOPIC)) {
//                    topicRepo.save(topic);
//                }
//
//                if (modSubject && (fileType == FileType.COURSE)) {
//                    Topic temp = topicRepo.findById(course.getOrgTopicId()).get();
//                    course.setTopic(temp);
//                    courseRepo.save(course);
//
//                }

                if (modified) {
                    switch (excelFileType) {
                        case TOPIC:
                            topicAL.add(topic);
                            break;

                        case COURSE:
                            courseAL.add(course);
                            break;

                        case CRN:
                            crnAL.add(crn);
                            break;

                        default:
                            System.out.println("\n********** ExcelReadWrite: Unknown excelFileType="+ excelFileType);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private boolean readTopic(Topic topic, int idx, String textBuf, double doubleBuf) {
        boolean modFlag = false;

        switch(idx) {
            case 0:// topic id
                topic.setId((long)doubleBuf);
                modFlag = true;
                break;

            case 1:   // description   // topic's title
                topic.setTitle(textBuf);
                modFlag = true;
                break;
            case 2:   // description
                topic.setDescription(textBuf);
                modFlag = true;
                break;
            default:  // error - ignore
                System.out.println("\n**********readTopics: unknown idx="+ idx);
                break;
        }

        return (modFlag);
    }


    private boolean readCourse(Course course, int idx, String textBuf, double doubleBuf) {
        boolean modFlag = false;

        switch(idx) {
            case 0:   // course's topic id
                course.setOrgTopicId((long)doubleBuf);
                modFlag = true;
                break;

            case 1:   // course's courseID
                course.setCourseId(textBuf);
                modFlag = true;
                break;

            case 2:   // title
                course.setTitle(textBuf);
                modFlag = true;
                break;

            case 3:
                course.setOption(textBuf);
                modFlag = true;
                break;

            case 4:
                course.setHour((long)doubleBuf);
                modFlag = true;
                break;

            case 5:
                course.setMaterials(textBuf);
                modFlag = true;
                break;

            case 6:
                course.setDescription(textBuf);
                modFlag = true;
                break;

            case 7:
                course.setPrerequisites(textBuf);
                modFlag = true;
                break;

            case 8:
                course.setObjectives(textBuf);
                modFlag = true;
                break;

            default:  // error - ignore
                System.out.println("\n**********readCourse: unknown idx="+ idx);
                break;
        }

        return (modFlag);
    }


    private boolean readCRN(CRN crnBuf, int idx, String textBuf, double doubleBuf) {
        boolean modFlag = false;

        switch(idx) {
            case 0:// main course no
                crnBuf.setMainCourseNo(textBuf);
                modFlag = true;
                break;

            case 1:   // course no
                crnBuf.setCourseNo(textBuf);
                modFlag = true;
                break;

            case 2:   // crn
                crnBuf.setCrn((long)doubleBuf);
                modFlag = true;
                break;

            case 3:   // startDate
                crnBuf.setStartDate((long)doubleBuf);
                modFlag = true;
                break;

            case 4:   // endDate
                crnBuf.setEndDate((long)doubleBuf);
                modFlag = true;
                break;

            case 5:   // startTime
                crnBuf.setStartTime((long)doubleBuf);
                modFlag = true;
                break;

            case 6:   // endTime
                crnBuf.setEndTime((long)doubleBuf);
                modFlag = true;
                break;

            case 7:   // sessions
                crnBuf.setSessions((long)doubleBuf);
                modFlag = true;
                break;

            case 8:   // weekdays
                crnBuf.setWeekdays(textBuf);
                modFlag = true;
                break;

            case 9:   // base
                crnBuf.setBase((long)doubleBuf);
                modFlag = true;
                break;

            case 10:   // fee
                crnBuf.setFee((long)doubleBuf);
                modFlag = true;
                break;

            case 11:   // nmr
                crnBuf.setNmr((long)doubleBuf);
                modFlag = true;
                break;

            default:  // error - ignore
                System.out.println("\n**********readCRN: unknown idx="+ idx);
                break;
        }

        return (modFlag);
    }


}