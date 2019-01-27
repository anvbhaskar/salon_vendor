package com.saloon.vendor.datasource.entities;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "tbl_vendor")
@Configuration
public class TblVendor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEN_VendorID", unique = true)
	private Integer venVendorId;
	
	@Column(name = "VEN_VendorName", length = 255)
	private String venVendorName;

	@Column(name = "VEN_VendorDesc", length = 255)
	private String venVendorDesc;

	@Column(name = "VEN_CustomMessage", length = 255)
	private String venCustomMessage;
	
	@Column(name = "VEN_Status", length = 9)
	private String venStatus;
	
	@Column(name = "VEN_CreatedDateTime", columnDefinition = "DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar venCreatedDateTime;
}
