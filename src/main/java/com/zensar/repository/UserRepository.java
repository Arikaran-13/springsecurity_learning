package com.zensar.repository;

import com.zensar.entity.User;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     Optional<User>findByEmail(String email);
}
