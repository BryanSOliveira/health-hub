package br.com.bryan.webservice.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.HandlerChain;
import javax.jws.WebService;

import com.opensymphony.xwork2.validator.ValidationException;

import br.com.bryan.facade.ExamFacade;
import br.com.bryan.model.Exam;
import br.com.bryan.model.criteria.SearchCriteria;
import br.com.bryan.webservice.ExamWebService;

@WebService(endpointInterface = "br.com.bryan.webservice.ExamWebService", serviceName = "soap")
@HandlerChain(file = "/handler-chain.xml")
public class ExamWebServiceImpl implements ExamWebService {

	@EJB
	private ExamFacade examFacade; 
	
	@Override
	public Exam getExam(Long id) {
		return examFacade.findById(id);
	}

	@Override
	public List<Exam> getAllExams(SearchCriteria criteria) {
		return examFacade.findAll(criteria);
	}

	@Override
	public Exam createExam(Exam exam) throws ValidationException {
		return examFacade.save(exam);
	}

	@Override
	public void updateExam(Exam exam) throws ValidationException {
		examFacade.update(exam);
	}

	@Override
	public void deleteExam(Long id) throws ValidationException {
		examFacade.delete(id);
	}

}
