package br.com.bryan.model;

import java.util.Objects;

public class Exam {
	private Long id;
	private String name;
	private Boolean active;
	private String detail1;
	private String detail2;
	
	public Exam() {}

	public Exam(String name, Boolean active, String detail1, String detail2) {
		this.name = name;
		this.active = active;
		this.detail1 = detail1;
		this.detail2 = detail2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDetail1() {
		return detail1;
	}

	public void setDetail1(String detail1) {
		this.detail1 = detail1;
	}

	public String getDetail2() {
		return detail2;
	}

	public void setDetail2(String detail2) {
		this.detail2 = detail2;
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
		Exam other = (Exam) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Exam [id=" + id + ", name=" + name + ", active=" + active + "]";
	}
}
