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
@Table(name="producent")
public class Producent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producent")
	private Long id;
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	@Column(name="name_producent")
	private String producentName;
	
	@Column(name="user_id")
	private Long userId;

	@Column(name="activ")
	private int activ;
	
	@OneToMany(fetch= FetchType.LAZY,
			   mappedBy="producent",
			   cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Medicine> medicines;
	
	public Producent(String producentName, Long userId, int activ, List<Medicine> medicines) {
		this.producentName = producentName;
		this.userId = userId;
		this.activ = activ;
		this.medicines = medicines;
	}

	public Producent() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProducentName() {
		return producentName;
	}

	public void setProducentName(String producentName) {
		this.producentName = producentName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public int getActiv() {
		return activ;
	}

	public void setActiv(int activ) {
		this.activ = activ;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}
/*
	@Override
	public String toString() {
		return "Producent [id=" + id + ", producentName=" + producentName + ", userId=" + userId + ", activ=" + activ
				+ ", medicines=" + medicines + "]";
	}
	*/
}
