package com.example.sendmail.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

	private String firstName;
	private String lastName;
	private String email;
}
