package com.venky.socailmedia.socialmedia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.venky.socailmedia.socialmedia.dao.CommentRepo;
import com.venky.socailmedia.socialmedia.dao.PostRepo;
import com.venky.socailmedia.socialmedia.dto.CommentDTO;
import com.venky.socailmedia.socialmedia.entity.Comments;
import com.venky.socailmedia.socialmedia.entity.Myuser;
import com.venky.socailmedia.socialmedia.entity.Posts;

@RestController
public class CommentController {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private UserController userController;
	
	@Autowired
	private PostRepo postRepo;
	
	@PostMapping("/AddComment")
	public ResponseEntity<?> addComment(@RequestHeader(value="Authorization",required = false) String token,@RequestBody Comments comments){
		Myuser user=userController.getUserIdFromToken(token);
		System.out.print(comments.toString());
		Optional<Posts> post=postRepo.findById(comments.getPosts().getPostId());
		comments.setUser(user);
		if(post.isPresent()) {
			commentRepo.save(comments);
			return ResponseEntity.ok("Added successfully");
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/getCommentByPostId")
	public List<CommentDTO> getCommentByPostId(@RequestParam Long PostId) {
	    List<Comments> comments = commentRepo.findByPosts_PostId(PostId);
	    List<CommentDTO> commentDto = comments.stream()
	            .map(comment -> {
	                CommentDTO dto = new CommentDTO();
	                dto.setCommentId(comment.getCommentId());
	                dto.setComment(comment.getComment());
	                dto.setLike(comment.getLike());
					dto.setPostId(comment.getPosts().getPostId());
	                dto.setUserId(comment.getUser().getId());
	                return dto;
	            })
	            .collect(Collectors.toList());

	    return commentDto.isEmpty() ? new ArrayList<>() : commentDto; 
	}

	
	@PatchMapping("/UpdateComment")
	public ResponseEntity<?> UpdateComment(@RequestBody Comments comments){
		Optional<Comments> comment=commentRepo.findByCommentId((long) comments.getCommentId());
		
		if(comment.isPresent()) {
			commentRepo.save(comments);
			return ResponseEntity.ok("updated successfully");
		}
		return ResponseEntity.notFound().build();
		
	}
	@DeleteMapping("/DeleteComment")
	public ResponseEntity<?> DeleteComment(@RequestParam Long CommentId){
		Optional<Comments> comment=commentRepo.findByCommentId(CommentId);
		if(comment.isPresent()) {
			commentRepo.delete(comment.get());
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
}
