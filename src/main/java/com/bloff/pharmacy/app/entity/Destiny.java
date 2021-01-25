package com.bloff.pharmacy.app.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="destiny")
public class Destiny {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_destiny")
	private Long id;
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	@Column(name="name_destiny")	
	private String destinyName;
	
	@OneToMany(fetch= FetchType.LAZY,
			   mappedBy="destiny",
			   cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Medicine> medicines;

	public Destiny(Long id, String destinyName) {
		this.id = id;
		this.destinyName = destinyName;
	}
	
	public Destiny(String destinyName) {
		this.destinyName = destinyName;
	}

	public Destiny() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDestinyName() {
		return destinyName;
	}

	public void setDestinyName(String destinyName) {
		this.destinyName = destinyName;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}
	
	@Override
	public String toString() {
		return "Destiny [id=" + id + ", destinyName=" + destinyName + ", medicines=" + medicines + "]";
	}

	
	
}
