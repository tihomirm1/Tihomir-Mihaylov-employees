package com.employee.report.repository;


import com.employee.report.dto.AssignmentTeamReportDTO;
import com.employee.report.model.AssignmentTeamReportMO;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssignmentTeamRepository extends JpaRepository<AssignmentTeamReportMO, Long> {


    @Query(value = "SELECT \n" +
            "    empId1, \n" +
            "    empId2, \n" +
            "    SUM(overlappingDuration) AS totalOverlappingDuration \n" +
            "FROM \n" +
            "    (SELECT \n" +
            "                r1.emp_id AS empId1, \n" +
            "                r2.emp_id AS empId2, \n" +
            "                r1.project_id as projectId, \n" +
            "                TIMESTAMPDIFF(DAY, GREATEST(r1.date_from, r2.date_from), LEAST(r1.date_to, r2.date_to)) AS overlappingDuration \n" +
            "            FROM \n" +
            "                assignment_team_report r1 \n" +
            "            JOIN \n" +
            "                assignment_team_report r2 ON \n" +
            "                r1.project_id = r2.project_id \n" +
            "            WHERE \n" +
            "                (r1.date_from BETWEEN r2.date_from AND r2.date_to OR r1.date_to BETWEEN r2.date_from AND r2.date_to) \n" +
            "                AND r1.emp_id < r2.emp_id) AS resultSet \n" +
            "GROUP BY \n" +
            "    empId1, empId2 \n" +
            "ORDER BY \n" +
            "    totalOverlappingDuration DESC;", nativeQuery = true)
    List<Tuple> getWorkTogetherData();

    @Query(value = "SELECT * FROM assignment_team_report WHERE emp_id = :employee1 OR emp_id = :employee2 ORDER BY project_id ASC", nativeQuery = true)
    List<AssignmentTeamReportMO> getProjects(@Param("employee1") Long employee1, @Param("employee2") Long employee2);


}
