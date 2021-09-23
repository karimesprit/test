package com.voyage.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voyage.entities.Post;
import com.voyage.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;

	@PostMapping("/post-emps")
	public ResponseEntity<Post> createPost(@RequestBody Post post) throws URISyntaxException {
		post.setDateCreation(LocalDateTime.now());
		post.setLikeCount(0);
		post.setDislikeCount(0);
		Post result = postService.savePost(post);
		return ResponseEntity.created(new URI("/api/post-emps/" + result.getId())).body(result);
	}

	@PutMapping("/post-emps")
	public ResponseEntity<Post> updatePost(@RequestBody Post postEmp) throws URISyntaxException {
		Post result = postService.savePost(postEmp);
		return ResponseEntity.created(new URI("/api/post-emps/" + result.getId())).body(result);
	}

	@GetMapping("/post-emps")
	public ResponseEntity<List<Post>> getAllPostEmps() {
		return ResponseEntity.ok().body(postService.getAllPosts());
	}

	@DeleteMapping("/post-emps/{id}")
	public ResponseEntity<Void> deletePostEmp(@PathVariable Long id) {
		postService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/post-emps/like")
	public ResponseEntity<Void> likePost(@RequestBody Post postEmp) throws URISyntaxException {
		postService.like(postEmp.getId());
		return ResponseEntity.created(new URI("/api/post-emps/")).build();
	}

	@PostMapping("/post-emps/dislike")
	public ResponseEntity<Void> dislikePost(@RequestBody Post postEmp) throws URISyntaxException {
		postService.dislike(postEmp.getId());
		return ResponseEntity.created(new URI("/api/post-emps/")).build();
	}
	
	@GetMapping("/post-emps/{id}")
	public Post getPost(@PathVariable Long id) {
		
		Post thePost=postService.getByIdPost(id); 
		return thePost;
	}
	@GetMapping("/post-somme/{id}")
	public Integer somme(@PathVariable Long id) {
		Post thePost=postService.getByIdPost(id); 
		Integer like=thePost.getLikeCount();
		Integer dislike= thePost.getDislikeCount();
		Integer x= like - dislike;
		return x;
	}
	@GetMapping("/post-etoile/{id}")
	public String etoile(@PathVariable Long id) {
		Integer x=somme(id);
		String y = null;
		if (x<100) {
			y="*";
		}
		else if(x>=100 & x<200){
			y="**";
		}else if(x>=200 & x<300){
			y="***";
		}
		else if(x>=300 & x<400){
			y="****";
		}
		else if(x>=500){
			y="*****";
		}
		return y;

		
		
		
	}
}
