package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;
import com.example.domain.Comment;

@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs,i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		
		Comment comment = new Comment();
		comment.setId(rs.getInt("com_id"));
		comment.setName(rs.getString("com_name"));
		comment.setContent(rs.getString("com_content"));
		comment.setArticleId(rs.getInt("article_id"));
		
		List<Comment> comList = new ArrayList<>();
		comList.add(comment);
		article.setCommentList(comList);
		
		return article;
	};
	
	public List<Article> findAll(){
		String sql = "SELECT a.id AS id,a.name AS name,a.content AS content,c.id AS com_id,c.name AS com_name,c.content "
				+ "AS com_content,c.article_id AS article_id FROM articles AS a LEFT OUTER JOIN comments AS c "
				+ "ON a.id=c.article_id ORDER BY id DESC";
		
		List<Article> artList = template.query(sql, ARTICLE_ROW_MAPPER);
		
		return artList;
	}
	
	public void insert(Article art) {
		String sql = "INSERT INTO articles (name, content) VALUES (:name, :content)";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(art);
		
		template.update(sql, param);
	}
	
	public void deleteById(Integer id) {
		String sql = "DELETE FROM articles WHERE id=:id";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		
		template.update(sql, param);
	}
}
