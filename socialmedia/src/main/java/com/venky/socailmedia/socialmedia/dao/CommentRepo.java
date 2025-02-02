package com.venky.socailmedia.socialmedia.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.venky.socailmedia.socialmedia.entity.Comments;

import jakarta.transaction.Transactional;

@Repository
public interface CommentRepo extends JpaRepository<Comments, Long>{

	@Transactional
	List<Comments> findByPosts_PostId(Long post_id);

	Optional<Comments> findByCommentId(Long CommentId);

}
