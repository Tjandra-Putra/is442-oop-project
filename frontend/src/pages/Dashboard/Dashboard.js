import React from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Grid from "@mui/material/Grid";
import Chip from "@mui/material/Chip";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import style from "./Dashboard.module.css";
import AddIcon from "@mui/icons-material/Add";
import { useNavigate } from "react-router-dom";

import PortfolioReturnsChart from "../../components/Charts/PortfolioReturnsChart/PortfolioReturnsChart";
import PortfolioCard from "../../components/PorfolioCard/PortfolioCard";
import Menu from "../../components/Menu/Menu";
import MarketExposureByGeographicalLocationChart from "../../components/Charts/MarketExposureByGeographicalLocationChart/MarketExposureByGeographicalLocationChart";
import { TextField } from "@mui/material";
import MarketExposureBySegment from "../../components/Charts/MarketExposureBySegment/MarketExposureBySegment";

const Dashboard = () => {
  const navigate = useNavigate();

  const [portfolioName, setPortfolioName] = React.useState("");

  const handleAddNewPortfolioButton = () => {
    // Replace '/target-page' with the path of the page you want to redirect to.
    navigate("/create-portfolio");
  };

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
        <Grid container spacing={2} mt={0}>
          <Grid item md={3} xs={6}>
            <Card className={style.figuresBar}>
              <div className={style.figuresBarTop}>
                <div variant="h7" className={style.portfolioWorthText}>
                  Portfolios' Worth
                </div>
                <div className={style.iconWrapper}>
                  <i class="fa-solid fa-dollar-sign"></i>
                </div>
              </div>

              <div variant="h5" mr={2} className={style.portfolioWorthFigure}>
                $46,509.00
              </div>
            </Card>
          </Grid>
          <Grid item md={3} xs={6}>
            <Card className={style.figuresBar}>
              <div className={style.figuresBarTop}>
                <div variant="h7" className={style.portfolioWorthText}>
                  Net Profit / Loss
                </div>
                <div className={style.iconWrapper}>
                  <i class="fa-solid fa-chart-line"></i>
                </div>
              </div>

              <div variant="h7" mr={2} className={style.portfolioWorthFigure}>
                $3039.00
              </div>
            </Card>
          </Grid>
          <Grid item md={3} xs={6}>
            <Card className={style.figuresBar}>
              <div className={style.figuresBarTop}>
                <div variant="h7" className={style.portfolioWorthText}>
                  Wallet Balance
                </div>
                <div className={style.iconWrapper}>
                  <i class="fa-solid fa-wallet"></i>
                </div>
              </div>

              <div variant="h7" mr={2} className={style.portfolioWorthFigure}>
                $1093.00
              </div>
            </Card>
          </Grid>
          <Grid item md={3} xs={6}>
            <Card className={style.figuresBar}>
              <div className={style.figuresBarTop}>
                <div variant="h7" className={style.portfolioWorthText}>
                  Daily Profit / Loss
                </div>
                <div className={style.iconWrapper}>
                  <i class="fa-regular fa-calendar-check"></i>
                </div>
              </div>

              <div variant="h7" mr={2} className={style.portfolioWorthFigure}>
                $204.00
              </div>
            </Card>
          </Grid>
        </Grid>

        <Grid container spacing={2}>
          <Grid item md={9} xs={12}>
            <Card className={style.chart} sx={{ minHeight: 540 }}>
              <CardContent>
                <PortfolioReturnsChart />
              </CardContent>
            </Card>

            <Card className={style.chart} sx={{ minHeight: 540 }}>
              <CardContent>
                <MarketExposureByGeographicalLocationChart />
              </CardContent>
            </Card>

            <Card className={style.chart} sx={{ minHeight: 540 }}>
              <CardContent>
                <MarketExposureBySegment />
              </CardContent>
            </Card>

            <div className="portfolios" style={{ marginTop: "2rem" }}>
              <Grid container spacing={4}>
                <Grid item md={6} xs={12}>
                  <Typography variant="h6" className={style.portfoliosText}>
                    My Portfolios
                  </Typography>
                </Grid>
                {/* <Grid item md={6} xs={12} style={{ textAlign: "right" }}>
                  <Stack direction="row" spacing={1} justifyContent="flex-end" sx={{ mb: 3 }}>
                    <Menu menuName="Sort by" />
                    <Button variant="contained" startIcon={<AddIcon />} onClick={() => handleAddNewPortfolioButton()}>
                      Add
                    </Button>
                  </Stack>
                </Grid> */}
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
            <Card className={style.rightSection}>
              <CardContent>
                <Typography variant="h6" className={style.portfolioWorthText}>
                  Create New Portfolio
                </Typography>
                <Button
                  variant="outlined"
                  size="large"
                  className={style.addNewPortfolioButton}
                  onClick={() => handleAddNewPortfolioButton()}
                >
                  Add New Portfolio
                </Button>
              </CardContent>

              <hr className={style.horizontalLine} />

              <CardContent>
                <Typography variant="h6" className={style.portfolioWorthText}>
                  Navigate to Portfolio
                </Typography>
                <TextField
                  id="outlined-basic"
                  label="Name of Portfolio"
                  variant="standard"
                  multiline
                  maxRows={2}
                  sx={{
                    width: "100%",
                  }}
                  value={portfolioName}
                  onChange={(e) => setPortfolioName(e.target.value)}
                  helperText="Enter the name of the portfolio you want to navigate to."
                />

                <Button
                  size="large"
                  variant="contained"
                  className={style.addNewPortfolioButton}
                  onClick={() => (portfolioName ? navigate(`/portfolio/${portfolioName}`) : null)}
                >
                  Go
                </Button>
              </CardContent>

              <hr className={style.horizontalLine} />

              <CardContent>
                <Typography variant="h6" className={style.portfolioWorthText}>
                  Top Performing Portfolio
                </Typography>

                <ul className={style.topPerformingPortfolios}>
                  <li className={style.topPerformingPortfolio}>
                    <span className={style.topPerformingPortfolioName}>Portfolio 1</span>
                    <Chip
                      label="+ 5.3%"
                      // color="primary"
                      className={style.topPerformingPortfolioReturns}
                      sx={{ ml: 1 }}
                    />
                  </li>
                  <li className={style.topPerformingPortfolio}>
                    <span className={style.topPerformingPortfolioName}>Portfolio 2</span>
                    <Chip
                      label="+ 3.3%"
                      // color="primary"
                      className={style.topPerformingPortfolioReturns}
                      sx={{ ml: 1 }}
                    />
                  </li>
                  <li className={style.topPerformingPortfolio}>
                    <span className={style.topPerformingPortfolioName}>Portfolio 3</span>
                    <Chip
                      label="+ 2.3%"
                      // color="primary"
                      className={style.topPerformingPortfolioReturns}
                      sx={{ ml: 1 }}
                    />
                  </li>
                </ul>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default Dashboard;
