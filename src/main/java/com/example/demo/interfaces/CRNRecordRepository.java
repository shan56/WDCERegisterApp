package com.example.demo.interfaces;

import com.example.demo.models.CRNRecord;
import org.springframework.data.repository.CrudRepository;

public interface CRNRecordRepository extends CrudRepository<CRNRecord, Long> {
    boolean existsByCourseNo(String courseno);
    CRNRecord findByCourseNo(String courseno);
    CRNRecord findByCrn(long crnno);
}
