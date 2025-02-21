import React, { useState, useEffect } from "react";
import axios from "axios";

function App() {
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    fetchTodos();
  }, []);

  const fetchTodos = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/todos", {
        withCredentials: true,
        headers: {
          "Content-Type": "application/json",
        },
      });
      setTodos(response.data);
    } catch (error) {
      console.error("API 요청 실패:", error);
    }
  };

  return (
    <div>
      <h1>ToDo List</h1>
      <ul>
        {todos.map((todo) => (
          <li key={todo.todo_id}>{todo.title}</li>
        ))}
      </ul>
    </div>
  );
}

export default App;
