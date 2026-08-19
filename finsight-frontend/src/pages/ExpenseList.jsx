import { useEffect, useState } from "react";
import { getAllExpenses, deleteExpense } from "../services/api";
import { useNavigate } from "react-router-dom";
import "./ExpenseList.css";

function ExpenseList() {
  const [expenses, setExpenses] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchExpenses();
  }, []);

  const fetchExpenses = async () => {
    try {
      const response = await getAllExpenses();
      console.log(response.data);
      setExpenses(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleDelete = async (id) => {
  const confirmed = window.confirm(
    "Are you sure you want to delete this expense?"
  );

  if (!confirmed) {
    return;
  }

  try {
    await deleteExpense(id);

    alert("Expense deleted successfully!");

    fetchExpenses();
  } catch (error) {
    console.log(error);
    alert("Failed to delete expense.");
  }
};

  return (
    <div className="expense-list-container">
      <h2>Expense List</h2>

      <div className="expense-table-container">
        <table className="expense-table">
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
              
              <td>
               <button
                 className="edit-expense-btn"
                 onClick={() =>
                 navigate(`/edit-expense/${expense.id}`)
                }
               >
                 Edit
               </button>

              <button
                 className="delete-expense-btn"
                 onClick={() => handleDelete(expense.id)}
              >
               Delete
              </button>
               </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default ExpenseList;