package com.employee.report.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AssignmentTeamReportDTO {

    private Long empId1;
    private Long empId2;
    private Long overlappingDuration;

    public AssignmentTeamReportDTO(Long empId1, Long empId2, Long overlappingDuration) {
        this.empId1 = empId1;
        this.empId2 = empId2;
        this.overlappingDuration = overlappingDuration;
    }

    public Long getEmpId1() {
        return empId1;
    }

    public void setEmpId1(Long empId1) {
        this.empId1 = empId1;
    }

    public Long getEmpId2() {
        return empId2;
    }

    public void setEmpId2(Long empId2) {
        this.empId2 = empId2;
    }

    public Long getOverlappingDuration() {
        return overlappingDuration;
    }

    public void setOverlappingDuration(Long overlappingDuration) {
        this.overlappingDuration = overlappingDuration;
    }





}
