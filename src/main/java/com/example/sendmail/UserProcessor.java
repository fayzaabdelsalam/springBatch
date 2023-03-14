package com.example.sendmail;

import org.springframework.batch.item.ItemProcessor;

import com.example.sendmail.entities.User;
import com.example.sendmail.models.UserDetails;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class UserProcessor implements ItemProcessor<UserDetails, User> { 
	
	@Override
	    public User process(UserDetails userDetails) 
	{
		User user = new User();
		user.setFullName(userDetails.getFirstName() + " " + userDetails.getLastName());
		user.setEmail(userDetails.getEmail());
		return user; 
	}
}