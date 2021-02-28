package co.com.sofka.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoDTA service;

    @GetMapping(value = "api/todos")
    public Iterable<TodoDTO> list(){
        return service.list();
    }
    
    @PostMapping(value = "api/todo")
    public TodoDTO save(@Validated @RequestBody TodoDTO todoDTO){
        if (validateBasicParameters(todoDTO).equals("")) {
            try {
                return service.save(todoDTO);
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
        throw new RuntimeException(validateBasicParameters(todoDTO));
    }

    @PutMapping(value = "api/todo")
    public TodoDTO update(@Validated @RequestBody TodoDTO todoDTO){
        if (validarParametersUpdate(todoDTO).equals("")) {
            try {
                service.save(todoDTO);
            } catch (Exception exception) {
                throw new RuntimeException(validarParametersUpdate(todoDTO)+exception);
            }
        }
        throw new RuntimeException(validarParametersUpdate(todoDTO));

    }

    @DeleteMapping(value = "api/{id}/todo")
    public void delete(@PathVariable("id")Long id){
        service.delete(id);
    }

    @GetMapping(value = "api/{id}/todo")
    public TodoDTO get(@PathVariable("id") Long id){
        if (id > 0) {
            try {
                service.delete(id);
            } catch (Exception exception) {
                throw new RuntimeException("The character id only can contains positive numbers"+ exception);
            }
        }
        throw new RuntimeException("");
    }

    private String validarParametersUpdate(TodoDTO todo) {
        String response = "";
        response = validId(todo) ? "The id not exist to update" : validateBasicParameters(todo);
        return response;
    }

    private String validateBasicParameters(TodoDTO todo) {
        String response = "";

        if (validateNull(todo.getName())) {
            response = ("The name can't be null");
        }
        if (validateEmpty(todo.getName())) {
            response = ("The name can't be empty");
        }
        if (validateNull(todo.getGroupListId())) {
            response = ("The group list id can't be null");
        }
        if (validateEmpty(todo.getGroupListId())) {
            response = ("The group list id can't be empty");
        }
        return response;
    }

    private boolean validateNull(String date) {
        return date == null;
    }

    private boolean validateEmpty(String date) {
        return date.isEmpty();
    }

    private Boolean validId(TodoDTO todo) {
        return todo.getId() == null;
    }



}
