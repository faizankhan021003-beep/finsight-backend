import { useState } from "react";
import "../styles/AddExpense.css";
import { addExpense } from "../services/api";
import { useNavigate } from "react-router-dom";

function AddExpense() {
  const [title, setTitle] = useState("");
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("");
  const [date, setDate] = useState("");
  const [description, setDescription] = useState("");
  const navigate = useNavigate();
  const handleSubmit = async () => {
  try {
    const expense = {
      title,
      amount,
      category,
      date,
      description,
    };
    
    await addExpense(expense);
    alert("Expense Added Successfully!");

    setTitle("");
    setAmount("");
    setCategory("");
    setDate("");
    setDescription("");
    navigate("/dashboard");
  } catch (error) {
    alert("Failed to add expense.");
    console.log(error);
  }
  };

  return (
    <div className="add-expense-container">
      <h2>Add Expense</h2>

      <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />


      <input
        type="number"
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
      />

      <select
  value={category}
  onChange={(e) => setCategory(e.target.value)}
>
  <option value="">Select Category</option>
  <option value="Food">Food</option>
  <option value="Transport">Transport</option>
  <option value="Shopping">Shopping</option>
  <option value="Entertainment">Entertainment</option>
  <option value="Bills">Bills</option>
  <option value="Health">Health</option>
  <option value="Education">Education</option>
  <option value="Fuel">Fuel</option>
  <option value="Other">Other</option>
  </select>


      <input
        type="date"
        value={date}
        onChange={(e) => setDate(e.target.value)}
      />

      <textarea
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
       
       <button onClick={handleSubmit}>
       Add Expense
       </button>
    </div>
  );
}

export default AddExpense;