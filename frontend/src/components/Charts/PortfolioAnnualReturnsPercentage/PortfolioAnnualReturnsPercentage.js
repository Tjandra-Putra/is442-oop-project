import React, { useEffect, useState } from "react";
import { Chart } from "react-google-charts";
import axios from "axios";

const PortfolioAnnualReturnsPercentage = ({ portfolioId }) => {
  const [initialData, setInitialDAta] = React.useState([["Year", "Returns"]]);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/portfolioStock/getAnnualReturns/${portfolioId}`)
      .then((res) => {
        let response = res.data.data;

        response.forEach((element) => {
          const year = Object.keys(element)[0];
          const returns = Object.values(element)[0];
          initialData.push([year, returns]);
        });
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  // Merge the initial data with annualReturns
  const options = {
    chart: {
      // title: "Company Performance",
      subtitle: "Sales, Expenses, and Profit: 2014-2017",
    },
  };

  return <Chart chartType="Bar" width="100%" height="400px" data={initialData} options={options} />;
};

export default PortfolioAnnualReturnsPercentage;
