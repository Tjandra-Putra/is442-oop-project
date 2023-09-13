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
import AddIcon from "@mui/icons-material/Add";
import BarChart from "../../components/Charts/BarChart/BarChart";
import PortfolioCard from "../../components/PorfolioCard/PortfolioCard";
import Menu from "../../components/Menu/Menu";

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
        <Grid container spacing={4} mt={0}>
          <Grid item md={9} xs={12}>
            <Card className={style.figuresBar}>
              <Typography variant="h6" className={style.portfolioWorthText}>
                Portfolios' Worth
              </Typography>

              <Stack direction="row" justifyContent="space-between" alignItems="flex-start">
                <Typography variant="h6" mr={2} className={style.portfolioWorthFigure}>
                  $46,509.00
                </Typography>
                <Chip label="+1,468" color="primary" className={style.statusChip} />
              </Stack>
            </Card>

            <Card className={style.chart} sx={{ minHeight: 540 }}>
              <CardContent>
                <div style={{ textAlign: "right" }}>
                  <Menu menuName="Filter by Year" />
                </div>
                <BarChart />
              </CardContent>
            </Card>

            <div className="portfolios" style={{ marginTop: "0rem" }}>
              <Grid container spacing={4} mt={0}>
                <Grid item md={6} xs={12}>
                  <Typography variant="h6" className={style.portfoliosText}>
                    You Stock Portfolios
                  </Typography>
                </Grid>
                <Grid item md={6} xs={12} style={{ textAlign: "right" }}>
                  <Stack direction="row" spacing={1} justifyContent="flex-end">
                    <Menu menuName="Sort by" />
                    <Button variant="contained" startIcon={<AddIcon />}>
                      Add
                    </Button>
                  </Stack>
                </Grid>
              </Grid>

              <Grid container spacing={4}>
                <Grid item md={4} xs={12}>
                  <PortfolioCard />
                </Grid>
                <Grid item md={4} xs={12}>
                  <PortfolioCard />
                </Grid>
                <Grid item md={4} xs={12}>
                  <PortfolioCard />
                </Grid>

                <Grid item md={4} xs={12}>
                  <PortfolioCard />
                </Grid>

                <Grid item md={4} xs={12}>
                  <PortfolioCard />
                </Grid>
              </Grid>
            </div>
          </Grid>
          <Grid item md={3} xs={12}>
            <Card className={style.rightSection} sx={{ minHeight: 540 }}>
              <CardContent>
                <Typography variant="h6" className={style.portfolioWorthText}>
                  Total Balance
                </Typography>
                <Typography variant="h4" className={style.totalBalance}>
                  $108,509.00
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
