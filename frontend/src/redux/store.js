// purpose of store: to store the state of the application and update
// the state based on the action that is dispatched from the action creator (e.g user.js)

import { configureStore, getDefaultMiddleware, combineReducers } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage";
import logger from "redux-logger";
import { userReducer } from "./reducers/user";

const persistConfig = {
  key: "root",
  storage,
};

const rootReducer = combineReducers({
  userReducer: userReducer,
});

const persistReducers = persistReducer(persistConfig, rootReducer);

const middleware = [
  ...getDefaultMiddleware({
    thunk: true,
    immutableCheck: true,
    serializableCheck: false,
  }),
  logger,
];

const store = configureStore({
  reducer: persistReducers,
  middleware,
});

const persistor = persistStore(store);

export { store, persistor };
