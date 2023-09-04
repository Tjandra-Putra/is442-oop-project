// purpose of actions: to make API calls to backend and update the redux store
// based on the response from the backend (success or fail)

import axios from "axios";

// ============================ Load User ============================
export const loadUser = () => async (dispatch) => {
  try {
    dispatch({ type: "LOAD_USER_REQUEST" });

    const res = await axios.get(`${server}/api/auth`, {
      withCredentials: true,
    });

    dispatch({ type: "LOAD_USER_SUCCESS", payload: res.data });
  } catch (error) {
    dispatch({ type: "LOAD_USER_FAIL", payload: error.response.data.message });
  }
};

// ============================ Register ============================
export const register = (formData) => async (dispatch) => {
  try {
    dispatch({ type: "REGISTER_USER_REQUEST" });

    const res = await axios.post(`${server}/api/auth/register`, formData, {
      withCredentials: true,
    });

    dispatch({ type: "REGISTER_USER_SUCCESS", payload: res.data });
  } catch (error) {
    dispatch({ type: "REGISTER_USER_FAIL", payload: error.response.data.message });
  }
};

// ============================ Login ============================
export const login = (formData) => async (dispatch) => {
  try {
    dispatch({ type: "LOGIN_USER_REQUEST" });

    const res = await axios.post(`${server}/api/auth/login`, formData, {
      withCredentials: true,
    });

    dispatch({ type: "LOGIN_USER_SUCCESS", payload: res.data });
  } catch (error) {
    dispatch({ type: "LOGIN_USER_FAIL", payload: error.response.data.message });
  }
};

// ============================ Logout ============================
export const logout = () => async (dispatch) => {
  try {
    await axios.get(`${server}/api/auth/logout`, {
      withCredentials: true,
    });

    dispatch({ type: "LOGOUT_USER" });
  } catch (error) {
    dispatch({ type: "LOGOUT_USER_FAIL", payload: error.response.data.message });
  }
};

// ============================ Clear Errors ============================
export const clearErrors = () => async (dispatch) => {
  dispatch({ type: "CLEAR_ERRORS" });
};
