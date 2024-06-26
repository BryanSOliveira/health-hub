package br.com.bryan.dao;

import java.time.LocalDate;
import java.util.List;

import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

public interface ExamTakenDAO {
	ExamTaken findById(Long id);

	ExamTaken save(ExamTaken examTaken);

	void update(ExamTaken examTaken);

	void delete(Long id);

	List<ExamTaken> findAll(SearchCriteria criteria);
	
	void deleteExamsTakenByEmployeeId(Long employeeId);
	
	boolean isExamTaken(Long examId);
	
	boolean isDuplicateExamTaken(ExamTaken examTaken);
	
	List<ExamTaken> findByDateRange(LocalDate startDate, LocalDate endDate);
}
