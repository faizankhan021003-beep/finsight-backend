import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getExpenseById, updateExpense } from "../services/api";
import "./EditExpense.css";

function EditExpense() {
  const { id } = useParams();

  const [expense, setExpense] = useState({
    title: "",
    amount: "",
    category: "",
    date: "",
    description: "",
  });

  useEffect(() => {
    fetchExpense();
  }, []);

  const fetchExpense = async () => {
    try {
      const response = await getExpenseById(id);
      setExpense(response.data);
      console.log(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleChange = (e) => {
    setExpense({
      ...expense,
      [e.target.name]: e.target.value,
    });
  };

  const handleUpdate = async (e) => {
    e.preventDefault();

    try {
      const response = await updateExpense(id, expense);
      alert(response.data.message);
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="edit-container">
      <div className="edit-card">
        <h2>Edit Expense</h2>

        <form onSubmit={handleUpdate} className="edit-form">
          <input
            type="text"
            name="title"
            value={expense.title}
            onChange={handleChange}
            placeholder="Title"
          />

          <input
            type="number"
            name="amount"
            value={expense.amount}
            onChange={handleChange}
            placeholder="Amount"
          />

          <input
            type="text"
            name="category"
            value={expense.category}
            onChange={handleChange}
            placeholder="Category"
          />

          <input
            type="date"
            name="date"
            value={expense.date}
            onChange={handleChange}
          />

          <textarea
            name="description"
            value={expense.description}
            onChange={handleChange}
            rows="4"
            placeholder="Description"
          />

          <button type="submit" className="update-btn">
            Update Expense
          </button>
        </form>
      </div>
    </div>
  );
}

export default EditExpense;