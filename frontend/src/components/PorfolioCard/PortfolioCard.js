import React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Avatar from "@mui/material/Avatar";

import style from "./PortfolioCard.module.css";
import stockImage from "../../assets/images/ethereum.png";

const PortfolioCard = () => {
  return (
    <Card className={style.portfolioCardWrapper}>
      <CardContent>
        <div className={style.stockHeader}>
          <div className={style.stockType}>
            <Avatar src={stockImage}></Avatar>
            <div className={style.stockName} gutterBottom variant="h5" component="div">
              Etherium
            </div>
          </div>
          <div className={style.ticker}>(NETSOL)</div>
        </div>

        <div className={style.stockMiddle}>
          <div className="value">RS 128.00</div>
          <div className="performance">-3(0.58%)</div>
        </div>

        <div className={style.stockFooter}>
          <div className={style.stockFooterLeftItem}>
            <div className={style.stockFooterText}>Quantity</div>
            <div className={style.stockFooterFigure}>100</div>
          </div>
          <div className={style.stockFooteMiddleItem}>
            <div className={style.stockFooterText}>Average Cost</div>
            <div className={style.stockFooterFigure}>Rs 128.00</div>
          </div>
          <div className={style.stockFooterRightItem}>
            <div className={style.stockFooterText}>Market Value</div>
            <div className={style.stockFooterFigure}>Rs 128.00</div>
          </div>
        </div>
      </CardContent>
    </Card>
  );
};

export default PortfolioCard;
