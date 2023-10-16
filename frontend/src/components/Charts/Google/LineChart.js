import React from "react";
import { Chart } from "react-google-charts";
import faker from "faker";

function LineChart() {
  // Generate data in the format you specified
  const labels = ["January", "February", "March", "April", "May", "June", "July"];
  const dataset1 = [100, 200, 700, 400, 500, 600, 700];
  const dataset2 = [700, 200, 500, 400, 300, 200, 100];

  // Create a DataTable with headers and rows
  const data = [
    ["Month", "Dataset 1", "Dataset 2"],
    ...labels.map((month, index) => [month, dataset1[index], dataset2[index]]),
  ];

  const options = {
    title: "Portfolio Returns Over Time",
    curveType: "function",
    legend: { position: "bottom" },
  };

  return <Chart chartType="LineChart" width="100%" height="400px" data={data} options={options} />;
}
export default LineChart;
