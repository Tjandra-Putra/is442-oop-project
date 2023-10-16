import React from "react";
import style from "./PortfolioDetailed.module.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";
import StockCard from "../../components/StockCard/StockCard";
import PieChart from "../../components/Charts/PieChart/PieChart";
// import Dashboard from "../Dashboard/Dashboard";
import TransactionDetails from "../../components/StockGrid/TransactionDetails";
import AccountTransactions from "../../components/StockGrid/AccountTransactions";
// import EditIcon from "@mui/icons-material/Edit";
import BarChart from "../../components/Charts/BarChart/BarChart";
import { Card, Grid, CardContent } from "@mui/material";
// import { Pie } from "react-chartjs-2";

const PortfolioDetailed = () => {
  // get id from url paramter
  const { id } = useParams();

  return (
    <div className={style.portfolioDetailedWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              Portfolio: {id}
            </Typography>
          </div>
        </Container>
      </div>

      {/* First container */}
      {/* <Container>
        <div className={style.firstContainer}>
          <StockCard name="Portfolio Value" value="$6,364" />
          <StockCard name="Total Stocks" value="10" />
          <StockCard name="Net Value" value="20% " />
          <StockCard name="Wallet" value="$20,000" />
        </div>
      </Container> */}

      {/* Second Container */}
      <Container>
        {/* LEFT SECTION */}
        <Grid container rowSpacing={1} columnSpacing={{ xs: 1, sm: 2, md: 1 }}>
          <Grid item xs={12} md={6} lg={6}>
            <div className={style.firstContainer}>
              <StockCard name="Portfolio Value" value="$6,364" />
              <StockCard name="Total Stocks" value="10" />
            </div>

            <div style={{ maxHeight: "300px", marginTop: "10px" }}>
              <Card className={style.pieChart}>
                <CardContent>
                  <PieChart />
                </CardContent>
              </Card>
            </div>

            <div style={{ maxHeight: "300px", marginTop: "10px" }}>
              <TransactionDetails />
            </div>
          </Grid>

          {/* RIGHT SECTION */}
          <Grid item xs={12} md={6} lg={6}>
            <div className={style.firstContainer}>
              <StockCard name="Net Value" value="20% " />
              <StockCard name="Wallet" value="$20,000" />
            </div>

            <div style={{ maxHeight: "300px", marginTop: "10px" }}>
              <AccountTransactions />
            </div>

            <div style={{ maxHeight: "300px", marginTop: "10px" }}>
              <Card className={style.barChart}>
                <CardContent>
                  <BarChart />
                </CardContent>
              </Card>
            </div>
          </Grid>

          {/* <Grid item xs={12} md={6} lg={6}>
            <div style={{ maxHeight: "300px", marginTop: "10px" }}>
              <AccountTransactions />
            </div>
          </Grid>
          <Grid item xs={12} md={6} lg={6}>
            <div style={{ maxHeight: "300px", marginTop: "10px", marginRight: "20px" }}>
              <TransactionDetails />
            </div>
          </Grid>
          <Grid item xs={12} md={6} lg={6}>
            <div style={{ maxHeight: "300px", marginTop: "10px" }}>
              <Card className={style.barChart}>
                <CardContent>
                  <BarChart />
                </CardContent>
              </Card>
            </div>
          </Grid> */}
        </Grid>
      </Container>
    </div>
  );
};

export default PortfolioDetailed;
