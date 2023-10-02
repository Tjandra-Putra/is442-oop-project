import React , { useState } from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import { Button } from "@mui/material";
import {  Modal } from '@mui/material';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';


import style from "./StockCard.module.css";

const StockCard = ({ name, value, percentage }) => {
  const [modalOpen, setModalOpen] = useState(false);

  const handleOpenModal = () => {
    setModalOpen(true);
  };

  const handleCloseModal = () => {
    setModalOpen(false);
  };

  return (
    <><Card className={style.portfolioCardWrapper} sx={{ maxWidth: 305 }}>
      <CardContent>
        <div className={style.stockHeader}>
          <p>{name}</p>
          {name == "Wallet" ?  <Button variant="outlined" onClick={handleOpenModal} aria-labelledby="modal-modal-title"
  aria-describedby="modal-modal-description">
                Top Up
              </Button> : null}
        </div>
        <div>{value}</div>
      </CardContent>
    </Card>
    
    <Modal open={modalOpen} onClose={handleCloseModal}>
    <Box className={style.modal}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
           Review Top Up
          </Typography>
          <Grid container>
          <Grid item xs={6} md={6} lg={6} >
            <p>Method</p>
          </Grid>
          <Grid item xs={6} md={6} lg={6} >
            <p>Top up here please please</p>
          </Grid>
          
          </Grid>
        </Box>
      </Modal>
      
      </>
  );
    
};

export default StockCard;
