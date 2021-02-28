package co.com.sofka.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TodoDTA {

    @Autowired
    private TodoRepository repository;

    public Iterable<TodoDTO> list(){
        return repository.findAll();
    }

    public TodoDTO save(TodoDTO todoDTO){
        if (validateBasicParameters(todoDTO).equals("")) {
            try {
                validateChar(todoDTO);
                return repository.save(todoDTO);

            } catch (Exception exception) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error"+exception);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,validateBasicParameters(todoDTO));
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

    public TodoDTO get(Long id){
         return repository.findById(id).orElseThrow();
    }

    private String validateBasicParameters(TodoDTO todo) {
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



    void validateChar(TodoDTO todo) {
        for (int i = 0; i < todo.getName().length(); i++) {
            if(isEspecialCharacter(todo, i, '#') || isEspecialCharacter(todo, i, '@') || isEspecialCharacter(todo, i, '$') || isEspecialCharacter(todo, i, '%')){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error no se permiten caracteres especiales");
            }
        }
    }

    private boolean isEspecialCharacter(TodoDTO todo, int i, char c) {
        return todo.getName().charAt(i) == c;
    }


}
