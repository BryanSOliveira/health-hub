package br.com.bryan.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.ExamBean;
import br.com.bryan.ejb.ExamTakenBean;
import br.com.bryan.facade.ExamFacade;
import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class ExamFacadeImpl implements ExamFacade {

	@EJB
	private ExamBean examBean;
	
	@EJB
	private ExamTakenBean examTakenBean;

	@Override
	public Exam findById(Long id) {
		return examBean.findById(id);
	}

	@Override
	public Exam save(Exam exam) throws ValidationException {
		validateExam(exam);
		return examBean.save(exam);
	}

	@Override
	public List<Exam> findAll(SearchCriteria criteria) {
		return examBean.findAll(criteria);
	}

	@Override
	public void update(Exam exam) throws ValidationException {
		validateExam(exam);
		examBean.update(exam);
	}

	@Override
	public void delete(Long id) throws ValidationException {
		if(examTakenBean.isExamTaken(id)) {
			throw new ValidationException("Cannot delete exam as it has been taken by one or more employees.");
		}
		examBean.delete(id);
	}
	
	private void validateExam(Exam exam) throws ValidationException {
		if (exam.getName() == null || exam.getName().trim().isEmpty()) {
			throw new ValidationException("Invalid name.");
	    } else if (exam.getName().length() < 3) {
	        throw new ValidationException("Name must be at least 3 characters long.");
	    }
	}
}
