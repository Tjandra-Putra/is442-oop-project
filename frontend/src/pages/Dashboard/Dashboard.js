import React from "react";
import style from "./Dashboard.module.css";
import Container from "@mui/material/Container";
import { styled } from "@mui/material/styles";
import Paper from "@mui/material/Paper";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";

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
            <Stack direction="row" justifyContent="space-between" alignItems="flex-start" spacing={1}>
              <Typography variant="h4" gutterBottom>
                Dashboard
              </Typography>
              <Typography variant="h5" gutterBottom>
                $46,509.00
              </Typography>
            </Stack>
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
