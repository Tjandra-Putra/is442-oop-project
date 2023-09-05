import React from "react";
import Container from "@mui/material/Container";
import style from "./Home.module.css";

const home = () => {
  return (
    <div className={style.homeWrapper}>
      <Container maxWidth="xl">
        <h1>Home</h1>
      </Container>
    </div>
  );
};

export default home;
