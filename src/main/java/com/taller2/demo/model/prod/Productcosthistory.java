package com.taller2.demo.model.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

/**
 * The persistent class for the productcosthistory database table.
 *
 */
@Entity
@NamedQuery(name = "Productcosthistory.findAll", query = "SELECT p FROM Productcosthistory p")
public class Productcosthistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PRODUCTCOSTHISTORY_PRODUCTCOSTHISTORYID_GENERATOR", allocationSize = 1, sequenceName = "PRODUCTCOSTHISTORY_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTCOSTHISTORY_PRODUCTCOSTHISTORYID_GENERATOR")
	private Integer id;

	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startdate;
	
	@NonNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate enddate;

	private Timestamp modifieddate;

	@NonNull()
	@Min(value = 0)
	private BigDecimal standardcost;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "productid", insertable = false, updatable = false)
	private Product product;

	public Productcosthistory() {
	}

	public LocalDate getEnddate() {
		return this.enddate;
	}

	public Integer getId() {
		return this.id;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public Product getProduct() {
		return this.product;
	}

	public BigDecimal getStandardcost() {
		return this.standardcost;
	}

	public void setEnddate(LocalDate enddate) {
		this.enddate = enddate;
	}
	
	public LocalDate getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setStandardcost(BigDecimal standardcost) {
		this.standardcost = standardcost;
	}

}