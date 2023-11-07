import React, { useEffect, useState } from "react";
import { Chart } from "react-google-charts";
import axios from "axios";
import { Typography } from "@mui/material/";

const PortfolioStockCandleStickChart = React.memo(({ ticker }) => {
  const [initialData, setInitialData] = useState([
    ["date", "openPrice", "lowPrice", "highPrice", "adjustedClosingPrice"],
    ["1999-11-12 00:00:00.0", 87.75, 86.75, 97.73, 0.6867],
  ]);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/stockHistory/getHistoryByWeekTicker/${ticker}`)
      .then((res) => {
        const responseData = res.data.data;

        // Map the received data to match the structure of initialData
        const formattedData = responseData.map((item) => [
          item.date, // date
          item.openPrice, // openPrice
          item.lowPrice, // lowPrice
          item.highPrice, // highPrice
          item.adjClosePrice, // adjustedClosingPrice
        ]);

        // Add the header row to the formatted data
        formattedData.unshift(["date", "openPrice", "lowPrice", "highPrice", "adjustedClosingPrice"]);

        setInitialData(formattedData);
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
      });
  }, [ticker]);

  const options = {
    legend: "none",
  };

  return (
    <>
      <Typography id="modal-modal-title" variant="h6" component="h2">
        Stock Name: {ticker}
      </Typography>
      <Chart chartType="CandlestickChart" width="100%" height="400px" data={initialData} options={options} />
    </>
  );
});

export default PortfolioStockCandleStickChart;
