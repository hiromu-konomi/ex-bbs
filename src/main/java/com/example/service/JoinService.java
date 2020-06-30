package com.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

		Set<Integer> articleIdSet = new HashSet<>();
		Map<Integer, Article> map = new HashMap<>();

		for (Article a : joinList) {
			articleIdSet.add(a.getId());
		}

		for (Article a : joinList) {
			for (Integer id : articleIdSet) {
				if (a.getId().equals(id)) {
					map.put(id, a);
				}
			}
		}

		List<Article> artList = new ArrayList<>(map.values());
		
		
		List<Comment> comList = new ArrayList<>();
		

		for (Article joinart : joinList) {
			comList.add(joinart.getCommentList().get(0));
		}
		
		artList.forEach(art -> art.getCommentList().clear());
		
		
		for (Comment comment : comList) {
			for (Article a : artList) {
				if (comment.getArticleId().equals(a.getId())) {
					a.setCommentList(comList);
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
