import React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Avatar from "@mui/material/Avatar";

import style from "./StockCard.module.css";
import stockImage from "../../assets/images/ethereum.png";

const StockCard = ({ name, value, percentage }) => {
  return (
    <Card className={style.portfolioCardWrapper} sx={{ maxWidth: 345 }}>
      <CardContent>
        <div className={style.stockHeader}>
          <p>{name}</p>
          {name == "Wallet" ? <button>Topup</button> : null}
        </div>
        <div>{value}</div>
      </CardContent>
    </Card>
  );
};

export default StockCard;
