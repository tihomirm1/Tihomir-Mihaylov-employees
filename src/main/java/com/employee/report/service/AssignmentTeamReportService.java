package com.employee.report.service;


import com.employee.report.dto.AssignmentTeamEntityDTO;
import com.employee.report.dto.AssignmentTeamReportDTO;
import com.employee.report.model.AssignmentTeamReportMO;
import com.employee.report.model.AssignmentTeamReportFactory;
import com.employee.report.repository.AssignmentTeamRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignmentTeamReportService implements AssignmentTeamReportServiceInterface{

    @Autowired
    private AssignmentTeamRepository employeeRepository;

    public AssignmentTeamReportMO handleCSVUpload(MultipartFile file) {

        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReaderBuilder(reader).build()) {

            List<String[]> csvData = csvReader.readAll();
            for (String[] row : csvData) {

                if(isNonEmptyRow(row)){

                    AssignmentTeamReportMO csvModel = AssignmentTeamReportFactory.createReportObject(row);

                    if(csvModel != null){
                        employeeRepository.save(csvModel);
                    }

                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException | NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
        }

        return null;

    }



    public List<AssignmentTeamEntityDTO> getProjects(Long empl1, Long empl2){

        return convertMoToDto(employeeRepository.getProjects(empl1, empl2));
    }

    private List<AssignmentTeamEntityDTO> convertMoToDto(List<AssignmentTeamReportMO> listMO){

        return listMO.stream()
                .map(AssignmentTeamEntityDTO::new)
                .collect(Collectors.toList());

    }

    private boolean isNonEmptyRow(String[] row) {
        for (String cell : row) {
            if (cell != null && !cell.trim().isEmpty() && !cell.equals("\"\"")) {
                return true;
            }
        }
        return false;
    }


    public List<AssignmentTeamReportMO> getAll(){

        return employeeRepository.findAll();

    }




    private AssignmentTeamReportDTO convertToDto(Tuple queryResult){

        return new AssignmentTeamReportDTO(
                queryResult.get(0, Long.class),
                queryResult.get(1, Long.class),
                queryResult.get(2, BigDecimal.class).longValue());

    }

    public AssignmentTeamReportDTO getReportData(){

        List<Tuple> result = employeeRepository.getWorkTogetherData();
        return convertToDto(result.get(0));
    }

}
