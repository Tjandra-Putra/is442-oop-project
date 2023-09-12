import React from "react";
import style from "./CreatePortfolio.module.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";

const CreatePortfolio = () => {
  return (
    <div className={style.createPortfolioWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              Create
            </Typography>
          </div>
        </Container>
      </div>
    </div>
  );
};

export default CreatePortfolio;
