package com.alura.UserManagement.controller;

import com.alura.UserManagement.domain.report.NpsReportDTO;
import com.alura.UserManagement.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/nps")
    public ResponseEntity<List<NpsReportDTO>> getNpsReport() {
        return reportService.calculateNpsForCourses();
    }
}
