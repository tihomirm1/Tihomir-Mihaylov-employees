package com.employee.report.dto;

import com.employee.report.model.AssignmentTeamReportMO;

import java.time.LocalDate;

public class AssignmentTeamEntityDTO {


    private Long empId;
    private Long projectId;
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public AssignmentTeamEntityDTO(AssignmentTeamReportMO mo) {
        this.empId = mo.getEmpId();
        this.dateFrom = mo.getDateFrom();
        this.dateTo = mo.getDateTo();
        this.projectId = mo.getProjectId();
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }


}
