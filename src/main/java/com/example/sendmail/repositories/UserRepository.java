package com.example.sendmail.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sendmail.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
		
}
