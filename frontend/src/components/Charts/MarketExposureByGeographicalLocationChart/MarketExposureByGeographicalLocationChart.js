import React, { useState } from "react";
import { Chart } from "react-google-charts";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { Grid } from "@mui/material";

function MarketExposureByGeographicalLocationChart() {
  const countries = ["USA", "Germany", "United States", "Brazil", "Canada", "France", "RU"];

  const [selectedCountry, setSelectedCountries] = useState("USA");

  const dataSets = [
    ["Country", "Popularity"],
    ["Germany", 200],
    ["United States", 300],
    ["Brazil", 400],
    ["Canada", 500],
    ["France", 600],
    ["RU", 700],
  ];

  const options = {
    // title: "Market Exposure by Location: " + selectedCountry,

    legend: { position: "bottom" },
  };

  return (
    <div>
      {/* <div style={{ textAlign: "right" }}>
        <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
          <InputLabel id="demo-select-small-label">Countries</InputLabel>
          <Select
            labelId="demo-select-small-label"
            id="demo-select-small"
            value={selectedCountry}
            label="Year"
            onChange={(e) => selectedCountry(e.target.value)}
          >
            {countries.map((country) => (
              <MenuItem key={country} value={country}>
                {country}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </div> */}

      <Grid container spacing={4}>
        <Grid item md={6} xs={12}>
          <Chart chartType="PieChart" data={dataSets} options={options} width={"100%"} height={"400px"} />
        </Grid>
        <Grid item md={6} xs={12}>
          <Chart
            chartEvents={[
              {
                eventName: "select",
                callback: ({ chartWrapper }) => {
                  const chart = chartWrapper.getChart();
                  const selection = chart.getSelection();
                  if (selection.length === 0) return;
                  const region = dataSets[selection[0].row + 1];
                  console.log("Selected : " + region);
                },
              },
            ]}
            chartType="GeoChart"
            width="100%"
            height="400px"
            data={dataSets}
            options={options}
          />
        </Grid>
      </Grid>
    </div>
  );
}

export default MarketExposureByGeographicalLocationChart;
