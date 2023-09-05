import React from "react";
import style from "./Dashboard.module.css";
import Container from "@mui/material/Container";
import { styled } from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";

import Grid from "@mui/material/Grid";

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === "dark" ? "#1A2027" : "#fff",
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: "center",
  color: theme.palette.text.secondary,
}));

const Dashboard = () => {
  return (
    <div className={style.dashboardWrapper}>
      <Container maxWidth="xl">
        <Grid container spacing={2}>
          <Grid item xs={8}>
            <Typography variant="h3" gutterBottom>
              h2. Heading
            </Typography>
          </Grid>
          <Grid item xs={4}>
            Right Side
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default Dashboard;
