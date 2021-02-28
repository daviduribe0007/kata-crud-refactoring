import React, { createContext } from 'react';
import { Form } from './Form';
import { List } from './List';
import { StoreProvider } from './StoreProvider';
export const HOST_API = "http://localhost:8080/api";
export const initialState = {
  todo: { list: [], item: {} }
};
export const Store = createContext(initialState)
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
