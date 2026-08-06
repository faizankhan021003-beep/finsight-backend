import Navbar from "../components/Navbar";

function Dashboard() {
  const token = localStorage.getItem("token");
  const email = localStorage.getItem("email");

  return (
  <>
    <Navbar />

    <div style={{ padding: "30px" }}>
      <h1>FinSight Dashboard</h1>
      <p>Welcome back, <strong>{email}</strong>! 🎉</p>
     <div
  style={{
    display: "flex",
    gap: "20px",
    marginTop: "30px",
  }}
>
  <div
    style={{
      background: "#334155",
      color: "white",
      padding: "20px",
      borderRadius: "10px",
      width: "200px",
      textAlign: "center",
    }}
  >
    <h3>Total Expenses</h3>
    <h2>₹0</h2>
  </div>

  <div
    style={{
      background: "#16a34a",
      color: "white",
      padding: "20px",
      borderRadius: "10px",
      width: "200px",
      textAlign: "center",
    }}
  >
    <h3>Categories</h3>
    <h2>0</h2>
  </div>

  <div
    style={{
      background: "#ea580c",
      color: "white",
      padding: "20px",
      borderRadius: "10px",
      width: "200px",
      textAlign: "center",
    }}
  >
    <h3>This Month</h3>
    <h2>₹0</h2>
  </div>
</div>
    </div>
  </>
);
}

export default Dashboard;