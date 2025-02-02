package com.venky.socailmedia.socialmedia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venky.socailmedia.socialmedia.entity.Posts;



@Repository
public interface PostRepo extends JpaRepository<Posts, Long>{

	List<Posts> findByStatus(boolean status);

}
