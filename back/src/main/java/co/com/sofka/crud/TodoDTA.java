package co.com.sofka.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoDTA {

    @Autowired
    private TodoRepository repository;

    public List<Todo> list(){
        return repository.findAll();
    }

    public Todo save(Todo todo){
        if (validateBasicParameters(todo).equals("")) {
            try {
                validateChar(todo);
                return repository.save(todo);

            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error"+exception);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,validateBasicParameters(todo));
    }

    public void delete(Long id){
        if (id > 0) {
            try {
                repository.delete(get(id));
            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error"+exception);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"The character id only can contains positive numbers");
    }

    public Todo get(Long id){
         return repository.findById(id).orElseThrow();
    }

    private String validateBasicParameters(Todo todo) {
        String response = "";

        if (validateNull(todo.getName())) {
            response = ("The name can't be null");
        }
        if (validateEmpty(todo.getName())) {
            response = ("The name can't be empty");
        }
        return response;
    }

    private boolean validateNull(String date) {
        return date == null;
    }

    private boolean validateEmpty(String date) {
        return date.isEmpty();
    }



    void validateChar(Todo todo) {
        for (int i = 0; i < todo.getName().length(); i++) {
            if(isEspecialCharacter(todo, i, '#') || isEspecialCharacter(todo, i, '@') || isEspecialCharacter(todo, i, '$') || isEspecialCharacter(todo, i, '%')){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error no se permiten caracteres especiales");
            }
        }
    }

    private boolean isEspecialCharacter(Todo todo, int i, char c) {
        return todo.getName().charAt(i) == c;
    }


}
