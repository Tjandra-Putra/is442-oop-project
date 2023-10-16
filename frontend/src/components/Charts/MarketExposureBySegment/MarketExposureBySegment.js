import React from "react";
import { Chart } from "react-google-charts";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import { Grid } from "@mui/material";

const MarketExposureBySegment = () => {
  const [selectedIndustry, setSelectedIndustry] = React.useState("USA");

  const dataSets = [
    ["Country", "Popularity"],
    ["Germany", 200],
    ["United States", 300],
    ["Brazil", 400],
    ["Canada", 500],
    ["France", 600],
    ["RU", 10000],
  ];

  const options = {
    title: "Market Exposure by Industry: " + selectedIndustry,

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
          <Chart chartType="BarChart" data={dataSets} options={options} width={"100%"} height={"400px"} />
        </Grid>
      </Grid>
    </div>
  );
};

export default MarketExposureBySegment;
