package com.employee.report.controller;

import com.employee.report.dto.AssignmentTeamEntityDTO;
import com.employee.report.dto.AssignmentTeamReportDTO;
import com.employee.report.model.AssignmentTeamReportMO;
import com.employee.report.service.AssignmentTeamReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/assignment-team")
public class AssignmentTeamController {

    @Autowired
    private AssignmentTeamReportService fileProcessingService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a CSV file to upload.");
        }

        try {
            fileProcessingService.handleCSVUpload(file);
            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to process the file: " + e.getMessage());
        }
    }


    @GetMapping("/get-report")
    public ResponseEntity<AssignmentTeamReportDTO> getWorkTogetherReport() {
        AssignmentTeamReportDTO reportData = fileProcessingService.getReportData();

        if (reportData != null) {
            return ResponseEntity.ok(reportData);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/get-projects")
    public ResponseEntity<List<AssignmentTeamEntityDTO>> getProjects(@RequestParam(name = "emp1") Long emp1,
                                                                     @RequestParam(name = "emp2") Long emp2) {

        List<AssignmentTeamEntityDTO> reportData = fileProcessingService.getProjects(emp1, emp2);

        if (reportData.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(reportData);
        }
    }
}
