import React, { useContext, useRef, useState } from 'react';
import { Store, HOST_API } from './App';
import { ValidateFunction } from "./ValidateFunction";

export const Form = () => {
  const formRef = useRef(null);
  const { dispatch, state: { todo } } = useContext(Store);
  const item = todo.item;
  const [state, setState] = useState(item);
  const [error, setError] = useState(null);

  const { validName, validadEspecialCharacters } = ValidateFunction(setError, state);


  const onAdd = (event) => {

    event.preventDefault();

    if (!validName()) {
      return;
    };

    if (!validadEspecialCharacters()) {
      return;
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
  };

  const onEdit = (event) => {

    event.preventDefault();

    if (!validName()) {
      return;
    };
    if (!validadEspecialCharacters()) {
      return;
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
  };

  return <form ref={formRef}>
    <div>
      {error && <span>{error}</span>}
    </div>
    <input
      type="text"
      name="name"
      placeholder="¿Qué piensas hacer hoy?"
      defaultValue={item.name}
      onChange={(event) => {
        setState({ ...state, name: event.target.value });
      }} className="imputName"></input>
    {item.id && <button onClick={onEdit} className="bntEdit">Actualizar</button>}
    {!item.id && <button onClick={onAdd} className="bntCreate">Crear</button>}
  </form>;
};
