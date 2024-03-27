package com.alura.UserManagement;

import com.alura.UserManagement.domain.report.NpsReportDTO;
import com.alura.UserManagement.domain.report.NpsValuesFormulaProjection;
import com.alura.UserManagement.repository.RatingRepository;
import com.alura.UserManagement.service.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ReportTests {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private ReportService reportService;

    // Helper method to mock NpsValuesFormulaProjection data
    private NpsValuesFormulaProjection createMockNpsValues(String courseCode, String courseName, int totalResponses, int promoters, int detractors) {
        return new NpsValuesFormulaProjection() {
            @Override
            public String getCourseName() {
                return courseName;
            }

            @Override
            public String getCourseCode() {
                return courseCode;
            }

            @Override
            public Integer getTotalResponses() {
                return totalResponses;
            }

            @Override
            public Integer getPromoters() {
                return promoters;
            }

            @Override
            public Integer getDetractors() {
                return detractors;
            }
        };
    }

    @Test
    public void testCalculateNpsForCourses() {
        List<NpsValuesFormulaProjection> mockData = List.of(
                createMockNpsValues("COURSE123", "Course 123", 100, 70, 10),
                createMockNpsValues("COURSE456", "Course 456", 50, 35, 5)
        );

        Mockito.when(ratingRepository.getValuesForNPSFormula()).thenReturn(mockData);

        ResponseEntity<List<NpsReportDTO>> response = reportService.calculateNpsForCourses();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        // Verify NPS calculations
        assertEquals(60.0, response.getBody().get(0).nps()); // For COURSE123: ((70-10)/100)*100 = 60.0
        assertEquals(60.0, response.getBody().get(1).nps()); // For COURSE456: ((35-5)/50)*100 = 60.0
    }
}