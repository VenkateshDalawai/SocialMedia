package com.venky.socailmedia.socialmedia.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venky.socailmedia.socialmedia.entity.Myuser;

@Repository
public interface UserRepo extends JpaRepository<Myuser, Long>{

	Optional<Myuser> findByUserName(String username);

}
