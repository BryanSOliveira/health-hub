package br.com.bryan.model;

import java.time.LocalDate;
import java.util.Objects;

public class ExamTaken {
	private Long id;
	private Employee employee;
	private Exam exam;
	private LocalDate date;
	
	public ExamTaken() {}

	public ExamTaken(Long id, Employee employee, Exam exam, LocalDate date) {
		this.id = id;
		this.employee = employee;
		this.exam = exam;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExamTaken other = (ExamTaken) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "ExamTaken [id=" + id + ", employee=" + employee + ", exam=" + exam + ", date=" + date + "]";
	}
}
