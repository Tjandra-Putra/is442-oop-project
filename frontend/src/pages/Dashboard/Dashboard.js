import React, { useEffect } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Grid from "@mui/material/Grid";
import Chip from "@mui/material/Chip";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Button from "@mui/material/Button";
import style from "./Dashboard.module.css";
import { useNavigate } from "react-router-dom";
import PortfolioCard from "../../components/PorfolioCard/PortfolioCard";
import { TextField } from "@mui/material";
import Autocomplete from "@mui/material/Autocomplete";
import axios from "axios";

const Dashboard = () => {
  const navigate = useNavigate();

  const [portfolioName, setPortfolioName] = React.useState("");
  const [portfolios, setPortfolios] = React.useState([]);
  const [searchListOfPortfolios, setSearchListOfPortfolios] = React.useState([]);
  const [selectedPortfolio, setSelectedPortfolio] = React.useState(null);

  const handlePortfolioSelection = (event, selectedOption) => {
    if (selectedOption) {
      setSelectedPortfolio(selectedOption);
    }
  };

  useEffect(() => {
    // Use selectedPortfolio.id for navigation if it exists
    if (selectedPortfolio) {
      navigate(`/portfolio/${selectedPortfolio.id}`);
    }
  }, [selectedPortfolio]);

  const handleAddNewPortfolioButton = () => {
    // Replace '/target-page' with the path of the page you want to redirect to.
    navigate("/create-portfolio");
  };

  useEffect(() => {
    const userId = 1;

    axios
      .get("http://localhost:8080/api/portfolio/getPortfolio/" + userId)
      .then((res) => {
        console.log(res.data);
        setPortfolios(res.data.data);

        let tempPortfolios = [];

        for (let i = 0; i < res.data.data.length; i++) {
          tempPortfolios.push({ label: res.data.data[i].portfolioName, id: res.data.data[i].portfolioId });
          setSearchListOfPortfolios(tempPortfolios);
        }

        console.log(searchListOfPortfolios);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

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
        {/* <Grid container spacing={2} mt={0}>
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
                  Net Profit / Loss ?
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
                  Wallet Balance ?
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
        </Grid> */}

        <Grid container spacing={2} sx={{ mt: 1 }}>
          <Grid item md={9} xs={12}>
            {/* <Card className={style.chart} sx={{ minHeight: 540 }}>
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
            </Card> */}

            <div className="portfolios" style={{ marginTop: "0rem" }}>
              <Grid container spacing={4}>
                <Grid item md={6} xs={12}>
                  {/* <Typography variant="h6" className={style.portfoliosText}>
                    My Portfolios
                  </Typography> */}
                </Grid>
              </Grid>
              <Grid container spacing={4}>
                {portfolios?.map((portfolio, index) => (
                  <Grid item md={4} xs={12} key={index} onClick={() => navigate(`/portfolio/${portfolio.portfolioId}`)}>
                    <PortfolioCard
                      portfolioName={portfolio.portfolioName}
                      capitalAmt={portfolio.capitalAmt}
                      description={portfolio.description}
                      portfolioId={portfolio.portfolioId}
                    />
                  </Grid>
                ))}
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
                  variant="contained"
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
                <Autocomplete
                  disablePortal
                  id="combo-box-demo"
                  options={searchListOfPortfolios}
                  getOptionLabel={(option) => option.label} // Assuming 'label' is the property containing the portfolio name in your data
                  onChange={handlePortfolioSelection}
                  sx={{ width: 300, mt: 1 }}
                  renderInput={(params) => <TextField {...params} label="My Portfolios" />}
                />
                {/* <TextField
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
                /> */}
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
