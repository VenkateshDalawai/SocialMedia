package com.venky.socailmedia.socialmedia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venky.socailmedia.socialmedia.controller.CommentController;
import com.venky.socailmedia.socialmedia.dao.PostRepo;
import com.venky.socailmedia.socialmedia.dto.CommentDTO;
import com.venky.socailmedia.socialmedia.entity.Posts;

@Service
public class PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentController commentController;

	public List<Posts> getAllposts() {
		List<Posts> posts = postRepo.findAll();
		if (!posts.isEmpty()) {
			posts = posts.stream().map(post -> {
				List<CommentDTO> comments = commentController.getCommentByPostId(post.getPostId());
				post.setComments(comments);
				return post;
			}).collect(Collectors.toList());
			return posts;
		}
		return new ArrayList<>();
	}

	public Posts getPostById(int postId) {
		Optional<Posts> post = postRepo.findById((long) postId);
		if (post.isPresent()) {
			List<CommentDTO> comments = commentController.getCommentByPostId(post.get().getPostId());
			post.get().setComments(comments);
			return post.get();
		}
		return null;
	}

}
