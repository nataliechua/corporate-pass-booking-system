package project.util;

//https://www.codejava.net/frameworks/spring-boot/export-data-to-excel-example

import project.entity.*;
import project.dto.*;
import project.exception.*;
import project.service.*;
import project.repository.*;
import java.util.*;
import java.util.stream.Collectors;
 
import java.io.IOException;
import java.util.List;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 
public class LoanExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Loan> listLoans;
     
    public LoanExcelExporter(List<Loan> listLoans) {
        this.listLoans = listLoans;
        workbook = new XSSFWorkbook();
    }
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Loans");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Loan_ID", style);      
        createCell(row, 1, "Staff_ID", style);       
        createCell(row, 2, "Loan_Date", style);    
        createCell(row, 3, "Attraction", style);
        createCell(row, 4, "Loan_Status", style);
        createCell(row, 5, "Pass_List", style);
        createCell(row, 6, "Saturday_Borrower", style);
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (Loan loan : listLoans) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, loan.getLoanId(), style);
            createCell(row, columnCount++, loan.getStaff().getStaffId(), style);
            createCell(row, columnCount++, loan.getLoanDate(), style);
            createCell(row, columnCount++, loan.getAttraction(), style);
            createCell(row, columnCount++, loan.getLoanStatus(), style);
            String strLoanList = loan.getPassList().stream().map(p->String.valueOf(p.getPassId())).collect(Collectors.joining(" "));
            createCell(row, columnCount++, strLoanList, style);
            createCell(row, columnCount++, loan.getSaturdayBorrower(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}