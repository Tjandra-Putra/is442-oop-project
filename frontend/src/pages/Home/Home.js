import React from "react";
// import Container from "@mui/material/Container";
import style from "./Home.module.css";

const home = () => {
  return (
    <div className={style.homeWrapper}>

        {/* First div */}
        <div className={style.firstWrapper}>
            <div className={style.firstWrappertext}>
                <p id = {style.FirstWrappertext}>Welcome to Goldman Sachs</p>
                <p>Here you can find good financial products</p>
            </div>
        </div>

        {/* Second div */}
        <div className={style.emptydiv} ></div>

    </div>
  );
};

export default home;
