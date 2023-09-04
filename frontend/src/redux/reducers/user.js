// Purpose of reducer: to update the state based on the action that
// is dispatched from the action creator (user.js)

import { createReducer } from "@reduxjs/toolkit";

const initialState = {
  isAuth: false,
  user: {},
  error: null,
  loading: false,
};

export const userReducer = createReducer(initialState, (builder) => {
  builder
    // ============================ Load User ============================
    .addCase("LOAD_USER_REQUEST", (state, action) => {
      state.loading = true;
    })
    .addCase("LOAD_USER_SUCCESS", (state, action) => {
      state.isAuth = true;
      state.user = action.payload;
      state.loading = false;
    })
    .addCase("LOAD_USER_FAIL", (state, action) => {
      state.error = action.payload;
      state.loading = false;
    })

    // ============================ Register ============================
    .addCase("REGISTER_USER_REQUEST", (state, action) => {
      state.loading = true;
    })
    .addCase("REGISTER_USER_SUCCESS", (state, action) => {
      state.success = action.payload;
      state.loading = false;
    })
    .addCase("REGISTER_USER_FAIL", (state, action) => {
      state.error = action.payload;
      state.loading = false;
    })

    // ============================ Login ============================
    .addCase("LOGIN_USER_REQUEST", (state, action) => {
      state.loading = true;
    })
    .addCase("LOGIN_USER_SUCCESS", (state, action) => {
      state.isAuth = true;
      state.user = action.payload;
      state.loading = false;
    })
    .addCase("LOGIN_USER_FAIL", (state, action) => {
      state.error = action.payload;
      state.loading = false;
    });

  // ============================ Logout ============================
  builder.addCase("LOGOUT_USER", (state, action) => {
    state.isAuth = false;
    state.user = {};
    state.error = null;
    state.success = null;
    state.loading = false;
  });
});
