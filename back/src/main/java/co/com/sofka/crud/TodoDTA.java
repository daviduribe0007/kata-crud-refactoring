package co.com.sofka.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoDTA {

    @Autowired
    private TodoRepository repository;

    public Iterable<TodoDTO> list(){
        return repository.findAll();
    }

    public TodoDTO save(TodoDTO todoDTO){
        return repository.save(todoDTO);
    }

    public void delete(Long id){
        repository.delete(get(id));
    }

    public TodoDTO get(Long id){
         return repository.findById(id).orElseThrow();
    }

}
