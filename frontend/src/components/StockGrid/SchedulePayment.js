import React from "react";
import style from "./SchedulePayment.module.css";
import RestoreIcon from '@mui/icons-material/Restore';
import PaymentDetails from "./Paymentdetails";

const SchedulePayment = () => {

    return (
        <>
        <div className={style.topWrapper}>
            <p> Scheduled payments </p>
                <RestoreIcon />
        </div>
        
        <div className={style.PaymentDetails}>
             <PaymentDetails />
             <PaymentDetails />
             <PaymentDetails />
            
        </div>
        
        </>
        

    )
}

export default SchedulePayment;