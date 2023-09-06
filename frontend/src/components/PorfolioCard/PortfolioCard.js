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
    <Card className={style.portfolioCardWrapper} sx={{ maxWidth: 345 }}>
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

        <div className={style.stockMiddle}></div>

        <div className={style.stockFooter}></div>

        <Typography variant="body2" color="text.secondary">
          Lizards are a widespread group of squamate reptiles, with over 6,000 species, ranging across all continents
          except Antarctica
        </Typography>
      </CardContent>
      <CardActions>
        <Button size="small">Share</Button>
        <Button size="small">Learn More</Button>
      </CardActions>
    </Card>
  );
};

export default PortfolioCard;
