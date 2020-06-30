package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleRepository artRep;

	@Autowired
	private CommentRepository comRep;

	public List<Article> findAll() {
		List<Article> artList = artRep.findAll();

		Article art = new Article();
		List<Comment> comList = new ArrayList<>();
		List<Article> artList2 = new ArrayList<>();
		int count = 0;

		for (Article a : artList) {
			if (count == 0) {
				comList.addAll(a.getCommentList());
				art.setId(a.getId());
				art.setName(a.getName());
				art.setContent(a.getContent());
				count++;
			} else if(a.getId() != comList.get(count).getArticleId()) {
				
			}
		}

		return artList2;
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
