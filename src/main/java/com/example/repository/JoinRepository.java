package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Join;

@Repository
public class JoinRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public static final RowMapper<Join> JOIN_ROW_MAPPER = (rs,i) -> {
		Join join = new Join();
		join.setId(rs.getInt("id"));
		join.setName(rs.getString("name"));
		join.setContent(rs.getString("content"));
		join.setCom_id(rs.getInt("com_id"));
		join.setCom_name(rs.getString("com_name"));
		join.setCom_content(rs.getString("com_content"));
		join.setArticle_id(rs.getInt("article_id"));
		return join;
	};
	
	public List<Join> findAll(){
		String sql = "SELECT a.id AS id,a.name AS name,a.content AS content,c.id AS com_id,c.name AS com_name,c.content "
				+ "AS com_content,c.article_id AS article_id FROM articles AS a JOIN comments AS c "
				+ "ON a.id=c.article_id ORDER BY id DESC";
		
		List<Join> joList = template.query(sql, JOIN_ROW_MAPPER);
		
		return joList;
	}
}
