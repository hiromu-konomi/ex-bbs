package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

//	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
//		Comment comment = new Comment();
//		comment.setId(rs.getInt("id"));
//		comment.setName(rs.getString("name"));
//		comment.setContent(rs.getString("content"));
//		comment.setArticleId(rs.getInt("article_id"));
//		return comment;
//	};

//	public List<Comment> findByArticleId(Integer articleId) {
//		String sql = "SELECT * FROM comments WHERE article_id = :articleId ORDER BY article_id";
//
//		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
//
//		List<Comment> comList = template.query(sql, param, COMMENT_ROW_MAPPER);
//
//		return comList;
//	}

	public void insert(Comment comment) {
		String sql = "INSERT INTO comments (article_id, name, content) VALUES (:articleId, :name, :content)";

		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", comment.getArticleId())
				.addValue("name", comment.getName()).addValue("content", comment.getContent());

		template.update(sql, param);
	}
	
	public void deleteByArticleId(Integer articleId) {
		String sql = "DELETE FROM comments WHERE article_id=:articleId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		template.update(sql, param);
	}
}
