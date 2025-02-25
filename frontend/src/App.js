import React, { useState, useEffect } from "react";
import axios from "axios";

function App({ todo, updateTodo, updateIsChecked, deleteTodo }) {
  const [todos, setTodos] = useState([]);
  const [newTodos, setNewTodos] = useState(null);
  const [time, setTime] = useState(new Date());

  const [content, setContent] = useState("");
  const [description, setDescription] = useState("");
  const [deadline, setDeadline] = useState("");

  const [editId, setEditId] = useState(null);
  const [editContent, setEditContent] = useState("");
  const [editDescription, setEditDescription] = useState("");
  const [editDeadline, setEditDeadline] = useState("");

  useEffect(() => {
    fetchTodos();
  }, []);

  // 
  useEffect(() => {
    const interval = setInterval(() => {
      setTime(new Date());
    }, 10000);
    
    return () => clearInterval(interval);
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
  
  // 추가 버튼
  const handleAddTodo = () => {
    setNewTodos({
      content : "",
      description : "",
      deadline : null,
    });
  };

  const handleSave = async () => {
    if (!content.trim()) {
      alert("할 일은 필수 항목입니다.");
      return;
    } else if (!deadline) {
      alert("마감 시간은 필수 항목입니다.");
      return;
    } else if (new Date(deadline) < time) {
      alert("마감 기한은 현재 날짜보다 이후로만 설정할 수 있습니다.");
      return;
    }
    const deadlineRepl = null;
    try {
      await axios.post("http://localhost:8080/api/todos/save", {
        content,
        description,
        isChecked : false,
        deadline: deadline.replace("T", " "),
      });
      setNewTodos(null);
      setContent("");
      setDescription("");
      setDeadline("");
      fetchTodos();
    } catch (error) {
      console.error(error);
    }
  };

  // 수정 버튼
  const handleEdit = (todo) => {
    setEditId(todo.todoId);
    setEditContent(todo.content);
    setEditDescription(todo.description);
    setEditDeadline(todo.deadline ? todo.deadline.replace(" ", "T") : "");
  };

  // 수정 완료 버튼
  const handleEditSave = async (id) => {
    if (!editContent.trim()) {
      alert("할 일은 필수 항목입니다.");
      return;
    } else if (!editDeadline) {
      alert("마감 시간은 필수 항목입니다.");
      return;
    } else if (new Date(editDeadline) < time) {
      alert("마감 기한은 현재 날짜보다 이후로만 설정할 수 있습니다.");
      return;
    }

    try {
      await axios.put(`http://localhost:8080/api/todos/update/${id}`, {
        content : editContent,
        description : editDescription,
        deadline: editDeadline.replace("T", " "),
      });

      setEditId(null);
      fetchTodos();
    } catch (error) {
      console.error("수정 실패 : ", error);
    }
  };

  // 수정 취소 버튼
  const handleEditCancel = () => {
    setEditId("");
    fetchTodos();
  }

  const handleDelete = async (id) => {
    if(window.confirm("삭제하시겠습니까?")) {
      try {
        await axios.get(`http://localhost:8080/api/todos/delete/${id}`);
        fetchTodos();
      } catch (error) {
        console.error("수정 취소 실패 : ", error);
      }
    }
  }

  const handleToggleCheck = async (id, isChecked) => {
    try {
      await axios.patch(`http://localhost:8080/api/todos/check/${id}`, {
        isChecked : !isChecked,
      });
      fetchTodos();
    } catch (error) {
      console.error("완료 여부 변경 실패 : ", error);
    }
  }

  return (
    <div>
      <h1>TODO List</h1>
      <table border="1" fontWeight="bold">
        <thead>
          <tr>
            <th>번호</th>
            <th>할 일</th>
            <th>설명</th>
            <th>생성 시간</th>
            <th>마감 기한</th>
            <th>완료 여부</th>
          </tr>
        </thead>
        <tbody>
          {todos.map((todo, index) => (
          <tr key={todo.todoId} style={{
                  backgroundColor : todo.isChecked ? "skyblue" : 
                                    new Date(todo.deadline.replace(" ", "T")) > time ? "transparent" : 
                                    "pink", 
                  textAlign : "center"}}>
            {editId === todo.todoId ? (
              <>
                <td>{index+1}</td>
                <td><input type="text" placeholder={todo.content} value={editContent} onChange={(e) => setEditContent(e.target.value)} /></td>
                <td><input type="text" placeholder={todo.description} value={editDescription} onChange={(e) => setEditDescription(e.target.value)} /></td>
                <td>{todo.createTime}</td>
                <td>
                <input type="datetime-local" value={editDeadline} onChange={(e) => setEditDeadline(e.target.value)} />
                </td>
                <td>{todo.isChecked ? "완료" :
                                      new Date(todo.deadline.replace(" ", "T")) < time ? "기간 만료" : 
                                      "미완료"}</td>
                <td><button onClick={() => handleEditSave(todo.todoId)}>저장</button></td>
                <td><button onClick={() => handleEditCancel()}>취소</button></td>
              </>
            ) : (
              <>
                <td>{index+1}</td>
                <td>{todo.content}</td>
                <td>{todo.description}</td>
                <td>{todo.createTime}</td>
                <td>{todo.deadline}</td>
                <td>
                  {todo.isChecked ? (
                    <button onClick={() => handleToggleCheck(todo.todoId, todo.isChecked)}>완료</button>) :
                                    new Date(todo.deadline.replace(" ", "T")) < time ? "기간 만료" :
                    <button onClick={() => handleToggleCheck(todo.todoId, todo.isChecked)}>미완료</button>}
                </td>
                <td><button onClick={() => handleEdit(todo)}>수정</button></td>
                <td><button onClick={() => handleDelete(todo.todoId)}>삭제</button></td>
              </>
            )}
          </tr>
          ))}
        </tbody>
      </table>
      <button onClick={handleAddTodo}>추가하기</button>
      {newTodos && (
        <table border="1">
          <thead>
            <tr>
              <th>할 일</th>
              <th>설명</th>
              <th>마감 기한</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td><input type="text" placeholder={newTodos.content} value={content} onChange={(e) => setContent(e.target.value)} /></td>
              <td><input type="text" placeholder={newTodos.description} value={description} onChange={(e) => setDescription(e.target.value)} /></td>
              <td>
                <input type="datetime-local" value={deadline} onChange={(e) => setDeadline(e.target.value)} />
              </td>
              <td><button onClick={() => handleSave(newTodos.todoId)}>저장</button></td>
              <td><button onClick={() => setNewTodos(null)}>취소</button></td>
            </tr>
      </tbody>
    </table>
    )}
    </div>
  );
}

export default App;
