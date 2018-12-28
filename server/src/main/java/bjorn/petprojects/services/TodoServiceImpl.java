package bjorn.petprojects.services;

import bjorn.petprojects.api.v1.mappers.TodoMapper;
import bjorn.petprojects.api.v1.model.TodoDTO;
import bjorn.petprojects.domain.Todo;
import bjorn.petprojects.repositories.TodoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
        this.todoMapper = TodoMapper.INSTANCE;
    }

    @Override
    public List<TodoDTO> findAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(todoMapper::todoToTodoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TodoDTO findById(Long id) {
        Optional<Todo> foundTodoOptional = todoRepository.findById(id);
        return foundTodoOptional.map(todoMapper::todoToTodoDTO).orElse(null);
    }

    @Override
    public TodoDTO createNewTodo(TodoDTO todoDTO) {
        return todoMapper.todoToTodoDTO(todoRepository.save(todoMapper.todoDTOToTodo(todoDTO)));
    }

    @Override
    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    private TodoDTO readJSONWithObjectMapper(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        TodoDTO todo = mapper.readValue(json, TodoDTO.class);
        return todo;
    }

    @Override
    public List<TodoDTO> findCompleteTodos() {
        return todoRepository.findAll()
                .stream()
                .filter(todo ->
                    (todo.getCompleted() != null && todo.getCompleted() == true)
                )
                .map(todoMapper::todoToTodoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TodoDTO> findInCompleteTodos() {
        return todoRepository.findAll()
                .stream()
                .filter(todo -> !(todo.getCompleted() != null && todo.getCompleted() == true))
                .map(todoMapper::todoToTodoDTO)
                .collect(Collectors.toList());
    }
}
