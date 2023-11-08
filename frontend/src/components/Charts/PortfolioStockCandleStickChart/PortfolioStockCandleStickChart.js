import React, { useEffect, useState } from "react";
import { Chart } from "react-google-charts";
import axios from "axios";
import { Typography, Select, MenuItem } from "@mui/material/";
import Loader from "../../Layout/Loader/Loader";
import { useSelector } from "react-redux";

const PortfolioStockCandleStickChart = React.memo(({ ticker }) => {
  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

  // const [initialData, setInitialData] = useState([
  // adjustprice is closingPrice
  //   ["date", "openPrice", "lowPrice", "highPrice", "adjustedClosingPrice"],
  //   // ["1999-11-12 00:00:00.0", 87.75, 86.75, 97.73, 0.6867],
  // ]);
  const [initialData, setInitialData] = useState([["date", "openPrice", "closePrice", "lowPrice", "highPrice"]]);

  const [showAllData, setShowAllData] = useState(0); // [true, false
  const [dataSetsToShow, setDataSetsToShow] = useState(10);
  const [loader, setLoader] = useState(true); // Set the loader initially to true

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/stockHistory/getWeeklyHistoryByTicker/${ticker}`, {
        headers: {
          Authorization: `Bearer ${user?.token}`, // Replace "yourTokenHere" with your actual token
        },
      })
      .then((res) => {
        let responseData = res.data.data; // Define responseData here
        responseData = responseData.reverse(); // Reverse the data so that the most recent data is at the end

        setShowAllData(responseData.length);

        const formattedData = [];
        for (let i = 0; i < responseData.length && i < dataSetsToShow; i++) {
          const item = responseData[i];
          // formattedData.push([item.date, item.openPrice, item.adjClosePrice, item.lowPrice, item.highPrice]);
          formattedData.push([item.date, item.lowPrice, item.openPrice, item.adjClosePrice, item.highPrice]);
          console.log(formattedData);
        }

        formattedData.unshift(["date", "lowPrice", "openPrice", "closePrice", "highPrice"]);

        // formattedData.unshift(["date", "openPrice", "closePrice", "lowPrice", "highPrice"]);
        setInitialData(formattedData);
        setLoader(false); // Set the loader to false when the request is completed
      })
      .catch((error) => {
        console.error("Error fetching data:", error);
        setLoader(false); // Set the loader to false in case of an error
      });
  }, [ticker, dataSetsToShow]);

  const options = {
    legend: "none",
  };

  const handleDataSetsChange = (event) => {
    setDataSetsToShow(event.target.value);
  };

  return loader ? (
    <p>Loading...</p>
  ) : (
    <>
      <div style={{ display: "flex", justifyContent: "space-between" }}>
        <Typography id="modal-modal-title" variant="h6" component="h2">
          Stock Name: {ticker}
        </Typography>
        <Select value={dataSetsToShow} onChange={handleDataSetsChange}>
          <MenuItem value={10}>Show 10 Data Sets</MenuItem>
          <MenuItem value={20}>Show 20 Data Sets</MenuItem>
          <MenuItem value={30}>Show 30 Data Sets</MenuItem>
          <MenuItem value={40}>Show 40 Data Sets</MenuItem>
          <MenuItem value={50}>Show 50 Data Sets</MenuItem>
          <MenuItem value={showAllData}>Show All Data Sets</MenuItem>
        </Select>
      </div>
      <Chart chartType="CandlestickChart" width="100%" height="400px" data={initialData} options={options} />
    </>
  );
});

export default PortfolioStockCandleStickChart;
