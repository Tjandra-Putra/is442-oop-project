import React from "react";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import style from "./Footer.module.css";
import Grid from "@mui/material/Grid";
import Container from "@mui/material/Container";

function Footer() {
  return (
    <div className={style.footerWrapper}>
      <Container maxWidth="xl">
        <Grid container spacing={4} mt={0}>
          <Grid item md={6} xs={12}>
            {/* Brand Name */}
            <div className={style.brandName}>
              Goldman <br />
              Sachs
            </div>
          </Grid>
          <Grid item md={3} xs={12}>
            <ul className={style.footerItems}>
              <li>Privacy and Cookies</li>
              <li>Terms of Use</li>
              <li>Security & Fraud Awareness</li>
              <li>Regulatory Disclosures</li>
            </ul>
          </Grid>
          <Grid item md={3} xs={12}>
            <ul className={style.footerItems}>
              <li>Site Map</li>
              <li>Client Logins</li>
              <li>Employee Login</li>
              <li>Alumni Network</li>
              <li>Mobile View</li>
            </ul>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}

export default Footer;
