import React from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Grid from "@mui/material/Grid";
import Chip from "@mui/material/Chip";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import style from "./Dashboard.module.css";

const Dashboard = () => {
  return (
    <div className={style.dashboardWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              Dashboard
            </Typography>
          </div>
        </Container>
      </div>
      <Container maxWidth="xl">
        <Grid container spacing={4} sx={{ mt: 0 }}>
          <Grid item md={8} xs={12}>
            <Card sx={{ minHeight: 540 }}>
              <CardContent>
                <Stack direction="row" justifyContent="space-between" alignItems="flex-start">
                  <Stack spacing={{ xs: 1, sm: 2 }} direction="row" useFlexGap flexWrap="wrap">
                    <Typography variant="h6" className={style.allStocksNetFigure}>
                      $46,509.00
                    </Typography>
                    <Chip label="+ 1,468" color="primary" className={style.statusChip} />
                  </Stack>

                  <Typography variant="h6">My Stock Values (USD)</Typography>
                </Stack>
              </CardContent>
            </Card>
          </Grid>
          <Grid item md={4} xs={12}>
            <Card sx={{ minHeight: 540 }}>
              <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                  Lizard
                </Typography>
                <Typography variant="body2" color="text.secondary">
                  Lizards are a widespread group of squamate reptiles, with over 6,000 species, ranging across all
                  continents except Antarctica
                </Typography>
              </CardContent>
              <CardActions>
                <Button size="small">Share</Button>
                <Button size="small">Learn More</Button>
              </CardActions>
            </Card>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default Dashboard;
