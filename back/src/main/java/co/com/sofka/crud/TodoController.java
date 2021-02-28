package co.com.sofka.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        return service.save(todoDTO);
    }

    @PutMapping(value = "api/todo")
    public TodoDTO update(@Validated @RequestBody TodoDTO todoDTO){
        if(todoDTO.getId() != null){
            return service.save(todoDTO);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The id not exist to update");
    }

    @DeleteMapping(value = "api/{id}/todo")
    public void delete(@PathVariable("id")Long id){
        service.delete(id);
    }

    @GetMapping(value = "api/{id}/todo")
    public TodoDTO get(@PathVariable("id") Long id){
        return service.get(id);
    }





}
