package com.bloff.pharmacy.app.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="medicine")
public class Medicine {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id_medicine")
	private Long id;
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	@Column(name="name_medicine")
	private String medicineName;
	
	@NotNull(message="is required")
	@Size(min=1, message="is required")
	@Column(name="exp_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expDate;
	
	@NotNull(message="is required")
	//@Size(min=0, message="is required")
	@Column(name="quantity")
	private int quantity;
	
	@Column(name="comments")
	private String comment;
	
	@ManyToOne(fetch=FetchType.LAZY, 
			   cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="Producent_id_producent")
	private Producent producent;
	
	@ManyToOne(fetch=FetchType.LAZY, 
			   cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="Type_id_type")
	private Type type;
	
	@ManyToOne(fetch=FetchType.LAZY, 
			   cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="Destiny_id_destiny")
	private Destiny destiny;
	
	@ManyToOne(fetch=FetchType.LAZY, 
			   cascade= {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name="user_id")
	private User user;
	
	public Medicine(Long id, String medicineName, LocalDate expDate, int quantity, String comment) {
		this.id = id;
		this.medicineName = medicineName;
		this.expDate = expDate;
		this.quantity = quantity;
		this.comment = comment;
	}

	public Medicine(String medicineName, LocalDate expDate, int quantity) {
		this.medicineName = medicineName;
		this.expDate = expDate;
		this.quantity = quantity;
	}
	
	public Medicine(String medicineName, LocalDate expDate, int quantity, User user) {
		this.medicineName = medicineName;
		this.expDate = expDate;
		this.quantity = quantity;
		this.user = user;
	}

	public Medicine() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMedicineName() {
		return medicineName;
	}

	public void setMedicineName(String medicineName) {
		this.medicineName = medicineName;
	}

	public LocalDate getExpDate() {
		return expDate;
	}

	public void setExpDate(LocalDate expDate) {
		this.expDate = expDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Producent getProducent() {
		return producent;
	}

	public void setProducent(Producent producent) {
		this.producent = producent;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Destiny getDestiny() {
		return destiny;
	}

	public void setDestiny(Destiny destiny) {
		this.destiny = destiny;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
/*
	@Override
	public String toString() {
		return "Medicine [id=" + id + ", medicineName=" + medicineName + ", expDate=" + expDate + ", quantity="
				+ quantity + ", comment=" + comment + ", producent=" + producent + ", type=" + type + ", destiny="
				+ destiny + "]";
	}
*/
	
	
}
