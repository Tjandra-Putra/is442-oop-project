import React from "react";
// import Container from "@mui/material/Container";
import styles from "./Home.module.css";


const home = () => {
  return (

    <>
    
    {/* First image */}
    <div className={styles.stockone}>
      <p>Welcome to Investment portfolio</p>
      <p className={styles.landingpage} > Start Building your Investment Portfolio Today</p>
    </div>

      
    {/* Second image */}
    <div className={styles.stocktwo}>
      <img src="image7.jpg"></img>
    </div>
      </>

  );
};

export default home;
