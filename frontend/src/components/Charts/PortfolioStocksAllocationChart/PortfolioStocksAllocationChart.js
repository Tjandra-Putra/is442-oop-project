import React from "react";
import { Chart } from "react-google-charts";

function PortfolioStocksAllocationChart() {
  const data = [
    ["Task", "Hours per Day"],
    ["Work", 11],
    ["Eat", 2],
    ["Commute", 2],
    ["Watch TV", 2],
    ["Sleep", 7],
  ];

  const options = {
    // title: "Stocks allocation %",
  };

  return <Chart chartType="PieChart" data={data} options={options} width={"500px"} height={"300px"} />;
}

export default PortfolioStocksAllocationChart;
