package br.com.bryan.facade;

import java.util.List;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.exceptions.EntityNotFoundException;
import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

@Local
public interface ExamFacade {
	Exam findById(Long id) throws EntityNotFoundException;
	
	List<Exam> findAll(SearchCriteria criteria);
	
    Exam save(Exam exam) throws ValidationException;
    
    void update(Exam exam) throws ValidationException, EntityNotFoundException;
    
    void delete(Long id) throws ValidationException, EntityNotFoundException;
}
