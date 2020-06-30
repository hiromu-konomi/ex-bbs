package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.service.ArticleService;
import com.example.service.JoinService;

@Controller
@RequestMapping("/bbs")
public class ArticleController {

	@Autowired
	private JoinService service;
	
	@ModelAttribute
	public ArticleForm setArticleForm() {
		return new ArticleForm();
	}
	
	@ModelAttribute
	public CommentForm setCommentForm() {
		return new CommentForm();
	}
	
	@RequestMapping("")
	public String index(Model model) {
		List<Article> artList = service.findAll();
		
		model.addAttribute("artList", artList);
		
		return "bbs";
	}
	
	@RequestMapping("/insert-article")
	public String insertArticle(ArticleForm form, Model model) {
		Article art = new Article();
		
		art.setName(form.getName());
		art.setContent(form.getContent());
		
		service.insertArticle(art);
		
		return index(model);
	}
	
	@RequestMapping("/insert-comment")
	public String insertComment(CommentForm form, Model model) {
		Comment com = new Comment();
		
		com.setArticleId(Integer.parseInt(form.getArticleId()));
		com.setName(form.getName());
		com.setContent(form.getContent());
		
		service.insertComment(com);
		
		return index(model);
	}
	
	@RequestMapping("/delete")
	public String deleteArticle(Integer id, Model model) {
		service.deleteComment(id);
		service.deleteArticle(id);
		
		return index(model);
	}
}
