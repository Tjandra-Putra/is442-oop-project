import React from "react";
import style from "./PortfolioDetailed.module.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";
import StockCard from "../../components/StockCard/StockCard";
import PortfolioStocksAllocationChart from "../../components/Charts/PortfolioStocksAllocationChart/PortfolioStocksAllocationChart";
import { Card, Grid, CardContent } from "@mui/material";
import PortfolioStocksBySegmentChart from "../../components/Charts/PortfolioStocksBySegmentChart/PortfolioStocksBySegmentChart";
import PortfolioStocksChart from "../../components/Charts/PortfolioStocksChart/PortfolioStocksChart";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import AddIcon from "@mui/icons-material/Add";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
import SaveIcon from "@mui/icons-material/Save";
import CancelIcon from "@mui/icons-material/Close";
import StockGrid from "../../components/StockGrid/StockGrid";
const PortfolioDetailed = () => {
  // get id from url paramter
  const { id } = useParams();

  // get portfolio name by id

  return (
    <div className={style.portfolioDetailedWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              {id}
            </Typography>
          </div>
        </Container>
      </div>

      {/* Second Container */}
      <Container>
        <div className={style.portfolioDescription}>
          <div className="portfolio-description-content">
            <Typography variant="h6" style={{ letterSpacing: "1px" }}>
              Portfolio Description
            </Typography>
            <Typography variant="body1" style={{ letterSpacing: "1px" }}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam voluptatum, quibusdam, voluptate, quia
              voluptas quod quos voluptatibus quae doloribus quidem voluptatem. Quisquam voluptatum, quibusdam,
              voluptate, quia voluptas quod quos voluptatibus quae doloribus quidem voluptatem. Quisquam voluptatum,
              quibusdam, voluptate, quia voluptas quod quos voluptatibus quae doloribus
            </Typography>
          </div>
        </div>

        {/* ============================ LEFT SECTION ============================ */}
        <Grid container rowSpacing={5} columnSpacing={{ xs: 2, sm: 2, md: 1 }}>
          <Grid item xs={12} md={6} lg={6}>
            <div className={style.firstContainer}>
              <StockCard name="Portfolio Value" value="$6,364" />
              <StockCard name="Total Stocks" value="10" />
            </div>

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Stocks Allocation</div>
                <PortfolioStocksAllocationChart />
              </CardContent>
            </Card>
          </Grid>

          {/* ============================ RIGHT SECTION ============================ */}
          <Grid item xs={12} md={6} lg={6}>
            <div className={style.firstContainer}>
              <StockCard name="Net Value" value="20% " />
              <StockCard name="Capital" value="$20,000" />
            </div>

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Stocks by Segment</div>
                <PortfolioStocksBySegmentChart />
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Grid item xs={12} md={12} lg={12}>
          <Card className={style.cardCustom}>
            <CardContent>
              <PortfolioStocksChart />
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={12} lg={12}>
          <Card className={style.cardCustom}>
            <CardContent>
              <StockGrid />
            </CardContent>
          </Card>
        </Grid>
      </Container>
    </div>
  );
};

export default PortfolioDetailed;
