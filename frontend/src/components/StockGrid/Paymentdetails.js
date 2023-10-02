import React from "react";
import style from "./Paymentdetails.module.css";
import MoreVertIcon from '@mui/icons-material/MoreVert';

const PaymentDetails = () => {

    return (
        <div className={style.PaymentDetails}>
            <div className={style.firstRow}>
                <p style={{ marginLeft: '5px' }} >Charity</p>
                <MoreVertIcon />
            </div>
            
            <div className={style.secondRow}>
                <select className={style.mySelect} name="fruits">
                    <option value="apple">Apple</option>
                    <option value="banana">Banana</option>
                    <option value="cherry">Cherry</option>
                    <option value="date">Date</option>
                </select>
                <p className={style.mytext}>800USD</p>
           </div>
        </div>
    )
}

export default PaymentDetails;