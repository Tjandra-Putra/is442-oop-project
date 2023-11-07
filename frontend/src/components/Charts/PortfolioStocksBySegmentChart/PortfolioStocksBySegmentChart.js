import React, { useState, useEffect } from "react";
import { Chart } from "react-google-charts";
import axios from "axios";

function PortfolioStocksBySegmentChart({ portfolioId }) {
  const [initialData, setInitialData] = useState([["Segment", "Percentage"]]);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/portfolioStock/getPortfolioStockIndustryAllocation/${portfolioId}`)
      .then((res) => {
        let response = res.data.data;

        console.log(response);

        const dataMap = {}; // Use an object to store segment data

        response.forEach((element) => {
          const segment = element.allocationName;
          const percentage = element.percentage;

          if (dataMap[segment]) {
            // If the segment exists, add the percentage to it
            dataMap[segment] += percentage;
          } else {
            // Otherwise, create a new entry
            dataMap[segment] = percentage;
          }
        });

        // Convert the object back to an array for the chart
        const chartData = [["Segment", "Percentage"]];
        for (const segment in dataMap) {
          chartData.push([segment, dataMap[segment]]);
        }

        setInitialData(chartData); // Update the state with the new data
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const options = {
    // title: "Stocks by Segment",
  };

  return <Chart chartType="PieChart" data={initialData} options={options} width={"500px"} height={"300px"} />;
}

export default PortfolioStocksBySegmentChart;
