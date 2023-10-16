import React, { useState } from "react";
import { Chart } from "react-google-charts";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select, { SelectChangeEvent } from "@mui/material/Select";

function LineChart() {
  const years = ["2022", "2023", "2024"];
  const [selectedYear, setSelectedYear] = useState("2023");

  const dataSets = {
    2022: {
      portfolio1: [100, 500, 300, 400, 500, 600, 700],
      portfolio2: [700, 300, 500, 400, 300, 200, 100],
    },
    2023: {
      portfolio1: [800, 1000, 600, 500, 400, 300, 200],
      portfolio2: [200, 100, 400, 500, 600, 700, 800],
    },
    2024: {
      portfolio1: [300, 100, 500, 600, 700, 800, 900],
      portfolio2: [900, 100, 700, 600, 500, 400, 300],
    },
  };

  const labels = ["January", "February", "March", "April", "May", "June", "July"];

  const selectedPortfolio1 = dataSets[selectedYear].portfolio1;
  const selectedPortfolio2 = dataSets[selectedYear].portfolio2;

  const data = [
    ["Month", "Dataset 1", "Dataset 2"],
    ...labels.map((label, index) => [`${label} ${selectedYear}`, selectedPortfolio1[index], selectedPortfolio2[index]]),
  ];

  const options = {
    title: "Chart with Dynamic Data",
    curveType: "function",
    legend: { position: "bottom" },
  };

  return (
    <div>
      <div style={{ textAlign: "right" }}>
        <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
          <InputLabel id="demo-select-small-label">Year</InputLabel>
          <Select
            labelId="demo-select-small-label"
            id="demo-select-small"
            value={selectedYear}
            label="Year"
            onChange={(e) => setSelectedYear(e.target.value)}
          >
            {years.map((year) => (
              <MenuItem key={year} value={year}>
                {year}
              </MenuItem>
            ))}
          </Select>
        </FormControl>
      </div>

      <Chart chartType="LineChart" width="100%" height="400px" data={data} options={options} />
    </div>
  );
}

export default LineChart;
