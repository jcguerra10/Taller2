package com.taller2.demo.model.prod;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class UserApp {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotBlank(message="Está Vacío")
	private String username;
	@NotBlank(message="Está Vacío")
	private String password;
	@NotNull(message="Está Vacío")
	private UserType type;
}
