import React, { useContext, useReducer, useEffect, useRef, useState, createContext } from 'react';
import reducer from './Reducers/reducer';
const HOST_API = "http://localhost:8080/api";
const initialState = {
  todo: { list: [], item: {} }
};
const Store = createContext(initialState)








const Form = () => {
  const formRef = useRef(null);
  const { dispatch, state: { todo } } = useContext(Store);
  const item = todo.item;
  const [state, setState] = useState(item);
  const [error, setError] = useState(null)

  const validName = () =>{
    let isValid =true;
    setError(null)
    if(state.name.length<1 || state.name.length>100 || state.name===null) {
      setError("The character only can contains letters,numbers and spaces , maximum 100 characteres minimun 1")
      isValid = false
    }
    return isValid
  }
  const validadEspecialCharacters = () =>{
    let isValidChar = true;
    setError(null)
    
    for(var i =0; i<state.name.length;i++){
      if(state.name.charAt(i)==='*'){
        setError("The character #, *, $, % is not valid");
        isValidChar = false;
      }
    }
    return isValidChar;
  }


  const onAdd = (event) => {

    event.preventDefault();
    
    if (!validName()){
      return
    };

    if (!validadEspecialCharacters()){
      return
    };

    const request = {
      name: state.name,
      id: null,
      completed: false
    };


    fetch(HOST_API + "/todo", {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then((todo) => {
        dispatch({ type: "add-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });
  }

  const onEdit = (event) => {

    event.preventDefault();
    
    if (!validName()){
      return
    };
    if (!validadEspecialCharacters()){
      return
    };

    const request = {
      name: state.name,
      id: item.id,
      isCompleted: item.isCompleted
    };


    fetch(HOST_API + "/todo", {
      method: "PUT",
      body: JSON.stringify(request),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then((todo) => {
        dispatch({ type: "update-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });
  }

  return <form ref={formRef}>
    <div>
      {
        error && <span>{error}</span>
      }
    </div>
    <input
      type="text"
      name="name"
      placeholder="¿Qué piensas hacer hoy?"
      defaultValue={item.name}
      onChange={(event) => {
        setState({ ...state, name: event.target.value })
      }}  className = "imputName"></input>
    {item.id && <button onClick={onEdit}className="bntEdit">Actualizar</button>}
    {!item.id && <button onClick={onAdd}className="bntCreate">Crear</button>}
  </form>
}


const List = () => {
  const { dispatch, state: { todo } } = useContext(Store);
  const currentList = todo.list;

  useEffect(() => {
    fetch(HOST_API + "/todos")
      .then(response => response.json())
      .then((list) => {
        dispatch({ type: "update-list", list })
      })
  }, [dispatch]);


  const onDelete = (id) => {
    fetch(HOST_API + "/" + id + "/todo", {
      method: "DELETE"
    }).then((list) => {
      dispatch({ type: "delete-item", id })
    })
  };

  const onEdit = (todo) => {
    dispatch({ type: "edit-item", item: todo })
  };

  const onChange = (event, todo) => {
    const request = {
      name: todo.name,
      id: todo.id,
      completed: event.target.checked
    };
    fetch(HOST_API + "/todo", {
      method: "PUT",
      body: JSON.stringify(request),
      headers: {
        'Content-Type': 'application/json'
      }
    })
      .then(response => response.json())
      .then((todo) => {
        dispatch({ type: "update-item", item: todo });
      });
  };

  const decorationDone = {
    textDecoration: 'line-through'
  };
  return <div >
    <table  className= "tableClass">
      <thead>
        <tr>          
          <td >ID</td>
          <td>Tarea</td>
          <td>¿Completado?</td>
        </tr>
      </thead>
      <tbody>
        {currentList.map((todo) => {
          return <tr key={todo.id} style={todo.completed ? decorationDone : {}}>
            <td>{todo.id}</td>
            <td>{todo.name}</td>
            <td><input type="checkbox" defaultChecked={todo.completed} onChange={(event) => onChange(event, todo)}></input></td>
            <td><button onClick={() => onDelete(todo.id)}className="btnDelete">Eliminar</button></td>
            <td><button onClick={() => onEdit(todo)} className="bntEdit">Editar</button></td>
          </tr>
        })}
        
      </tbody>
    </table>
  </div>
}





const StoreProvider = ({ children }) => {
  const [state, dispatch] = useReducer(reducer, initialState);

  return <Store.Provider value={{ state, dispatch }}>
    {children}
  </Store.Provider>

}

function App() {
  return <StoreProvider>
    <h1 className = "H1DashBoard">DahsBoard</h1>
    <h3>To-Do List</h3>
    <Form />
    <br />
    <List />
  </StoreProvider>
}

export default App;
