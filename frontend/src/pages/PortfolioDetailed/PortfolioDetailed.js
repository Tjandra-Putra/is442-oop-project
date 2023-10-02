import React from "react";
import style from "./PortfolioDetailed.module.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";
import StockCard from "../../components/StockCard/StockCard";
import PieChart from "../../components/Charts/PieChart/PieChart";
import Dashboard from "../Dashboard/Dashboard";
import TransactionDetails from "../../components/StockGrid/TransactionDetails";
import EditIcon from "@mui/icons-material/Edit";
import BarChart from "../../components/Charts/BarChart/BarChart";
import { Grid } from "@mui/material";

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
      <Container>
        <div className={style.firstContainer}>
          <StockCard name="Portfolio Value" value="$6,364" />
          <StockCard name="Total Stocks" value="10" />
          <StockCard name="Net Value" value="20% " />
          <StockCard name="Wallet" value="$20,000" />
        </div>
      </Container>

      {/* Second Container */}
      <Container>
        <Grid container>
          <Grid item xs={12} md={7} lg={7}>
            <div className={style.pieChart}>
              <PieChart />
            </div>

            <div className={style.outgoingTransactionsParent}>
              <div className={style.outgoingTransactions}>
                <div>
                  <p>Stock Transactions</p>
                </div>
                <div>
                  <p>Show all</p>
                </div>
              </div>
              <div className={style.outgoingTransactionsContent}>
                <div>
                  <p>Type</p>
                </div>
                <div>
                  <p>Date</p>
                </div>
                <div>
                  <p>Status</p>
                </div>
                <div>
                  <p>Amount</p>
                </div>
              </div>
              <div>
                <TransactionDetails />
                <TransactionDetails />
                <TransactionDetails />
              </div>
            </div>
          </Grid>
          <Grid item xs={12} md={5} lg={5}>
            <div className={style.activeAccount}>
              <div>
                <p style={{ paddingLeft: "10px" }}>Wallet Transactions</p>
              </div>
              <div className={style.activeAccountEdit}>
                <EditIcon />
              </div>
            </div>

            <div className={style.activeAccountContent}>
              <div>
                <p>Account#</p>
              </div>
              <div>
                <p>IBAN</p>
              </div>
              <div>
                <p>Total/Available</p>
              </div>
            </div>
            {/* Active Account Transactions */}
            <div className={style.TransactionDetails}>
              <TransactionDetails />
              <TransactionDetails />
              <TransactionDetails />
            </div>

            {/* Barchar */}
            <BarChart />
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default PortfolioDetailed;
