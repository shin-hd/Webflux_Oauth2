import axios from "axios";

const client = axios.create();

client.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
client.defaults.withCredentials = true;

export const setToken = () => {
  const token = localStorage.getItem("token");
  client.defaults.headers.common["X-AUTH-TOKEN"] =
    token !== "none" ? token : null;
};

export default client;
