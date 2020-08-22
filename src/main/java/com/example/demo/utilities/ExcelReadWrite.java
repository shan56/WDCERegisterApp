package com.example.demo.utilities;

import com.example.demo.models.CRNRecord;
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
import java.util.Iterator;

public class ExcelReadWrite {
    public void read(String filename, ExcelFileType excelFileType,
                     ArrayList<Topic> topicAL, ArrayList<Course> courseAL, ArrayList<CRNRecord> crnRecordAL) {
        Topic topic = new Topic();
        Course course = new Course();
        CRNRecord crnRecord = new CRNRecord();
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
                        crnRecord = new CRNRecord();
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
                            if (readCRN(crnRecord, idx, textBuffer, doubleBuffer))
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
                            crnRecordAL.add(crnRecord);
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


    private boolean readCRN(CRNRecord crnRecordBuf, int idx, String textBuf, double doubleBuf) {
        boolean modFlag = false;

        switch(idx) {
            case 0:// main course no
                crnRecordBuf.setMainCourseNo(textBuf);
                modFlag = true;
                break;

            case 1: // title
                crnRecordBuf.setTitle(textBuf);
                modFlag = true;
                break;

            case 2:   // course no
                crnRecordBuf.setCourseNo(textBuf);
                modFlag = true;
                break;

            case 3:   // crn
                crnRecordBuf.setCrn((long)doubleBuf);
                modFlag = true;

            case 4:  // location
                crnRecordBuf.setLocation(textBuf);
                modFlag = true;
                break;

            case 5:   // startDate
                crnRecordBuf.setStartDate((long)doubleBuf);
                modFlag = true;
                break;

            case 6:   // endDate
                crnRecordBuf.setEndDate((long)doubleBuf);
                modFlag = true;
                break;

            case 7:   // startTime
                crnRecordBuf.setStartTime((long)doubleBuf);
                modFlag = true;
                break;

            case 8:   // endTime
                crnRecordBuf.setEndTime((long)doubleBuf);
                modFlag = true;
                break;

            case 9:   // sessions
                crnRecordBuf.setSessions((long)doubleBuf);
                modFlag = true;
                break;

            case 10:   // weekdays
                crnRecordBuf.setWeekdays(textBuf);
                modFlag = true;
                break;

            case 11:   // base
                crnRecordBuf.setBase((long)doubleBuf);
                modFlag = true;
                break;

            case 12:   // fee
                crnRecordBuf.setFee((long)doubleBuf);
                modFlag = true;
                break;

            case 13:   // nmr
                crnRecordBuf.setNmr((long)doubleBuf);
                modFlag = true;
                break;

            default:  // error - ignore
                System.out.println("\n**********readCRN: unknown idx="+ idx);
                break;
        }

        return (modFlag);
    }


}