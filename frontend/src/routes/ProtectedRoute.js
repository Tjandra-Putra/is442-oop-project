import { useDispatch, useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import toast from "react-hot-toast";

const ProtectedRoute = ({ children }) => {
  // toast component
  const notifyError = (message) => toast.error(message, { duration: 5000 });

  const dispatch = useDispatch();

  const { loading, isAuth, error } = useSelector((state) => state.userReducer);

  if (!isAuth && !loading) {
    if (error) {
      notifyError(error);
      dispatch({ type: "ClearErrors" });
    }

    return <Navigate to="/login" replace />;
  }

  if (error === "Login first to access this resource.") {
    dispatch({ type: "ClearErrors" });
  }

  return children;
};

export default ProtectedRoute;
