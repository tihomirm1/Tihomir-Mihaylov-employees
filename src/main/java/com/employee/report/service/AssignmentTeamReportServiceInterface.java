package com.employee.report.service;

import com.employee.report.dto.AssignmentTeamEntityDTO;
import com.employee.report.dto.AssignmentTeamReportDTO;
import com.employee.report.model.AssignmentTeamReportMO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AssignmentTeamReportServiceInterface {

    public AssignmentTeamReportMO handleCSVUpload(MultipartFile file);
    public List<AssignmentTeamEntityDTO> getProjects(Long empl1, Long empl2);
    public AssignmentTeamReportDTO getReportData();


}
