package kr.or.connect.todo.service;

import kr.or.connect.todo.Todo;
import kr.or.connect.todo.persistence.TodoDao;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public class TodoService {

    private TodoDao dao;

    public TodoService(TodoDao dao) {
        this.dao = dao;
    }
    public Todo findById(Integer id) {
        return dao.selectById(id);
    }

    public Collection<Todo> findAll(){
        return dao.selectAll();
    }

    public Todo create(Todo todo) {
        Integer id = dao.insert(todo);
        todo.setId(id);
        return todo;
    }

    public boolean update(Todo todo) {
        int old = dao.update(todo);
        return old == 1;
    }

    public boolean delete(Integer id) {
        int old = dao.deleteById(id);
        return old == 1;
    }
    
    public int delcompleted(){
    	return dao.deleteCompleted();
    }
}
