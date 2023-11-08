import React, { useState, useEffect } from "react";
import { Chart } from "react-google-charts";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { Grid } from "@mui/material";
import axios from "axios";
import { useSelector } from "react-redux";

function MarketExposureByCountryChart({ portfolioId }) {
  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

  const [initialData, setInitialData] = useState([]);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/portfolioStock/getPortfolioStockCountryAllocation/${portfolioId}`, {
        headers: {
          Authorization: `Bearer ${user?.token}`, // Replace "yourTokenHere" with your actual token
        },
      })
      .then((res) => {
        let response = res.data.data;

        const dataMap = {}; // Use an object to store segment data

        response.forEach((element) => {
          const allocationName = element.allocationName;
          // if (allocationName === "US") {
          //   allocationName = "United States";
          // }

          const percentage = element.percentage;

          if (dataMap[allocationName]) {
            // If the segment exists, add the percentage to it
            dataMap[allocationName] += percentage;
          } else {
            // Otherwise, create a new entry
            dataMap[allocationName] = percentage;
          }
          // Convert the object back to an array for the chart
          const chartData = [["Allocation Name", "Percentage"]];
          for (const segment in dataMap) {
            chartData.push([segment, dataMap[segment]]);
          }

          setInitialData(chartData); // Update the state with the new data
        });
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const options = {
    legend: { position: "bottom" },
  };

  // Define GeoChart-specific options
  const geoChartOptions = {
    region: "world", // Display the whole world
    displayMode: "regions", // Display countries/regions
    resolution: "countries", // Display countries
  };

  return (
    <div>
      <Grid container spacing={4}>
        <Grid item md={12} xs={12}>
          <Chart chartType="PieChart" data={initialData} options={options} width={"100%"} height={"400px"} />
        </Grid>
        {/* <Grid item md={6} xs={12}>
          <Chart
            chartEvents={[
              {
                eventName: "select",
                callback: ({ chartWrapper }) => {
                  const chart = chartWrapper.getChart();
                  const selection = chart.getSelection();
                  if (selection.length === 0) return;
                  const region = initialData[selection[0].row + 1];
                  console.log("Selected : " + region);
                },
              },
            ]}
            chartType="GeoChart"
            width="100%"
            height="400px"
            data={initialData} // Replace with your GeoChart data
            options={geoChartOptions} // Use GeoChart-specific options
          />
        </Grid> */}
      </Grid>
    </div>
  );
}

export default MarketExposureByCountryChart;
