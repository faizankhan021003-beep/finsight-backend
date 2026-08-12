import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import AddExpense from "./pages/AddExpense";
import ExpenseList from "./pages/ExpenseList";
import EditExpense from "./pages/EditExpense";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/dashboard" element={<ProtectedRoute><Dashboard />
       </ProtectedRoute>}/>
      <Route path="/add-expense" element={<ProtectedRoute>
      <AddExpense />
    </ProtectedRoute>}/>
      <Route path="/expenses" element={<ProtectedRoute>
      <ExpenseList />
    </ProtectedRoute>}/>
      <Route path="/edit-expense/:id" element={<ProtectedRoute>
      <EditExpense />
    </ProtectedRoute>}/>
    </Routes>
  );
}

export default App;