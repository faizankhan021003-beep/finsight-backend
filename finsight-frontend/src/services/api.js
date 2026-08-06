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

export default api;