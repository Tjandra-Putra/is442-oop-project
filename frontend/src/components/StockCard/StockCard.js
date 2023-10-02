import React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import { Button } from "@mui/material";


import style from "./StockCard.module.css";

const StockCard = ({ name, value, percentage }) => {
  return (
    <Card className={style.portfolioCardWrapper} sx={{ maxWidth:305}}>
      <CardContent>
        <div className={style.stockHeader}>
          <p>{name}</p>
          {name == "Wallet" ? <Button variant="outlined">Top Up</Button>: null}
        </div>
        <div>{value}</div>
      </CardContent>
    </Card>
  );
};

export default StockCard;
