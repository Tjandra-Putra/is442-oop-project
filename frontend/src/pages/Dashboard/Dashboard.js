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
import { useSelector } from "react-redux";

const Dashboard = () => {
  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

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
    axios
      .get("http://localhost:8080/api/portfolio/getPortfolio/" + user.userId, {
        headers: {
          Authorization: `Bearer ${user?.token}`, // Replace "yourTokenHere" with your actual token
        },
      })
      .then((res) => {
        setPortfolios(res.data.data);

        let tempPortfolios = [];

        for (let i = 0; i < res.data.data.length; i++) {
          tempPortfolios.push({ label: res.data.data[i].portfolioName, id: res.data.data[i].portfolioId });
          setSearchListOfPortfolios(tempPortfolios);
        }
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
        <Grid container spacing={2} sx={{ mt: 1 }}>
          <Grid item md={9} xs={12}>
            <div className="portfolios" style={{ marginTop: "0rem" }}>
              <Grid container spacing={4}>
                <Grid item md={6} xs={12}></Grid>
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
              </CardContent>

              <hr className={style.horizontalLine} />

              {/* <CardContent>
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
              </CardContent> */}
            </Card>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default Dashboard;
