import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080/api",
});

export const getExpenseSummary = () => {
  return api.get("/expenses/summary", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
};

export const addExpense = (expense) => {
  return api.post("/expenses", expense, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
};

export const getAllExpenses = () => {
  return api.get("/expenses", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
};

export const getExpenseById = (id) => {
  return api.get(`/expenses/${id}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
};

export const updateExpense = (id, expense) => {
  return api.put(`/expenses/${id}`, expense, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("token")}`,
    },
  });
};

export default api;