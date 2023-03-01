import axios from "axios";

const client = axios.create();

client.defaults.baseURL = process.env.REACT_APP_API_BASE_URL;
client.defaults.withCredentials = true;

export const loadToken = () => {
  try {
    const token = localStorage.getItem("token");
    if (token) setToken(token);
  } catch (e) {
    console.log("locaStorage is not working!");
  }
};

export const setToken = (token?: string) =>
  (client.defaults.headers.common["X-AUTH-TOKEN"] = token);

export const deleteToken = () => {
  delete client.defaults.headers.common["X-AUTH-TOKEN"];
  window.localStorage.removeItem("token");
};

export default client;
