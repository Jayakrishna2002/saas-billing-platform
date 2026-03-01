package com.project.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.modal.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID>{

}
