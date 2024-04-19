package br.com.bryan.dao;

import java.util.List;

import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;

public interface ExamDAO {
	Exam findById(Long id);
	
	Exam findByName(String name);

	Exam save(Exam exam);

	void update(Exam exam);

	void delete(Long id);

	List<Exam> findAll(SearchCriteria criteria);
}
