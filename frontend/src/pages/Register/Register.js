import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import { Link } from "react-router-dom";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import axios from "axios";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import toast from "react-hot-toast";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";

import { DEVELOPMENT_SERVER_PATH } from "../../server";

import styles from "./Register.module.css";

const defaultTheme = createTheme();

const Register = () => {
  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

  const notifyError = (message) => toast.error(message, { duration: 5000 });
  const notifySuccess = (message) => toast.success(message, { duration: 5000 });
  const navigate = useNavigate();
  const [errorListMessage, setErrorListMessage] = React.useState([]);

  const isPasswordValid = (password) => {
    const errors = [];

    if (password.length < 8) {
      errors.push("Password is too short (minimum 8 characters).");
    }
    if (password.length > 25) {
      errors.push("Password is too long (maximum 25 characters).");
    }
    if (!/[A-Z]/.test(password)) {
      errors.push("Password must contain at least one uppercase letter.");
    }
    if (!/[a-z]/.test(password)) {
      errors.push("Password must contain at least one lowercase letter.");
    }
    if (!/\d/.test(password)) {
      errors.push("Password must contain at least one digit.");
    }
    if (!/[!@#$%^&*]/.test(password)) {
      errors.push("Password must contain at least one symbol.");
    }

    return errors;
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    const email = data.get("email");
    const password = data.get("password");
    const username = data.get("firstName") + " " + data.get("lastName");

    // console.log({
    //   email: data.get("email"),
    //   password: data.get("password"),
    // });

    // Validation
    if (email === "" || password === "" || data.get("firstName") === "" || data.get("lastName") === "") {
      notifyError("Fields cannot be empty");
      return;
    }

    const passwordErrors = isPasswordValid(password);
    setErrorListMessage(passwordErrors);

    if (passwordErrors.length > 0) {
      notifyError("Password does not meet the required restrictions:");
      return;
    }

    // Rest of your code for handling form submission
    const postData = {
      data: [
        {
          fieldName: "email",
          value: email,
        },
        {
          fieldName: "password",
          value: password,
        },
        {
          fieldName: "username",
          value: username,
        },
      ],
    };

    console.log("====================================");
    console.log(postData);

    axios
      .post(`${DEVELOPMENT_SERVER_PATH}/rest/auth/addUser`, postData)
      .then((res) => {
        console.log(res.data.data);
        notifySuccess("User registered successfully");
        navigate("/login");
      })
      .catch((err) => {
        notifyError("Unable to register user");
        console.log(err);
      });
  };

  return (
    <div className={styles.registerWrapper}>
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
              <i class="fa-solid fa-user"></i>
            </div>
            <Typography component="h1" variant="h5">
              Register an account
            </Typography>
            <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 3 }}>
              <Grid container spacing={2}>
                <Grid item xs={12} sm={6}>
                  <TextField
                    autoComplete="given-name"
                    name="firstName"
                    required
                    fullWidth
                    id="firstName"
                    label="First Name"
                    autoFocus
                  />
                </Grid>
                <Grid item xs={12} sm={6}>
                  <TextField
                    required
                    fullWidth
                    id="lastName"
                    label="Last Name"
                    name="lastName"
                    autoComplete="family-name"
                  />
                </Grid>
                <Grid item xs={12}>
                  <TextField required fullWidth id="email" label="Email Address" name="email" autoComplete="email" />
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="new-password"
                  />
                </Grid>
                <Grid item xs={12}>
                  <FormControlLabel
                    control={<Checkbox value="allowExtraEmails" color="primary" />}
                    label="I agree to the terms and conditions"
                  />
                </Grid>
              </Grid>

              <ul>
                {errorListMessage?.length > 0
                  ? errorListMessage.map((error, index) => (
                      <li key={index} style={{ color: "red" }}>
                        {error}
                      </li>
                    ))
                  : null}
              </ul>

              <Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }}>
                Sign Up
              </Button>
              <Grid container justifyContent="flex-end">
                <Grid item>
                  <Link
                    to="/login"
                    style={{ cursor: "pointer", color: "#2e82d6", fontSize: "14px", textDecoration: "none" }}
                  >
                    Already have an account? Sign in
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

export default Register;
