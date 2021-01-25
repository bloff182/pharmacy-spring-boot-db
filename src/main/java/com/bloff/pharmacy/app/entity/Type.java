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
@Table(name="type")
public class Type {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_type")
	private Long id;
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	@Column(name="name_type",  nullable = false)	
	private String typeName;
	
	@OneToMany(fetch = FetchType.LAZY,
			   mappedBy = "type",
			   cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Medicine> medicines;

	public Type(String typeName) {
		this.typeName = typeName;
	}
	
	public Type(Long id, String typeName) {
		this.id = id;
		this.typeName = typeName;
	}

	public Type() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	@Override
	public String toString() {
		return "Type [id=" + id + ", typeName=" + typeName + ", medicines=" + medicines + "]";
	}

	
	
	
	
}
