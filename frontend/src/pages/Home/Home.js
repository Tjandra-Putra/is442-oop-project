import React from "react";
// import Container from "@mui/material/Container";
import styles from "./Home.module.css";


const home = () => {
  return (

    <>
    
    {/* First Section */}
    <div className={styles.gridcontainerone}>
      <div className={styles.stockimageone}>
        <p>Welcome to Investment portfolio</p>
        <p className={styles.landingpage} > Start Building your Investment Portfolio Today</p>
      </div>
    </div>
      

    <div className={styles.emptydiv} ></div>
  
    {/* Second Section - Grid with two columns */}
    <div className={styles.gridcontainertwo}>
      <div className={styles.stockimagetwo}>
        <img src="image7.jpg"></img>
      </div>
      <div className={styles.stocktwoitem}>
        <p>Stock Market is a web application that allows users to view stock prices and company information. Users can search for stocks by company name or ticker symbol, and view the latest price data for each stock.</p>
      </div>
    </div>

    {/* Third Section - Grid with two columns */}
    <div className={styles.gridcontainerthree}>
      <div>
      <p>Stock Market is a web application that allows users to view stock prices and company information. Users can search for stocks by company name or ticker symbol, and view the latest price data for each stock.</p>
      </div>
      <div className={styles.stockimagethree}>
        <img src="image7.jpg"></img>
      </div>
    </div>

    </>

  );
};

export default home;
