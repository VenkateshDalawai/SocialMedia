package com.venky.socailmedia.socialmedia.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.venky.socailmedia.socialmedia.dao.CommentRepo;
import com.venky.socailmedia.socialmedia.dao.PostRepo;
import com.venky.socailmedia.socialmedia.entity.Comments;
import com.venky.socailmedia.socialmedia.entity.Myuser;
import com.venky.socailmedia.socialmedia.entity.Posts;
import com.venky.socailmedia.socialmedia.service.PostService;

@RestController
public class PostsController {

	@Autowired
	PostRepo postRepo;

	@Autowired
	private UserController userController;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private PostService postService;

	@PostMapping("/createPosts")
	public ResponseEntity<?> createPost(@RequestParam("title") String title,
			@RequestParam("description") String description,
			@RequestParam(value = "image", required = false) MultipartFile imageData,
			@RequestHeader(value="Authorization",required = false) String token) throws IOException {

		// Create a new Posts object
		Posts posts = new Posts();
		posts.setPostTitle(title);
		posts.setPostDescription(description);

		// Handle image data if provided
		if (imageData != null && !imageData.isEmpty()) {
			posts.setImageData(imageData.getBytes());
		}

		posts.setStatus(true);
		posts.setCreatedOn(LocalDateTime.now());
		posts.setUser(userController.getUserIdFromToken(token));

		postRepo.save(posts);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/getData/{postId}")
	public ResponseEntity<?> getPostbyId(@PathVariable("postId") int postId) {
		Posts post = postService.getPostById(postId);
		if (post != null) {
			return ResponseEntity.ok(post);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/getallposts")
	public ResponseEntity<?> getAllPosts() {

		List<Posts> posts = postService.getAllposts();
		if (!posts.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(posts);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data in the Data Base");
	}

	@PatchMapping("/updatePost")
	public ResponseEntity<?> updatePost(@RequestParam("postId") int postId,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "image", required = false) MultipartFile imageData,

			@RequestHeader(value="Authorization",required = false) String token, @RequestParam(value = "like", required = false) int like)
			throws IOException {

		Optional<Posts> checkPosts = postRepo.findById((long) postId);
		if (checkPosts.isPresent()) {
			Posts updatePosts = checkPosts.get();
			if (title != null) {
				updatePosts.setPostTitle(title);
			}
			if (description != null) {
				updatePosts.setPostDescription(description);
			}
			if (imageData != null) {
				updatePosts.setImageData(imageData.getBytes());
			}

			updatePosts.setCreatedOn(LocalDateTime.now());

			postRepo.save(updatePosts);
			return ResponseEntity.ok().body("Updated Successfully");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PutMapping("/deletePost/{postId}")
	public ResponseEntity<?> deletePosts(@PathVariable("postId") int postId) {
		Optional<Posts> checkPosts = postRepo.findById((long) postId);
		if (checkPosts.isPresent()) {
			checkPosts.get().setStatus(false);
			postRepo.save(checkPosts.get());
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post Deleted Successfullt");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post Id Not found");
	}
}
