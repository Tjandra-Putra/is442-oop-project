import React, { useState } from "react";
import { Chart } from "react-google-charts";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";

function LineChart() {
  const years = ["2022", "2023", "2024"];
  const [selectedYear, setSelectedYear] = useState("2023");

  const filterType = ["Yearly", "Quarterly", "Monthly"];
  const [selectedFilterType, setSelectedFilterType] = useState("Yearly");

  const dataSets = {
    2022: {
      portfolio1: {
        Yearly: [100, 500, 300, 400, 500, 600, 700],
        Quarterly: [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200],
        Monthly: [100, 200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200],
      },
      portfolio2: {
        Yearly: [700, 300, 500, 400, 300, 200, 100],
        Quarterly: [700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800],
        Monthly: [700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800],
      },
    },
    2023: {
      portfolio1: {
        Yearly: [800, 1000, 600, 500, 400, 300, 200],
        Quarterly: [800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900],
        Monthly: [800, 900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900],
      },
      portfolio2: {
        Yearly: [200, 100, 400, 500, 600, 700, 800],
        Quarterly: [200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300],
        Monthly: [200, 300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300],
      },
    },
    2024: {
      portfolio1: {
        Yearly: [300, 100, 500, 600, 700, 800, 900],
        Quarterly: [300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400],
        Monthly: [300, 400, 500, 600, 700, 800, 900, 1000, 1100, 1200, 1300, 1400],
      },
      portfolio2: {
        Yearly: [900, 100, 700, 600, 500, 400, 300],
        Quarterly: [900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000],
        Monthly: [900, 1000, 1100, 1200, 1300, 1400, 1500, 1600, 1700, 1800, 1900, 2000],
      },
    },
  };

  const labels = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "Novemeber",
    "December",
  ];

  const selectedData = dataSets[selectedYear];

  const getFilteredData = (filterType) => {
    if (filterType === "Yearly") {
      return labels.map((label, index) => [
        `${label}`,
        // `${label} ${selectedYear}`,
        selectedData.portfolio1.Yearly[index],
        selectedData.portfolio2.Yearly[index],
      ]);
    } else if (filterType === "Quarterly") {
      const quarterlyData = [];
      for (let i = 0; i < 12; i += 3) {
        const quarterLabel = `Q${i / 3 + 1} ${selectedYear}`;
        const portfolio1Sum = selectedData.portfolio1.Quarterly.slice(i, i + 3).reduce((a, b) => a + b, 0);
        const portfolio2Sum = selectedData.portfolio2.Quarterly.slice(i, i + 3).reduce((a, b) => a + b, 0);
        quarterlyData.push([quarterLabel, portfolio1Sum, portfolio2Sum]);
      }
      return quarterlyData;
    } else if (filterType === "Monthly") {
      return labels.map((label, index) => [
        `${label}`,
        selectedData.portfolio1.Monthly[index],
        selectedData.portfolio2.Monthly[index],
      ]);
    }
  };

  const data = [["Month", "Dataset 1", "Dataset 2"], ...getFilteredData(selectedFilterType)];

  const options = {
    title: `Portfolio Performance for ${
      selectedFilterType === "Yearly" ? selectedYear : selectedFilterType
    } ${selectedYear}`,
    curveType: "function",
    legend: { position: "bottom" },
    hAxis: {
      slantedText: true, // Set this property to true for vertical labels
      // slantedTextAngle: 90, // Set the angle to 90 degrees for vertical labels
    },
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

        <FormControl sx={{ m: 1, minWidth: 120 }} size="small">
          <InputLabel id="demo-select-small-label">Filter By</InputLabel>
          <Select
            labelId="demo-select-small-label"
            id="demo-select-small"
            value={selectedFilterType}
            label="Filter By"
            onChange={(e) => setSelectedFilterType(e.target.value)}
          >
            {filterType.map((filter) => (
              <MenuItem key={filter} value={filter}>
                {filter}
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
