package com.employee.report.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class AssignmentTeamReportFactory {


    // !!! date formats to be taken from config file
    private static final List<DateTimeFormatter> DATE_FORMATTERS = Arrays.asList(
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd-MM-yyyy")
    );

    public static AssignmentTeamReportMO createReportObject(String[] row) {

        LocalDate dateFrom = processDate("DateFrom", row[2]);
        LocalDate dateTo = processDate("DateTo", row[3]);

        if(dateFrom.isBefore(dateTo) && dateFrom != null && dateTo != null ){

            AssignmentTeamReportMO csvData = new AssignmentTeamReportMO();
            csvData.setEmpId(parseLong(row[0]));
            csvData.setProjectId(parseLong(row[1]));
            csvData.setDateFrom(dateFrom);
            csvData.setDateTo(dateTo);
            return csvData;

        }

        return null;

    }



    private static LocalDate tryParseDate(String date){

        for (DateTimeFormatter formatter : DATE_FORMATTERS) {
            try {
                return LocalDate.parse(date, formatter);
            } catch (DateTimeParseException e) {
                // try next format
            }
        }
        return null;

    }

    private static LocalDate processDate(String field, String date){

        if (date == null || "NULL".equals(date) || "null".equals(date)) {
            if(field.equals("DateTo")){
                return LocalDate.now();
            }else{
                return null;
            }
        }

        return tryParseDate(date);

    }


    private static Long parseLong(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            // Handle the case where the value is not a valid Long
            System.err.println("Error parsing Long: " + value);
            e.printStackTrace();
            return null;
        }
    }
}