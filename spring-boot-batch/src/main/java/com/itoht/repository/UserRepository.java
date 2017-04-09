package com.itoht.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itoht.entity.UserEnitity;

@Repository
public interface UserRepository extends JpaRepository<UserEnitity, Long> {

}
