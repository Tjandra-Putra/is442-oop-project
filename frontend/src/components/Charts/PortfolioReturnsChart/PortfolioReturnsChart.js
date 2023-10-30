import React, { useState } from "react";
import { Chart } from "react-google-charts";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import style from "./PortfolioReturnsChart.module.css";

import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import FormLabel from "@mui/material/FormLabel";
import NativeSelect from "@mui/material/NativeSelect";
import Box from "@mui/material/Box";

import { years, months, quarters } from "../../../data";

function LineChart() {
  const [selectedFilterType, setSelectedFilterType] = useState("yearly");
  const [selectedFromYear, setSelectedFromYear] = useState();
  const [selectedToYear, setSelectedToYear] = useState();
  const [selectedFromQuarter, setSelectedFromQuarter] = useState();
  const [selectedToQuarter, setSelectedToQuarter] = useState();
  const [selectedFromMonth, setSelectedFromMonth] = useState();
  const [selectedToMonth, setSelectedToMonth] = useState();

  const data = [
    ["Day", "Guardians of the Galaxy", "The Avengers", "Transformers: Age of Extinction"],
    [1, 37.8, 80.8, 41.8],
    [2, 30.9, 69.5, 32.4],
    [3, 25.4, 57, 25.7],
    [4, 11.7, 18.8, 10.5],
    [5, 11.9, 17.6, 10.4],
    [6, 8.8, 13.6, 7.7],
    [7, 7.6, 12.3, 9.6],
    [8, 12.3, 29.2, 10.6],
    [9, 16.9, 42.9, 14.8],
    [10, 12.8, 30.9, 11.6],
    [11, 5.3, 7.9, 4.7],
    [12, 6.6, 8.4, 5.2],
    [13, 4.8, 6.3, 3.6],
    [14, 4.2, 6.2, 3.4],
  ];

  const options = {
    title: `Portfolio Returns`,
    curveType: "function",
    legend: { position: "bottom" },
    hAxis: {
      slantedText: true, // Set this property to true for vertical labels
      // slantedTextAngle: 90, // Set the angle to 90 degrees for vertical labels
    },
  };

  const renderInputFields = () => {
    // ==================== Yearly ====================
    if (selectedFilterType === "yearly") {
      return (
        <React.Fragment>
          <FormControl sx={{ minWidth: 140, mr: 2 }} size="small">
            <InputLabel id="demo-select-small-label">Year (From)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedFromYear}
              label="Year (From)"
              onChange={(e) => setSelectedFromYear(e.target.value)}
              variant="standard"
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ minWidth: 140 }} size="small">
            <InputLabel id="demo-select-small-label">Year (To)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedToYear}
              label="Year (From)"
              onChange={(e) => setSelectedToYear(e.target.value)}
              variant="standard"
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </React.Fragment>
      );
    }
    // ==================== Quarterly ====================
    else if (selectedFilterType === "quarterly") {
      return (
        <React.Fragment>
          <FormControl sx={{ minWidth: 140, mr: 2 }} size="small">
            <InputLabel id="quarterly">Year (From)</InputLabel>
            <Select
              labelId="quarterly"
              id="demo-select-small"
              value={selectedFromYear}
              label="Year (From)"
              onChange={(e) => setSelectedFromYear(e.target.value)}
              variant="standard"
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ minWidth: 140, mr: 2 }} size="small">
            <InputLabel id="demo-select-small-label">Year (To)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedToYear}
              label="Year (From)"
              onChange={(e) => setSelectedToYear(e.target.value)}
              variant="standard"
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ minWidth: 140, mr: 2 }} size="small">
            <InputLabel id="demo-select-small-label">Quarter (From)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedFromQuarter}
              label="Quarter (From)"
              onChange={(e) => setSelectedFromQuarter(e.target.value)}
              variant="standard"
            >
              {quarters.map((quarter) => (
                <MenuItem key={quarter.value} value={quarter.value}>
                  {quarter.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ minWidth: 140 }} size="small">
            <InputLabel id="demo-select-small-label">Quarter (To)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedToQuarter}
              label="Quarter (To)"
              onChange={(e) => setSelectedToQuarter(e.target.value)}
              variant="standard"
            >
              {quarters.map((quarter) => (
                <MenuItem key={quarter.value} value={quarter.value}>
                  {quarter.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </React.Fragment>
      );
    }
    // ==================== Monthly ====================
    else if (selectedFilterType == "monthly") {
      return (
        <React.Fragment>
          <FormControl sx={{ minWidth: 140, mr: 2 }} size="small">
            <InputLabel id="demo-select-small-label">Year (From)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedFromYear}
              label="Year (From)"
              onChange={(e) => setSelectedFromYear(e.target.value)}
              variant="standard"
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ minWidth: 140, mr: 2 }} size="small">
            <InputLabel id="demo-select-small-label">Year (To)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedToYear}
              label="Year (From)"
              onChange={(e) => setSelectedToYear(e.target.value)}
              variant="standard"
            >
              {years.map((year) => (
                <MenuItem key={year} value={year}>
                  {year}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ minWidth: 140, mr: 2 }} size="small">
            <InputLabel id="demo-select-small-label">Month (From)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedFromMonth}
              label="Year (From)"
              onChange={(e) => setSelectedFromMonth(e.target.value)}
              variant="standard"
            >
              {months.map((month) => (
                <MenuItem key={month.name} value={month.value}>
                  {month.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl sx={{ minWidth: 140 }} size="small">
            <InputLabel id="demo-select-small-label">Month (To)</InputLabel>
            <Select
              labelId="demo-select-small-label"
              id="demo-select-small"
              value={selectedToMonth}
              label="Year (From)"
              onChange={(e) => setSelectedToMonth(e.target.value)}
              variant="standard"
            >
              {months.map((month) => (
                <MenuItem key={month.name} value={month.value}>
                  {month.name}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </React.Fragment>
      );
    }
  };

  return (
    <div>
      <div className={style.inputGroups}>
        <div className={style.selectInputsDisplay}>
          <div>
            <input
              type="radio"
              value="yearly"
              checked={selectedFilterType === "yearly"}
              onChange={() => setSelectedFilterType("yearly")}
              id="yearly"
            />
            <label for="yearly">Yearly</label>
          </div>
          <div>
            <input
              type="radio"
              value="quarterly"
              checked={selectedFilterType === "quarterly"}
              onChange={() => setSelectedFilterType("quarterly")}
              id="quarterly"
            />
            <label for="quarterly">Quarterly</label>
          </div>
          <div>
            <input
              type="radio"
              value="quarterly"
              checked={selectedFilterType === "monthly"}
              onChange={() => setSelectedFilterType("monthly")}
              id="monthly"
            />
            <label for="monthly">Monthly</label>
          </div>
        </div>

        <div className={style.selectedInputsDisplay}>{renderInputFields()}</div>
      </div>

      <Chart chartType="LineChart" width="100%" height="400px" data={data} options={options} />
    </div>
  );
}

export default LineChart;
