package br.com.bryan.dao;

import java.util.List;

import br.com.bryan.model.ExamTaken;
import br.com.bryan.model.criteria.SearchCriteria;

public interface ExamTakenDAO {
	ExamTaken findById(Long id);

	ExamTaken save(ExamTaken examTaken);

	void update(ExamTaken examTaken);

	void delete(Long id);

	List<ExamTaken> findAll(SearchCriteria criteria);
}
