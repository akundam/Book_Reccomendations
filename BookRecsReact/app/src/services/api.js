import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8080", 
});

API.interceptors.response.use(
  (response) => response,
  (error) => {
    alert(error.response?.data || "An error occurred");
    return Promise.reject(error);
  }
);

export default API;
