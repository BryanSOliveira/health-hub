package br.com.bryan.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.bryan.dao.impl.ExamTakenDAOImpl;
import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class ExamTakenBean {

	@Inject
	private ExamTakenDAOImpl examTakenDAOImpl;
	
	public ExamTaken findById(Long id) {
		return examTakenDAOImpl.findById(id);
	}
	
	public ExamTaken save(ExamTaken examTaken) {
		return examTakenDAOImpl.save(examTaken);
	}
	
	public void update(ExamTaken examTaken) {
		examTakenDAOImpl.update(examTaken);
	}
	
	public void delete(Long id) {
		examTakenDAOImpl.delete(id);
	}
	
	public List<ExamTaken> findAll(SearchCriteria criteria) {
		return examTakenDAOImpl.findAll(criteria);
	}
}
