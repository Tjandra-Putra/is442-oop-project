import React from "react";
import style from "./CreatePortfolio.module.css";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Grid";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import StockGrid from "../../components/StockGrid/StockGrid";

const CreatePortfolio = () => {
  return (
    <div className={style.createPortfolioWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              Create Portfolio
            </Typography>
          </div>
        </Container>
      </div>
      <Container maxWidth="xl">
        <Grid container spacing={4} mt={0}>
          <Grid item md={6} xs={12}>
            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Portfolio Name</div>

                <small className={style.cardDesc}>This will be the name that is displayed</small>

                <TextField
                  required
                  fullWidth
                  id="outlined-required"
                  label="Portfolio Name"
                  defaultValue=""
                  sx={{ mt: 3 }}
                />
              </CardContent>
            </Card>

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Description</div>

                <small className={style.cardDesc}>This will be the description that is displayed</small>

                <TextField
                  required
                  fullWidth
                  id="outlined-required"
                  label="Description"
                  defaultValue=""
                  sx={{ mt: 3 }}
                />
              </CardContent>
            </Card>

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Amount of Capital</div>

                <small className={style.cardDesc}>This will be the amount of capital that is displayed</small>

                <TextField
                  required
                  fullWidth
                  id="outlined-required"
                  label="Amount of Capital"
                  defaultValue=""
                  sx={{ mt: 3 }}
                />
              </CardContent>
            </Card>
          </Grid>
          <Grid item md={6} xs={12}>
            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Current Stock Market</div>
                <small className={style.cardDesc}>This will be the current stock market that is displayed</small>
                <div className={style.space}></div>
                <StockGrid />
              </CardContent>
            </Card>

            <div className={style.actionButtons}>
              <Button variant="outlined" className={style.cancelButton}>
                Cancel
              </Button>
              <Button variant="contained" className={style.saveButton}>
                Save
              </Button>
            </div>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default CreatePortfolio;
