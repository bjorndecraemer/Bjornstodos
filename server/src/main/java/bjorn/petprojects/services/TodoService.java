package bjorn.petprojects.services;

import bjorn.petprojects.api.v1.model.TodoDTO;

import java.io.IOException;
import java.util.List;

public interface TodoService {
    List<TodoDTO> findAllTodos();
    List<TodoDTO> findCompleteTodos();
    List<TodoDTO> findInCompleteTodos();
    TodoDTO findById(Long id);
    TodoDTO createNewTodo(TodoDTO todoDTO) throws IOException;
}