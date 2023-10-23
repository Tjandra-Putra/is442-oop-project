import React, { useEffect, useState } from "react";
import { Chart } from "react-google-charts";
import axios from "axios";

function PortfolioStocksAllocationChart({ portfolioId }) {
  const [portfolioStocksAllocation, setPortfolioStocksAllocation] = useState([]);
  const [data, setData] = useState([["Task", "Hours per Day"]]);

  useEffect(() => {
    // get
    axios
      .get(`http://localhost:8080/api/portfolioStock/getPortfolioStockAllocation/${portfolioId}`)
      .then((res) => {
        console.log(res.data);

        // iterate and push to data
        res.data.data.forEach((stock) => {
          setData((prev) => [...prev, [stock.allocationName, stock.percentage]]);
        });
      })
      .then((err) => {
        console.log(err);
      });
  }, []);

  // const data = [
  //   ["Task", "Hours per Day"],
  //   ["Work", 11],
  //   ["Eat", 2],
  //   ["Commute", 2],
  //   ["Watch TV", 2],
  //   ["Sleep", 7],
  // ];

  const options = {
    // title: "Stocks allocation %",
  };

  return <Chart chartType="PieChart" data={data} options={options} width={"500px"} height={"300px"} />;
}

export default PortfolioStocksAllocationChart;
