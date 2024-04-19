package br.com.bryan.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.bryan.dao.impl.ExamDAOImpl;
import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

@Stateless
public class ExamBean {

	@Inject
	private ExamDAOImpl examDAOImpl;
	
	public Exam findById(Long id) {
		return examDAOImpl.findById(id);
	}
	
	public Exam findByName(String name) {
		return examDAOImpl.findByName(name);
	}
	
	public List<Exam> findAll(SearchCriteria criteria) {
		return examDAOImpl.findAll(criteria);
	}
	
	public Exam save(Exam exam) {
		return examDAOImpl.save(exam);
	}
	
	public void update(Exam exam) {
		examDAOImpl.update(exam);
	}
	
	public void delete(Long id) {
		examDAOImpl.delete(id);
	}
}
