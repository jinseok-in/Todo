import React, { useState, useEffect } from 'react';
import axios from 'axios';

function App() {
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/todos", {
        withCredentials: true, // CORS 해결을 위한 추가 설정
        headers: {
          "Content-Type": "application/json",
        },
      });
      setTodos(response.data);
    } catch (error) {
      console.error("CORS Error:", error);
    }
  };

  return (
    <div>
      <h1>ToDo List</h1>
      <ul>
        {todos.map((todo) => (
          <li key={todo.id}>{todo.title}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;