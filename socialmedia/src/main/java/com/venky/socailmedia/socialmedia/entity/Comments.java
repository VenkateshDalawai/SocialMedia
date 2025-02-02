package com.venky.socailmedia.socialmedia.entity;

import org.springframework.context.annotation.ComponentScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@ComponentScan
@Entity
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;
	private String Comment;
	@ManyToOne
	@JoinColumn(name = "UserId", referencedColumnName = "userId")
	private Myuser user;
	@Column(name = "likes")
	private Integer like;
	@ManyToOne
	@JoinColumn(name = "PostId", referencedColumnName = "postId")
	private Posts posts;

	public Comments() {
	}

	public Comments(Long commentId, String comment, Myuser user, Integer like, Posts posts) {
		super();
		this.commentId = commentId;
		this.Comment = comment;
		this.user = user;
		this.like = like;
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "Comments [CommentId=" + commentId + ", Comment=" + Comment + ", user=" + user + ", like=" + like
				+ ", posts=" + posts + "]";
	}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return Comment;
	}

	public void setComment(String comment) {
		this.Comment = comment;
	}

	public Myuser getUser() {
		return user;
	}

	public void setUser(Myuser user) {
		this.user = user;
	}

	public Integer getLike() {
		return like;
	}

	public void setLike(Integer like) {
		this.like = like;
	}

	public Posts getPosts() {
		return posts;
	}

	public void setPosts(Posts posts) {
		this.posts = posts;
	}

}
