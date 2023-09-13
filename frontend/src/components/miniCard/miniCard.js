import React from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Stack from "@mui/material/Stack";
import Avatar from "@mui/material/Avatar";

import style from "./minicard.module.css";
import stockImage from "../../assets/images/ethereum.png";

const Minicard = () => {
  return (
    <Card className={style.portfolioCardWrapper} sx={{ maxWidth: 345 }}>
      <CardContent>
        <div className={style.stockHeader}>
          <p>ethereum</p>
          <p>4.35%</p>
         </div>
         <div>
           56,542
         </div>
      </CardContent>
    </Card>
  );
};

export default Minicard;

