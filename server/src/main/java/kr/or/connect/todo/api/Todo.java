package kr.or.connect.todo.api;

import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;


@RestController
public class Todo {
    private Integer id;
    private String todo;
    private Integer completed = 0;
    private Timestamp date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", todo='" + todo + '\'' +
                ", completed=" + completed +
                ", date=" + date +
                '}';
    }

    public Todo(){}

    public Todo(String todo, Integer completed, Timestamp date) {
        this.todo = todo;
        this.completed = completed;
        this.date = date;
    }
    
    public Todo(String todo, Timestamp date){
    	this.date = date;
    	this.todo = todo;
    }
}
