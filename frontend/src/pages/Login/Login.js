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

import styles from "./Login.module.css";

const defaultTheme = createTheme();

const Login = () => {
  const handleSubmit = (event) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);

    // ====================== get user info from database ======================
    const userId = 1;
    axios
      .get("http://localhost:8080/api/user/getUser/" + userId)
      .then((res) => {
        const objectString = JSON.stringify(res.data.data[0]);
        Cookies.set("userInfo", objectString, { expires: 7 }); // expires in 7 days

        // get cookie
        const cookieValue = Cookies.get("userInfo");
        const object = cookieValue ? JSON.parse(cookieValue) : null;
        console.log("cookie: " + object);
      })
      .catch((err) => {
        console.log(err);
      });
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
            <Typography component="h1" variant="h5">
              Sign in
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
                  <Link to="/register" style={{ cursor: "pointer", color: "#2e82d6", fontSize: "14px" }}>
                    Forgot password?
                  </Link>
                </Grid>
                <Grid item>
                  <Link to="/register" style={{ cursor: "pointer", color: "#2e82d6", fontSize: "14px" }}>
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
