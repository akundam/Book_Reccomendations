import React, { useState } from "react";
import API from "../services/api";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
    try {
      const response = await API.post("/auth/register", { username, password });
      alert(response.data); 
    } catch (error) {
      
    }
  };

  return (
    <div className="card">
      <h2>Register</h2>
      <input
        type="text"
        placeholder="Create Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />
      <input
        type="password"
        placeholder="Create Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <button onClick={handleRegister}>Register</button>
    </div>
  );
};

export default Register;
