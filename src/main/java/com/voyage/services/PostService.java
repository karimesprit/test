package com.voyage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.voyage.entities.Post;
import com.voyage.repositories.PostRepository;

@Service
@Transactional
public class PostService {

	@Autowired
	PostRepository postRepository;

	public List<Post> getAllPosts() {
		return postRepository.findAll();
	}

	public Post savePost(Post post) {
		return postRepository.save(post);
	}

	public void delete(Long id) {
		postRepository.deleteById(id);
	}

	public void like(Long id) {
		Post post = postRepository.findById(id).orElse(null);
		if (post != null) {
			post.setLikeCount(post.getLikeCount() + 1);
			savePost(post);
		}
	}

	public void dislike(Long id) {
		Post post = postRepository.findById(id).orElse(null);
		if (post != null) {
			post.setDislikeCount(post.getDislikeCount() + 1);
			savePost(post);
		}
	}
	public Post getByIdPost(Long id) {
		return postRepository.findById(id).orElse(null);
	}

}
