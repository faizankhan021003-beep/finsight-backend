import { useEffect, useState } from "react";
import { getAllExpenses } from "../services/api";
import { useNavigate } from "react-router-dom";

function ExpenseList() {
    const [expenses, setExpenses] = useState([]);

    useEffect(() => {
    fetchExpenses();
    }, []);
    const navigate = useNavigate();
    const fetchExpenses = async () => {
    try {
    const response = await getAllExpenses();
    console.log(response.data);
    setExpenses(response.data);
    } catch (error) {
    console.log(error);
    }
  };
  return (
    <div style={{ padding: "30px" }}>
      <h2>Expense List</h2>

      <table border="1" cellPadding="10" style={{ width: "100%" }}>
        <thead>
          <tr>
            <th>Title</th>
            <th>Amount</th>
            <th>Category</th>
            <th>Date</th>
            <th>Description</th>
            <th>Action</th>
          </tr>
        </thead>

         <tbody>
        {expenses.map((expense) => (
        <tr key={expense.id}>
        <td>{expense.title}</td>
        <td>₹{expense.amount}</td>
        <td>{expense.category}</td>
        <td>{expense.date}</td>
        <td>{expense.description}</td>
        <button onClick={() => navigate(`/edit-expense/${expense.id}`)}>
             Edit
        </button>
        </tr>
        ))}
        </tbody>
      </table>
    </div>
  );
}

export default ExpenseList;