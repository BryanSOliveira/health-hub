package br.com.bryan.facade;

import java.util.List;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

@Local
public interface ExamTakenFacade {
	ExamTaken findById(Long id);
	
	ExamTaken save(ExamTaken examTaken) throws ValidationException;
	
    void update(ExamTaken examTaken) throws ValidationException;
    
    void delete(Long id);
    
    List<ExamTaken> findAll(SearchCriteria criteria);
}
