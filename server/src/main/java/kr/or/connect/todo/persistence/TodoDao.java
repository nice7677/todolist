package kr.or.connect.todo.persistence;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.Todo;

import javax.sql.DataSource;
import java.util.*;

@Repository
public class TodoDao {

	RowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;

	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}

	public int countTodos() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(TodoSqls.COUNT, params, Integer.class);
	}

	public Todo selectById(Integer id) {
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		return jdbc.queryForObject(TodoSqls.SELECT_BY_ID, params, rowMapper);
	}

	public Integer insert(Todo todo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return insertAction.executeAndReturnKey(params).intValue();
	}

	public int deleteById(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(TodoSqls.DELETE_BY_ID, params);
	}

	public int update(Todo todo){
		SqlParameterSource params = new BeanPropertySqlParameterSource(todo);
		return jdbc.update(TodoSqls.UPDATE, params);
	}

	public List<Todo> selectAll(){
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_ALL, params, rowMapper);
	}
		
	public int deleteCompleted(){
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.update(TodoSqls.DELETE_COMPLETED, params);
	}

}
