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
		List<Article> joinList = artRep.findAll();

		Article art = new Article();
		List<Comment> comList = new ArrayList<>();
		List<Article> artList2 = new ArrayList<>();
		int count = 0;

		for (int i = 0; i < joinList.size(); i++) {
			if (count == 0) {
				art.setId(joinList.get(i).getId());
				art.setName(joinList.get(i).getName());
				art.setContent(joinList.get(i).getContent());
				comList.addAll(joinList.get(i).getCommentList());
				count++;
			} else if (joinList.get(i).getId() == joinList.get(i - 1).getId()) {
				comList.addAll(joinList.get(i).getCommentList());
				art.setCommentList(comList);
			} else {
				art.setCommentList(comList);
				artList2.add(art);
				art = new Article();
				comList = new ArrayList<>();
				art.setId(joinList.get(i).getId());
				art.setName(joinList.get(i).getName());
				art.setContent(joinList.get(i).getContent());
				comList.addAll(joinList.get(i).getCommentList());
				art.setCommentList(comList);
			}
			if(i == joinList.size() - 1) {
				art.setCommentList(comList);
				artList2.add(art);
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
