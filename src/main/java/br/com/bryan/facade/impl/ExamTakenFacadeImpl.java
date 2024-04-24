package br.com.bryan.facade.impl;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.ejb.ExamTakenBean;
import br.com.bryan.exceptions.EntityNotFoundException;
import br.com.bryan.facade.ExamTakenFacade;
import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class ExamTakenFacadeImpl implements ExamTakenFacade {
	
	@EJB
	private ExamTakenBean examTakenBean;

	@Override
	public ExamTaken findById(Long id) throws EntityNotFoundException {
		ExamTaken examTaken = examTakenBean.findById(id);
		if (examTaken == null) {
            throw new EntityNotFoundException("Exam Taken not found with ID: " + id);
        }
		return examTaken;
	}

	@Override
	public ExamTaken save(ExamTaken examTaken) throws ValidationException {
		validateExamTaken(examTaken);
		if(examTakenBean.isDuplicateExamTaken(examTaken)) {
			throw new ValidationException("Duplicate exam entry for the same employee on the same date is not allowed.");
		}
		return examTakenBean.save(examTaken);
	}

	@Override
	public void update(ExamTaken examTaken) throws ValidationException, EntityNotFoundException {
		findById(examTaken.getId());
		validateExamTaken(examTaken);
		examTakenBean.update(examTaken);
	}

	@Override
	public void delete(Long id) throws EntityNotFoundException {
		findById(id);
		examTakenBean.delete(id);
	}

	@Override
	public List<ExamTaken> findAll(SearchCriteria criteria) {
		return examTakenBean.findAll(criteria);
	}
	
	private void validateExamTaken(ExamTaken examTaken) throws ValidationException {
		if (examTaken.getEmployee() == null || examTaken.getEmployee().getId() == null) {
			throw new ValidationException("Invalid employee id.");
	    } else if (examTaken.getExam() == null || examTaken.getExam().getId() == null) {
			throw new ValidationException("Invalid exam id.");
	    } else if (examTaken.getDate() == null) {
	        throw new ValidationException("Invalid date.");
	    }
	}

	@Override
	public List<ExamTaken> findByDateRange(LocalDate startDate, LocalDate endDate) {
		return examTakenBean.findByDateRange(startDate, endDate);
	}

}
