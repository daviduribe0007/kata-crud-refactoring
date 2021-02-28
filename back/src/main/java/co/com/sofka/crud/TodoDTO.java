package co.com.sofka.crud;


import lombok.Data;

@Data
public class TodoDTO {
    private  long id;
    private  String  name;
    private  boolean completed;

}
