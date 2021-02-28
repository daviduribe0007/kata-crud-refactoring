package co.com.sofka.crud;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Todo")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @NotNull
    @NotBlank(message = "Name can't be empty")
    @Size(min = 1, max = 100, message = "The list only can be 1 or 100 characters")
    @Pattern(regexp = "[a-zA-Z0-9@\\s]+", message = "The list only can contain letters and numbers")
    @Column(name = "name", length = 100)
    private String name;
    @Column(name = "complete")
    private boolean completed;
    @Column(name = "GroupListId")
    private String groupListId;

    public String getGroupListId() {
        return groupListId;
    }

    public void setGroupListId(String groupListId) {
        this.groupListId = groupListId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
