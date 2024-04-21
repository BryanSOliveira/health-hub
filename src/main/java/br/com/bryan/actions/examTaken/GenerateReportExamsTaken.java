package br.com.bryan.actions.examTaken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.action.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

import br.com.bryan.facade.ExamTakenFacade;
import br.com.bryan.model.ExamTaken;

public class GenerateReportExamsTaken extends ActionSupport implements SessionAware {

	private static final long serialVersionUID = 1L;
	
	private ExamTakenFacade examTakenFacade;
	private Map<String, Object> sessionMap;
	
	private LocalDate startDate;
	private LocalDate endDate;
	private InputStream fileInputStream;
	
	public GenerateReportExamsTaken() {
		try {
			InitialContext ic = new InitialContext();
			examTakenFacade = (ExamTakenFacade) ic.lookup("java:app/health-hub/ExamTakenFacadeImpl");
		} catch (NamingException e) {
			throw new RuntimeException("Failed to look up ExamTakenFacade", e);
		}
	}
	
	public void validate() {
		if (startDate == null) {
	        addFieldError("startDate", "Invalid start date.");
	    } else if (endDate == null) {
	    	addFieldError("endDate", "Invalid end date.");
	    } else if (endDate.isBefore(startDate)) {
	        addFieldError("endDate", "End date must be after start date.");
	    }
	}
	
	public String execute() {
		try {
			Boolean isLoggedIn = sessionMap.get("LOGGED_IN_USER") != null;
			if(isLoggedIn) {
				downloadReport();
				return SUCCESS;
			} else {
				return "login";
			}
		} catch (Exception e) {
			addActionError("An error occurred: " + e.getMessage());
			return ERROR;
		}
	}
	
	public void downloadReport() throws IOException {
		List<ExamTaken> examsTaken = examTakenFacade.findByDateRange(startDate, endDate);
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
		    Workbook workbook = new XSSFWorkbook();
		    try (workbook) {
		    	Sheet sheet = workbook.createSheet("Exams Taken Report");
		    	
		    	// Styles
		    	CellStyle headerStyle = workbook.createCellStyle();
			    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			    headerStyle.setAlignment(HorizontalAlignment.CENTER);
			    
			    Font headerFont = workbook.createFont();
			    headerFont.setBold(true);
			    headerFont.setFontHeightInPoints((short) 14);
			    headerStyle.setFont(headerFont);
			    
			    // Data Style
		        CellStyle textStyle = workbook.createCellStyle();
		        Font textFont = workbook.createFont();
		        textFont.setFontName("Arial");
		        textFont.setFontHeightInPoints((short) 12);
		        textStyle.setFont(textFont);
		        
		        CellStyle dateStyle = workbook.createCellStyle();
		        CreationHelper createHelper = workbook.getCreationHelper();
		        dateStyle.cloneStyleFrom(textStyle);
		        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
		        
		        // Set headers
		        String[] headers = {"Employee ID", "Employee Name", "Exam ID", "Exam Name", "Exam Taken Date"};
			    
			    int rowNum = 0; 
			    
			    // Create filter row
			    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			    
			    Row filterInfoRow = sheet.createRow(rowNum++);
			    Cell filterInfoCell = filterInfoRow.createCell(0);
			    filterInfoCell.setCellValue("Report from " + startDate.format(formatter) + " to " + endDate.format(formatter));
			    filterInfoCell.setCellStyle(headerStyle);
			    
			    sheet.addMergedRegion(new CellRangeAddress(
		    	    rowNum - 1,
		    	    rowNum - 1,
		    	    0,
		    	    headers.length - 1
		    	));
			    
			    // Empty row
			    rowNum++;
			    
			    // Create header
		        Row headerRow = sheet.createRow(rowNum++);
		        for (int i = 0; i < headers.length; i++) {
		            Cell cell = headerRow.createCell(i);
		            cell.setCellValue(headers[i]);
		            cell.setCellStyle(headerStyle);
		        } 		        
		        
		        // Data rows
	            for (ExamTaken examTaken : examsTaken) {
	                Row row = sheet.createRow(rowNum++);
	               
	                Cell cell0 = row.createCell(0);
	                cell0.setCellValue(examTaken.getEmployee().getId());
	                cell0.setCellStyle(textStyle);

	                Cell cell1 = row.createCell(1);
	                cell1.setCellValue(examTaken.getEmployee().getName());
	                cell1.setCellStyle(textStyle);

	                Cell cell2 = row.createCell(2);
	                cell2.setCellValue(examTaken.getExam().getId());
	                cell2.setCellStyle(textStyle);

	                Cell cell3 = row.createCell(3);
	                cell3.setCellValue(examTaken.getExam().getName());
	                cell3.setCellStyle(textStyle);
	                
	                Cell cell4 = row.createCell(4);
	                cell4.setCellValue(examTaken.getDate());
	                cell4.setCellStyle(dateStyle);
	            }
	            
	            for (int col = 0; col < headers.length; col++) {
	                sheet.autoSizeColumn(col);
	            }

		        workbook.write(out);
		    }
		    fileInputStream = new ByteArrayInputStream(out.toByteArray());
		}
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	
	@Override
	public void withSession(Map<String, Object> session) {
		sessionMap = session;
	}
}
