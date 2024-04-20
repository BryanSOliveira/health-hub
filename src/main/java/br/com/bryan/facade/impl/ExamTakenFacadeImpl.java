package br.com.bryan.facade.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.ExamTakenBean;
import br.com.bryan.facade.ExamTakenFacade;
import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class ExamTakenFacadeImpl implements ExamTakenFacade {
	
	@EJB
	private ExamTakenBean examTakenBean;

	@Override
	public ExamTaken findById(Long id) {
		return examTakenBean.findById(id);
	}

	@Override
	public ExamTaken save(ExamTaken examTaken) throws ValidationException {
		if (examTaken.getEmployee() == null || examTaken.getEmployee().getId() == null) {
			throw new ValidationException("Invalid employee id.");
	    } else if (examTaken.getExam() == null || examTaken.getExam().getId() == null) {
			throw new ValidationException("Invalid exam id.");
	    } else if (examTaken.getDate() == null) {
	        throw new ValidationException("Invalid date.");
	    }
		
		return examTakenBean.save(examTaken);
	}

	@Override
	public void update(ExamTaken examTaken) throws ValidationException {
		if (examTaken.getEmployee() == null || examTaken.getEmployee().getId() == null) {
			throw new ValidationException("Invalid employee id.");
	    } else if (examTaken.getExam() == null || examTaken.getExam().getId() == null) {
			throw new ValidationException("Invalid exam id.");
	    } else if (examTaken.getDate() == null) {
	        throw new ValidationException("Invalid date.");
	    }
		
		examTakenBean.update(examTaken);
	}

	@Override
	public void delete(Long id) {
		examTakenBean.delete(id);
	}

	@Override
	public List<ExamTaken> findAll(SearchCriteria criteria) {
		return examTakenBean.findAll(criteria);
	}

}
