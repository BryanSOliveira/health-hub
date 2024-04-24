package br.com.bryan.facade;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.exceptions.EntityNotFoundException;
import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

@Local
public interface ExamTakenFacade {
	ExamTaken findById(Long id) throws EntityNotFoundException;
	
	ExamTaken save(ExamTaken examTaken) throws ValidationException;
	
    void update(ExamTaken examTaken) throws ValidationException, EntityNotFoundException;
    
    void delete(Long id) throws EntityNotFoundException;
    
    List<ExamTaken> findAll(SearchCriteria criteria);
    
    List<ExamTaken> findByDateRange(LocalDate startDate, LocalDate endDate);
}
