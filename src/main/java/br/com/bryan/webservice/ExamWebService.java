package br.com.bryan.webservice;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

@WebService
public interface ExamWebService {
	@WebMethod
	Exam getExam(Long id);
	
	@WebMethod
	List<Exam> getAllExams(SearchCriteria criteria);
	
	@WebMethod
    Exam createExam(Exam exam) throws ValidationException;
	
	@WebMethod
    void updateExam(Exam exam) throws ValidationException;
	
	@WebMethod
    void deleteExam(Long id) throws ValidationException;
}
