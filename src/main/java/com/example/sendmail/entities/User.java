package com.example.sendmail.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user")
public class User  {
	
		@Id
		@GeneratedValue
		private Integer id;
		@Column(name="full_name")
		private String fullName;
		@Column(name="email")
		private String email;
		
}
