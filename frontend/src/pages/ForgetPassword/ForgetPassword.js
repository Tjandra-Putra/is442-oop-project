import * as React from "react";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import axios from "axios";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { Link } from "react-router-dom";
import Cookies from "js-cookie";

import styles from "./ForgetPassword.module.css";

const defaultTheme = createTheme();

const ForgetPassword = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
  };

  return (
    <div className={styles.forgetPasswordWrapper}>
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
              <i class="fa-solid fa-lock"></i>
            </div>
            <Typography component="h1" variant="h5">
              Forget Password
            </Typography>
            <small className={styles.description}>
              Enter your email address and we will send you a link to reset your password.
            </small>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1, width: "25rem" }}>
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
              <Button type="submit" fullWidth variant="contained" sx={{ mt: 2, mb: 2 }}>
                Send Email Link to Reset Password
              </Button>
              <Grid container>
                <Grid item xs>
                  <Link
                    to="/login"
                    style={{ cursor: "pointer", color: "#2e82d6", fontSize: "14px", textDecoration: "none" }}
                  >
                    <i class="fa-solid fa-arrow-left" style={{ marginRight: "0.5rem" }}></i> Return to Sign In
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

export default ForgetPassword;
