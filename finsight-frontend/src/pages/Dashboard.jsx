import Navbar from "../components/Navbar";
import { useEffect, useState } from "react";
import { getExpenseSummary } from "../services/api";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const email = localStorage.getItem("email");
  const navigate = useNavigate();
  const [summary, setSummary] = useState({
  totalExpense: 0,
  totalCategories: 0,
  monthlyExpense: 0,
  });
  useEffect(() => {
  fetchSummary();
  }, []);
  const [loading, setLoading] = useState(true);
  const fetchSummary = async () => {
  try {
    const response = await getExpenseSummary();
    console.log(response.data);
    setSummary(response.data);
  } catch (error) {
    alert("Failed to load dashboard data.");
    console.log(error);
  } finally {
    setLoading(false);
  }
  };
  if (loading) {
  return <h2 style={{ padding: "30px" }}>Loading Dashboard...</h2>;
  }
  return (
  <>
    <Navbar />

    <div style={{ padding: "30px" }}>
      <h1>FinSight Dashboard</h1>
      <p>Welcome back, <strong>{email}</strong>! 🎉</p>
      <button
  onClick={() => navigate("/add-expense")}
  style={{
    background: "#2563EB",
    color: "white",
    border: "none",
    padding: "10px 20px",
    borderRadius: "5px",
    cursor: "pointer",
    marginTop: "15px",
    marginBottom: "20px",
  }}
  >
   + Add Expense
   </button>
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
    <h2>₹{summary.totalExpenses}</h2>
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
    <h3>Total Transactions</h3>
    <h2>{summary.totalTransactions}</h2>
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