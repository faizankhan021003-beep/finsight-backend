import { useState } from "react";
import "../styles/Login.css";
import api from "../services/api";

function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleRegister = async () => {
  try {
    await api.post("/auth/register", {
      name,
      email,
      password,
    });

    alert("Registration Successful!");

  } catch (error) {
    alert(error.response?.data?.message || "Registration Failed");
  }
  };

  return (
    <div className="login-container">
      <h2>Register</h2>

      <input
        type="text"
        placeholder="Enter Name"
        value={name}
        onChange={(e) => setName(e.target.value)}
      />

      <input
        type="email"
        placeholder="Enter Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
      />

      <input
        type="password"
        placeholder="Enter Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={handleRegister}>
         Register
      </button>
    </div>
  );
}

export default Register;