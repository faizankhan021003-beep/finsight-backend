import { useState } from "react";
import "../styles/Login.css";
import api from "../services/api";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const handleLogin = async () => {
  try {
    const response = await api.post("/auth/login", {
      email: email,
      password: password,
    });

    localStorage.setItem("token", response.data);
    alert("Login Successful!");
    navigate("/dashboard");

  } catch (error) {
    console.log(error.response.data);
  }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>

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

      <button onClick={handleLogin}>
        Login
      </button>
       <p>
       Don't have an account? <Link to="/register">Register</Link>
       </p>
    </div>
  );
}

export default Login;