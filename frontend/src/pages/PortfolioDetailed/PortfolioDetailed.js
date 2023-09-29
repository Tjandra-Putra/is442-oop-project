import React from "react";
import style from "./PortfolioDetailed.module.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";
import Minicard from "../../components/miniCard/miniCard";
import PieChart from "../../components/Charts/PieChart/PieChart";
import Dashboard from "../Dashboard/Dashboard";
import SchedulePayment from "../../components/StockGrid/SchedulePayment";
import TransactionDetails from "../../components/StockGrid/TransactionDetails";
import EditIcon from '@mui/icons-material/Edit';
import BarChart from "../../components/Charts/BarChart/BarChart";
import { Bar } from "react-chartjs-2";
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
          <Minicard />
          <Minicard />
          <Minicard />
          <Minicard />
        </div>
      </Container>


      {/* Second Container */}
      <Container>
      <Grid container >
        <Grid item xs={12} md={7} lg={7}>
        <div className={style.PieChart} >
                    <div>
                      <PieChart />
                    </div>
                    <div>
                      <SchedulePayment />
                    </div>
        </div>

        <div className={style.outgoingTransactionsParent}>
                <div className={style.outgoingTransactions}>
                    <div>
                      <p>Outgoing Transactions</p>
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
                <p style={{ paddingLeft: '10px' }}>Active Accounts</p>
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
