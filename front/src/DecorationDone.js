import React from 'react';


export function DecorationDone(currentList, onChange, onDelete, onEdit) {
  const decorationDone = {
    textDecoration: 'line-through'
  };
  return <div>
    <table className="tableClass">
      <thead>
        <tr>
          <td>ID</td>
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
            <td><button onClick={() => onDelete(todo.id)} className="btnDelete">Eliminar</button></td>
            <td><button onClick={() => onEdit(todo)} className="bntEdit">Editar</button></td>
          </tr>;
        })}

      </tbody>
    </table>
  </div>;
}
