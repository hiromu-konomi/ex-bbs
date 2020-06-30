package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Service
@Transactional
public class JoinService {

	@Autowired
	private ArticleRepository artRep;

	@Autowired
	private CommentRepository comRep;

	public List<Article> findAll() {
		List<Article> joinList = artRep.findAll();

		Map<Integer, Article> map = new HashMap<>();

		for (Article a : joinList) {
			map.put(a.getId(), a);
		}

		List<Article> artList = new ArrayList<>(map.values());

		List<Comment> comList = new ArrayList<>();

		for (Article a : joinList) {
			comList.add(a.getCommentList().get(0));
		}

		artList.forEach(art -> art.getCommentList().clear());

		for (Comment comment : comList) {
			for (Article a : artList) {
				if (comment.getArticleId().equals(a.getId())) {
					a.getCommentList().add(comment);
				}
			}
		}

		return artList;
	}

	public void insertArticle(Article art) {
		artRep.insert(art);
	}

	public void insertComment(Comment com) {
		comRep.insert(com);
	}

	public void deleteArticle(Integer id) {
		artRep.deleteById(id);
	}

	public void deleteComment(Integer articleId) {
		comRep.deleteByArticleId(articleId);
	}
}
