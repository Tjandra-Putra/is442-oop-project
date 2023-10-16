import React , { useState } from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import { Button } from "@mui/material";
import {  Modal } from '@mui/material';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Grid from '@mui/material/Grid';
import TextField from '@mui/material/TextField';
import Chip from '@mui/material/Chip';
import CreditCardIcon from '@mui/icons-material/CreditCard';
import AttachMoneyIcon from '@mui/icons-material/AttachMoney';


import style from "./StockCard.module.css";

const StockCard = ({ name, value, percentage }) => {
  const [modalOpen, setModalOpen] = useState(false);
  const [amount, setAmount] = useState(0);

  const handleOpenModal = () => {
    setModalOpen(true);
  };

  const handleCloseModal = () => {
    setModalOpen(false);
  };

  const handleAmount = () => {
    if (amount <= 0) {
      // Open the modal here
      alert('Amount cannot be less than 0');
    } else {
      // make API call here
    }
  }

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
    <Box className={style.modal} style={{width:"300px" , height:"350px"}}>
          <Grid container spacing={2} style={{marginTop:"10px"}}> 
          {/* <div style={{ display: "flex"}}>
              <div style={{ display: "flex", alignItems: "center" , justifyContent: "space-between"}}>
                  <p style={{ margin: 0 }}> Wallet name </p>
                  <Chip label="Chip Filled" />
              </div>
          </div> */}
          <Grid item xs={6} md={6} lg={6}>
            <Typography id="modal-modal-title" variant="h6" component="h2" style={{marginBottom:"5px"}}>
            Review Top Up
            </Typography>
          </Grid>
          <Grid item xs={6} md={6} lg={6}>
            <div style={{ display: "flex", justifyContent: "flex-end" }}>
              <Chip icon={<AttachMoneyIcon />}label="Amount" />
            </div>
          </Grid>

          <Grid item xs={6} md={6} lg={6}>
          <p style={{ margin: 0 }}> Method </p>
          </Grid>
          <Grid item xs={6} md={6} lg={6}>
          <div style={{ display: "flex", justifyContent: "flex-end" }}>
            <Chip icon={<CreditCardIcon />}label="Mastercard" />
          </div>
          </Grid>

          <Grid item xs={8} md={8} lg={8} >
            <p style={{ margin: 0 }}> Top-up amount </p>
          </Grid>
          <Grid item xs={4} md={4} lg={4} >
          <div style={{ display: "flex", justifyContent: "flex-end" }}>
          <TextField
            id="standard-number"
            type="number"
            InputLabelProps={{
              shrink: true,
            }}
            variant="standard"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
      
          />
          </div>

          </Grid>
    
          

          <Grid item xs={12} md={12} lg={12} >
            <Box textAlign='center'> 
              <Button variant="outlined" onClick={handleAmount} aria-labelledby="modal-modal-title"> Top up </Button>
            </Box>
          </Grid>
          
          </Grid>
        </Box>
      </Modal>
      
      </>
  );
    
};

export default StockCard;
