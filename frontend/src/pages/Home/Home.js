import React from "react";
// import Container from "@mui/material/Container";
import styles from "./Home.module.css";


const home = () => {
  return (

    <>
    
    {/* First Section */}
    <div className={styles.gridcontainerone}>
      <div className={styles.stockone}>
        <p>Welcome to Investment portfolio</p>
        <p className={styles.landingpage} > Start Building your Investment Portfolio Today</p>
      </div>
    </div>
      
  
    {/* Second Section - Grid with two columns */}
    <div className={styles.gridcontainertwo}>
      <div className={styles.stocktwo}>
      <img src="image7.jpg"></img>
      </div>
      <div className={styles.stocktwoitem}>
        <p>Stock Market is a web application that allows users to view stock prices and company information. Users can search for stocks by company name or ticker symbol, and view the latest price data for each stock.</p>
      </div>
    </div>

    {/* Third Section - Grid with two columns */}
    <div className={styles.gridcontainerthree}>
      <p>Stock Market is a web application that allows users to view stock prices and company information. Users can search for stocks by company name or ticker symbol, and view the latest price data for each stock.</p>
      
      <div className={styles.stockthree}>
      <img src="image7.jpg"></img>
      </div>
    </div>

    </>

  );
};

export default home;
