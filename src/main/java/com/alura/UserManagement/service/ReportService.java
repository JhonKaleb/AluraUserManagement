package com.alura.UserManagement.service;

import com.alura.UserManagement.domain.report.NpsReportDTO;
import com.alura.UserManagement.domain.report.NpsValuesFormulaProjection;
import com.alura.UserManagement.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    RatingRepository ratingRepository;

    public ResponseEntity<List<NpsReportDTO>> calculateNpsForCourses() {
        var valuesForNPSFormula = ratingRepository.getValuesForNPSFormula();
        var npsReportDTOs = valuesForNPSFormula
                .stream()
                .map(this::formatNPSReportDTO)
                .toList();
        return ResponseEntity.ok(npsReportDTOs);
    }

    private NpsReportDTO formatNPSReportDTO(NpsValuesFormulaProjection valuesForNPSFormula) {
        var nps = calculateNps(valuesForNPSFormula);
        return new NpsReportDTO(valuesForNPSFormula.getCourseCode(), valuesForNPSFormula.getCourseName(), nps);

    }

    private double calculateNps(NpsValuesFormulaProjection valuesForNPSFormula) {
        var promoters = valuesForNPSFormula.getPromoters();
        var detractors = valuesForNPSFormula.getDetractors();
        var totalResponses = valuesForNPSFormula.getTotalResponses();

        return ((double) (promoters - detractors) / totalResponses) * 100;
    }
}
