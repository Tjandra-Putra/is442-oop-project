import * as React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import axios from "axios";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";

import { useNavigate } from "react-router-dom";
import { login } from "../../redux/actions/user";

import styles from "./Login.module.css";

const defaultTheme = createTheme();

const Login = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

  React.useEffect(() => {
    // check error
    if (error) {
      console.log(error);
      dispatch({ type: "CLEAR_ERRORS" });
    }
  }, [error]);

  const handleSubmit = (event) => {
    event.preventDefault();

    // Access form fields using event.target
    const email = event.target.email.value;
    const password = event.target.password.value;

    // Create a postData object with the form data
    const postData = {
      email: email,
      password: password,
    };

    // Example: Send the data to your server
    dispatch(login(postData));
  };

  return (
    <div className={styles.loginWrapper}>
      <ThemeProvider theme={defaultTheme}>
        <Container component="main" maxWidth="xs">
          <CssBaseline />
          <Box
            sx={{
              display: "flex",
              flexDirection: "column",
              alignItems: "center",
            }}
          >
            <div className={styles.iconWrapper}>
              <i class="fa-solid fa-key"></i>{" "}
            </div>
            <Typography component="h1" variant="h5">
              Login to your account
            </Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="email"
                label="Email Address"
                name="email"
                autoComplete="email"
                autoFocus
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
              />
              <FormControlLabel control={<Checkbox value="remember" color="primary" />} label="Remember me" />
              <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Sign In
              </Button>
              <Grid container>
                <Grid item xs>
                  <Link
                    to="/forget-password"
                    style={{ cursor: "pointer", color: "#2e82d6", fontSize: "14px", textDecoration: "none" }}
                  >
                    Forgot password?
                  </Link>
                </Grid>
                <Grid item>
                  <Link
                    to="/register"
                    style={{ cursor: "pointer", color: "#2e82d6", fontSize: "14px", textDecoration: "none" }}
                  >
                    {" "}
                    {"Don't have an account? Sign Up"}
                  </Link>
                </Grid>
              </Grid>
            </Box>
          </Box>
        </Container>
      </ThemeProvider>
    </div>
  );
};

export default Login;
