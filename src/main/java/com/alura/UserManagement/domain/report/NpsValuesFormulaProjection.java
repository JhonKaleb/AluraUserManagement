package com.alura.UserManagement.domain.report;

public interface NpsValuesFormulaProjection {
    String getCourseName();
    String getCourseCode();
    Integer getTotalResponses();
    Integer getPromoters();
    Integer getDetractors();
}
