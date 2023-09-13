import React from "react";
import style from "./TransactionDetails.module.css";
import AccessTimeIcon from '@mui/icons-material/AccessTime';
const TransactionDetails = () => {

    return (
        <div className={style.TransactionDetails}>
         <p>Internal Payment</p>
         <p>20-02-2023 12:30</p>
         <div className={style.TransactionDetailsIcon}>
            <AccessTimeIcon />
            Waiting
         </div>
         <p>150,000 USD</p>
        </div>
    )
}

export default TransactionDetails;