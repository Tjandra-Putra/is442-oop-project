import React from "react";
// import Container from "@mui/material/Container";
import style from "./Home.module.css";

const home = () => {
  return (
    <div className={style.homeWrapper}>

        {/* First div - Created a grid of 4 columns with the fonts occupying the second columm */}
        <div className={style.firstWrapper}>
          <div></div>
            <div className={style.firstWrappertext}>
                <div className={style.landingPage} >
                   <p>Welcome to Goldman Sachs</p>
                    <p>Here you can find good financial products</p>
                </div>
            </div>
          <div></div>
          <div></div>
        </div>

        {/* Second div */}
        <div className={style.emptydiv} ></div>

        {/* Third div */}
        <div className={style.secondWrapper}>
          {/* First Row */}
          <div className={style.secondWrapperImage}>
            <img src="image7.jpg"></img>
          </div>
          {/* Second Row */}
          <div>
          <p>Welcome to Goldman Sachs</p>
          <p>Here you can find good financial products</p>
          </div>
        </div>

        {/* Third div */}
        <div className={style.thirdWrapper}>
          {/* First Row */}
          <div className={style.thirdWrapperImage}>
            <p>Welcome to Goldman Sachs</p>
            <p>Here you can find good financial products</p>
          </div>
          {/* Second Row */}
          <div>
          <img src="image7.jpg"></img>
          </div>
        </div>


    </div>
  );
};

export default home;
