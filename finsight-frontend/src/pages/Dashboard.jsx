import Navbar from "../components/Navbar";
import { useEffect, useState } from "react";
import {
  getExpenseSummary,
  getCategorySummary,
  getMonthlySummary,
} from "../services/api";
import { useNavigate } from "react-router-dom";
import "./Dashboard.css";

import {
  PieChart,
  Pie,
  Cell,
  Tooltip,
  Legend,
  ResponsiveContainer,
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
} from "recharts";

function Dashboard() {
  const email = localStorage.getItem("email");
  const navigate = useNavigate();

  const [summary, setSummary] = useState({
    totalExpense: 0,
    totalCategories: 0,
    monthlyExpense: 0,
  });

  const [categoryData, setCategoryData] = useState([]);
  const [monthlyData, setMonthlyData] = useState([]);
  const [loading, setLoading] = useState(true);

  const COLORS = ["#2563EB", "#16A34A", "#EA580C", "#9333EA"];

  useEffect(() => {
    fetchSummary();
  }, []);

  const fetchSummary = async () => {
    try {
      const response = await getExpenseSummary();

      console.log(response.data);

      const categoryResponse = await getCategorySummary();

      console.log(categoryResponse.data);

      setCategoryData(categoryResponse.data);

      const monthlyResponse = await getMonthlySummary();

      console.log(monthlyResponse.data);

      setMonthlyData(monthlyResponse.data);

      setSummary(response.data);
    } catch (error) {
      alert("Failed to load dashboard data.");
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
  return <h2 className="loading-message">Loading Dashboard...</h2>;
}

  return (
    <>
      <Navbar />

      <div className="dashboard-container">
        <h1>FinSight Dashboard</h1>

        <p>
          Welcome back, <strong>{email}</strong>! 🎉
        </p>

        <button
         className="add-expense-btn"
         onClick={() => navigate("/add-expense")}
         >
         + Add Expense
        </button>

        <button
         className="view-expenses-btn"
         onClick={() => navigate("/expenses")}
         >
         View Expenses
        </button>

        {/* Summary Cards */}
        <div className="summary-cards">

          {/* Total Expenses */}
          <div className="summary-card total-expenses">
            <h3>Total Expenses</h3>
            <h2>₹{summary.totalExpenses}</h2>
          </div>

          {/* Total Transactions */}
          <div className="summary-card total-transactions">
            <h3>Total Transactions</h3>
            <h2>{summary.totalTransactions}</h2>
          </div>

          {/* This Month */}
          <div className="summary-card this-month">
            <h3>This Month</h3>
            <h2>₹0</h2>
          </div>

        </div>

        {/* Charts */}
        <div className="charts-container">

          {/* Category Expense Chart */}
          <div className="chart-card">
            <h2>Expenses by Category</h2>

            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie
                  data={categoryData}
                  dataKey="totalAmount"
                  nameKey="category"
                  cx="50%"
                  cy="50%"
                  outerRadius={120}
                  label
                >
                  {categoryData.map((entry, index) => (
                    <Cell
                      key={`cell-${index}`}
                      fill={COLORS[index % COLORS.length]}
                    />
                  ))}
                </Pie>

                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </div>

          {/* Monthly Expense Chart */}
          <div className="monthly-chart-card">
            <h2>Monthly Expenses</h2>

            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={monthlyData}>
                <CartesianGrid strokeDasharray="3 3" />

                <XAxis dataKey="month" />

                <YAxis />

                <Tooltip />

                <Bar
                  dataKey="totalAmount"
                  fill="#2563EB"
                />
              </BarChart>
            </ResponsiveContainer>
          </div>

        </div>
      </div>
    </>
  );
}

export default Dashboard;