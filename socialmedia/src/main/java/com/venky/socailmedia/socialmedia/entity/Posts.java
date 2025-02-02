package com.venky.socailmedia.socialmedia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.venky.socailmedia.socialmedia.dto.CommentDTO;

@Entity
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	private String postTitle;
	private String postDescription;

	@Lob
	private byte[] imageData;

	private boolean status;

	private LocalDateTime createdOn;

	@Transient
	private List<CommentDTO> comments = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "userId")
	private Myuser user;

	// Getters and Setters
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public Myuser getUser() {
		return user;
	}

	public void setUser(Myuser user) {
		this.user = user;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	@Override
	public String toString() {
		return "Posts [postId=" + postId + ", postTitle=" + postTitle + ", postDescription=" + postDescription
				+ ", imageData=" + Arrays.toString(imageData) + ", status=" + status + ", createdOn=" + createdOn
				+ ", comments=" + comments + ", user=" + user + "]";
	}
}
