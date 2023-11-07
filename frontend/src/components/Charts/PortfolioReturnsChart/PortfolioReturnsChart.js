import React, { useState, useEffect } from "react";
import { Chart } from "react-google-charts";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import style from "./PortfolioReturnsChart.module.css";
import axios from "axios";

import { years, months, quarters } from "../../../data";

function LineChart({ portfolioId }) {
  const [selectedFilterType, setSelectedFilterType] = useState("yearly");
  const [selectedFromYear, setSelectedFromYear] = useState();
  const [selectedToYear, setSelectedToYear] = useState();
  const [selectedFromQuarter, setSelectedFromQuarter] = useState();
  const [selectedToQuarter, setSelectedToQuarter] = useState();
  const [selectedFromMonth, setSelectedFromMonth] = useState();
  const [selectedToMonth, setSelectedToMonth] = useState();

  const [dataQuarterly, setDataQuarterly] = useState([]);
  const [dataMonthly, setDataMonthly] = useState([]);

  const [data, setData] = useState([["Month", "2023", "2024"]]);

  useEffect(() => {
    // show all data
    axios.get(`http://localhost:8080/api/stockHistory/getMonthlyPortfolioValue/${portfolioId}`).then((res) => {
      console.log("=========== Monthly ===========");
      const response = res.data.data;
      const dataMap = {}; // Use an object to store segment data

      // Loop through the response to build the dataMap
      response.forEach((element) => {
        for (const year in element) {
          const yearData = element[year];
          dataMap[year] = dataMap[year] || {}; // Initialize the year if it doesn't exist
          for (const month in yearData) {
            dataMap[year][month] = yearData[month];
          }
        }
      });

      console.log(dataMap);

      let processData = [["Year-Month", "Returns"]];
      for (const year in dataMap) {
        const yearData = dataMap[year];

        // Add the year to the first row
        for (let month = 1; month < 13; month++) {
          let currentRow = [];
          let value = yearData[month];
          let date = new Date(year, month);
          let currentDate = new Date();
          if (date <= currentDate) {
            currentRow.push(date);
            currentRow.push(value);
            processData.push(currentRow);
          }
        }
      }

      console.log("============ PROCESS DATA============");
      console.log(processData);

      setData(processData);
    });
  }, []);

  if (selectedFilterType === "monthly") {
    if (selectedFromYear && selectedToYear && selectedFromMonth && selectedToMonth) {
      axios
        .get(`http://localhost:8080/api/stockHistory/getMonthlyPortfolioValue/${portfolioId}`)
        .then((res) => {
          console.log("=========== Monthly ===========");
          const response = res.data.data;
          const dataMap = {}; // Use an object to store segment data

          // Loop through the response to build the dataMap
          response.forEach((element) => {
            for (const year in element) {
              const yearData = element[year];
              dataMap[year] = dataMap[year] || {}; // Initialize the year if it doesn't exist
              for (const month in yearData) {
                dataMap[year][month] = yearData[month];
              }
            }
          });

          let processData = [["Year-Month", "Returns"]];
          for (const year in dataMap) {
            const yearData = dataMap[year];

            // Add the year to the first row
            for (let month = 1; month < 13; month++) {
              let currentRow = [];
              let value = yearData[month];
              var date = new Date(year, month);
              var fromYear = parseInt(selectedFromYear);
              var toYear = parseInt(selectedToYear);
              var fromMonth = parseInt(selectedFromMonth);
              var toMonth = parseInt(selectedToMonth);
              var fromDate = new Date(fromYear, fromMonth);
              var toDate = new Date(toYear, toMonth);
              console.log(fromYear, toYear, fromMonth, toMonth);
              if (date >= fromDate && date <= toDate && toDate >= fromDate) {
                currentRow.push(date);
                currentRow.push(value);
                processData.push(currentRow);
              }
            }
          }

          setData(processData);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  if (selectedFilterType === "quarterly") {
    if (selectedFromYear && selectedToYear && selectedFromQuarter && selectedToQuarter) {
      axios
        .get(`http://localhost:8080/api/stockHistory/getQuarterlyPortfolioValue/${portfolioId}`)
        .then((res) => {
          console.log("=========== Quarterly ===========");
          const response = res.data.data;
          const dataMap = {}; // Use an object to store segment data

          // Loop through the response to build the dataMap
          response.forEach((element) => {
            for (const year in element) {
              const yearData = element[year];
              dataMap[year] = dataMap[year] || {}; // Initialize the year if it doesn't exist
              for (const quarter in yearData) {
                dataMap[year][quarter] = yearData[quarter];
              }
            }
          });

          let processData = [["Year-Quarter", "Returns"]];
          for (const year in dataMap) {
            const yearData = dataMap[year];

            // Add the year to the first row
            for (let quarter = 1; quarter < 5; quarter++) {
              let currentRow = [];
              let value = yearData[quarter];
              var date = new Date(year, quarter);
              var fromYear = parseInt(selectedFromYear);
              var toYear = parseInt(selectedToYear);
              var fromQuarter = parseInt(selectedFromQuarter);
              var toQuarter = parseInt(selectedToQuarter);
              var fromDate = new Date(fromYear, fromQuarter);
              var toDate = new Date(toYear, toQuarter);
              console.log(fromYear, toYear, fromQuarter, toQuarter);
              if (date >= fromDate && date <= toDate && toDate >= fromDate) {
                currentRow.push(date);
                currentRow.push(value);
                processData.push(currentRow);
              }
            }
          }

          setData(processData);
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }

  const options = {
    // title: `Portfolio Returns`,
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
