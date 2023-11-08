import React, { useEffect, useState } from "react";
import { Chart } from "react-google-charts";
import axios from "axios";
import { useSelector } from "react-redux";

function PortfolioStocksAllocationChart({ portfolioId }) {
  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

  const [portfolioStocksAllocation, setPortfolioStocksAllocation] = useState([]);
  const [data, setData] = useState([["Task", "Hours per Day"]]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/portfolioStock/getPortfolioStockAllocation/${portfolioId}`,
          {
            headers: {
              Authorization: `Bearer ${user?.token}`, // Replace "yourTokenHere" with your actual token
            },
          }
        );
        const responseData = response.data;

        if (responseData && responseData.data) {
          responseData.data.forEach((stock) => {
            setData((prev) => [...prev, [stock.allocationName, stock.percentage]]);
          });
        } else {
          // Handle the case where no data is available
          console.log("No data available");
        }
      } catch (error) {
        // Handle the error, e.g., show an error message
        console.error("Error fetching data:", error);
      }
    };

    fetchData();
  }, [portfolioId]);

  const options = {
    // title: "Stocks allocation %",
  };

  return <Chart chartType="PieChart" data={data} options={options} width={"500px"} height={"300px"} />;
}

export default PortfolioStocksAllocationChart;
