import React from "react";
import style from "./PortfolioDetailed.module.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";
import Minicard from "../../components/miniCard/miniCard";
import PortfolioCard from "../../components/PorfolioCard/PortfolioCard";
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
      <Container className={style.firstContainer}>
         <Minicard />
         <Minicard />
         <Minicard />
         <Minicard />
      </Container>
    </div>
  );
};

export default PortfolioDetailed;
