package br.com.bryan.facade;

import java.util.List;

import javax.ejb.Local;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

@Local
public interface ExamFacade {
	Exam findById(Long id);
	List<Exam> findAll(SearchCriteria criteria);
    Exam save(Exam exam) throws ValidationException;
    void update(Exam exam) throws ValidationException;
    void delete(Long id) throws ValidationException;
}
