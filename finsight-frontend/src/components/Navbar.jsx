import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    alert("Logged out successfully!");
    navigate("/login");
  };

  return (
    <nav
      style={{
        backgroundColor: "#2563eb",
        color: "white",
        padding: "15px 30px",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
      }}
    >
      <h2>FinSight</h2>

      <button
        onClick={handleLogout}
        style={{
          backgroundColor: "white",
          color: "#2563eb",
          border: "none",
          padding: "8px 16px",
          cursor: "pointer",
          borderRadius: "5px",
        }}
      >
        Logout
      </button>
    </nav>
  );
}

export default Navbar;