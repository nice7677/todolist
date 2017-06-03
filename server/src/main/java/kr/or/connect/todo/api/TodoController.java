package kr.or.connect.todo.api;

import kr.or.connect.todo.Todo;
import kr.or.connect.todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;


@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final Logger log = LoggerFactory.getLogger(TodoController.class);
    private final TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    Collection<Todo> readList(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    Todo read(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Todo create(@RequestBody Todo todo) {
        Todo newTodo = service.create(todo);
        log.info("Todo created : {}" , newTodo);
        return todo;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void update(@PathVariable Integer id, @RequestBody Todo todo) {
        todo.setId(id);
        service.update(todo);
        log.info("Todo update : {}", id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Integer id) {
        service.delete(id);
        log.info("Todo delete : {}", id);
    }
}
