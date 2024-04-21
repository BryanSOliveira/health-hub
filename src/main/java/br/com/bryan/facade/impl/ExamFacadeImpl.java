package br.com.bryan.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.ExamBean;
import br.com.bryan.facade.ExamFacade;
import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class ExamFacadeImpl implements ExamFacade {

	@EJB
	private ExamBean examBean;

	@Override
	public Exam findById(Long id) {
		return examBean.findById(id);
	}

	@Override
	public Exam save(Exam exam) throws ValidationException {
		if (exam.getName() == null || exam.getName().trim().isEmpty()) {
			throw new ValidationException("Invalid name.");
	    } else if (exam.getName().length() < 3) {
	        throw new ValidationException("Name must be at least 3 characters long.");
	    }
		
		return examBean.save(exam);
	}

	@Override
	public List<Exam> findAll(SearchCriteria criteria) {
		return examBean.findAll(criteria);
	}

	@Override
	public void update(Exam exam) throws ValidationException {
		if (exam.getName() == null || exam.getName().trim().isEmpty()) {
			throw new ValidationException("Invalid name.");
	    } else if (exam.getName().length() < 3) {
	        throw new ValidationException("Name must be at least 3 characters long.");
	    }
		examBean.update(exam);
	}

	@Override
	public void delete(Long id) {
		examBean.delete(id);
	}
}
