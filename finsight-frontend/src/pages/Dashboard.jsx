function Dashboard() {
  const token = localStorage.getItem("token");

  return (
    <div style={{ padding: "30px" }}>
      <h1>FinSight Dashboard</h1>
      <p>Welcome back! 🎉</p>

      <hr />

      <h3>Login Status</h3>

      <p>
        <strong>JWT Token Stored:</strong>
      </p>

      <textarea
        value={token}
        rows="6"
        cols="80"
        readOnly
      />
    </div>
  );
}

export default Dashboard;