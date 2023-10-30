import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { Box, Stack, TextField, Button, Grid, CardContent, Card, Typography, Container } from "@mui/material";
import toast from "react-hot-toast";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import style from "./CreatePortfolio.module.css";

const CreatePortfolio = () => {
  const navigate = useNavigate();

  const [selectedRows, setSelectedRows] = useState([]);
  const [portfolioName, setPortfolioName] = useState("");
  const [portfolioDescription, setPortfolioDescription] = useState("");
  const [portfolioCapital, setPortfolioCapital] = useState("");
  const [stockMarket, setStockMarket] = useState([]);
  const [stockColumns, setStockColumns] = useState([]);
  const [stockRows, setStockRows] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [portfolioId, setPortfolioId] = useState(null);
  const [buyDate, setBuyDate] = useState("");
  const [quantityValues, setQuantityValues] = useState(Object.fromEntries(stockRows.map((row) => [row.id, 1])));

  useEffect(() => {
    console.log("Selected Rows Updated:", selectedRows);
  }, [selectedRows]);

  const notifyError = (message) => toast.error(message, { duration: 5000 });
  const notifySuccess = (message) => toast.success(message, { duration: 5000 });

  // get total price of selected stocks
  const getTotalPrice = () => {
    let totalPrice = 0;
    selectedRows.forEach((row) => {
      totalPrice += row.Total;
    });
    return totalPrice;
  };

  const handleQuantityChange = async (id, newValue) => {
    newValue = parseInt(newValue, 10);

    if (!isNaN(newValue) && newValue >= 1) {
      const newSelectedRows = selectedRows.map((row) => {
        if (row.id === id) {
          return {
            ...row,
            Quantity: newValue,
            Total: newValue * row.Price,
          };
        }
        return row;
      });

      setSelectedRows(newSelectedRows);

      // Update the quantityValues state only for the specific row
      setQuantityValues((prevValues) => ({
        ...prevValues,
        [id]: newValue,
      }));
    }
  };

  const handleBuyDateChange = (id, newValue) => {
    const newSelectedRows = selectedRows.map((row) => {
      if (row.id === id) {
        return {
          ...row,
          BuyDate: newValue,
        };
      }
      return row;
    });
    setSelectedRows(newSelectedRows);
  };

  // Material UI DataGrid
  const selectedStockColumns = [
    { field: "id", headerName: "No.", width: 50 },
    {
      field: "Ticker",
      headerName: "Ticker",
      width: 80,
    },
    {
      field: "Price",
      headerName: "Price",
      type: "number",
      width: 80,
    },
    {
      field: "Quantity",
      headerName: "Quantity",
      renderCell: (params) => (
        <input
          type="number"
          value={params.row.Quantity ? params.row.Quantity : 1}
          onChange={(e) => handleQuantityChange(params.row.id, e.target.value)}
          className={style.quantityInput}
        />
      ),
      width: 80,
    },
    {
      field: "BuyDate",
      headerName: "Buy Date",
      renderCell: (params) => {
        const today = new Date();
        const formattedToday = today.toISOString().split("T")[0]; // Format as YYYY-MM-DD

        return (
          <input
            type="date"
            value={params.row.BuyDate ? params.row.BuyDate : formattedToday}
            InputLabelProps={{
              shrink: true,
            }}
            onChange={(e) => handleBuyDateChange(params.row.id, e.target.value)}
            className={style.quantityInput}
            fullWidth
          />
        );
      },
      width: 120, // Adjust the width as needed
    },
    {
      field: "Total",
      headerName: "Total",
      type: "number",
      width: 70,
    },
  ];

  // refresh page
  const onResetSelectedRows = () => {
    window.location.reload();
  };

  // return state as one object including quantity, portfolio name, description, capital
  const getSelectedRows = () => {
    const selectedRowsWithQuantity = selectedRows.map((row) => {
      return {
        ...row,
        Quantity: quantityValues[row.id],
      };
    });

    return {
      selectedRows: selectedRowsWithQuantity,
      // portfolioName: portfolioName,
      // portfolioDescription: portfolioDescription,
      // portfolioCapital: portfolioCapital,
    };
  };

  // submit form with validation
  const submitFormHandler = () => {
    if (portfolioCapital.length === 0) {
      notifyError("Please enter a valid amount of capital");
      return;
    } else if (portfolioCapital < 0) {
      notifyError("Please enter a valid amount of capital");
      return;
    } else if (portfolioName.length === 0) {
      notifyError("Please enter a portfolio name");
      return;
    } else if (portfolioDescription.length === 0) {
      notifyError("Please enter a description");
      return;
    } else if (selectedRows.length === 0) {
      notifyError("Please select at least one stock");
      return;
    }

    const userId = 1;

    const postData1 = {
      data: [
        {
          fieldName: "capitalAmt",
          value: portfolioCapital,
        },
        {
          fieldName: "description",
          value: portfolioDescription,
        },
        { fieldName: "portfolioName", value: portfolioName },
      ],
    };

    // send the portfolio name, description, capital
    axios
      .post("http://localhost:8080/api/portfolio/addPortfolio/" + userId, postData1)
      .then((res) => {
        console.log(res.data);

        if (res.data && res.data.data === null) {
          notifyError(res.data.message);
        } else {
          notifySuccess(res.data.message);

          // ================ send selected stock data to backend =================
          const postData2 = {
            data: [],
          };

          console.log("SUBMIT === selectedRows ===");
          selectedRows.forEach((row) => {
            console.log(row);

            const formattedData = [
              {
                fieldname: "ticker",
                value: row.Ticker,
              },
              {
                fieldname: "price",
                value: row.Price,
              },
              {
                fieldname: "buyDate",
                value: row.BuyDate, // You can set the buyDate to a specific value or get it dynamically
              },
            ];

            postData2.data.push(formattedData);
          });

          console.log("POSTDATA2 === postData2 ===" + postData2);

          const portfolioId = res.data.data.portfolioId;

          // Send the selected stocks
          axios
            .post("http://localhost:8080/api/portfolioStock/addPortfolioStock/" + portfolioId, postData2)
            .then((res) => {
              console.log(res.data);
            })
            .catch((err) => console.log(err));

          navigate(`/portfolio/${portfolioId}`);
        }
      })
      .catch((err) => {
        notifyError(err.response.data.message);
      });
  };

  useEffect(() => {
    // http://localhost:8080/api/stockInfo/getStockInfo/ticker/{ticker}
    // get current stock market
    axios
      .get("http://localhost:8080/api/stock/getStock")
      .then((res) => {
        let stockData = res.data.data;
        setStockMarket(stockData);
        // console.log(res.data.data);

        const generatedStockRows = stockData.map((stock, index) => ({
          id: index + 1, // Start from 1
          Ticker: stock.ticker,
          Name: stock.name,
        }));

        setStockRows(generatedStockRows);
        setStockColumns([
          { field: "id", headerName: "No.", width: 100 },
          { field: "Ticker", headerName: "Ticker", width: 100 },
          { field: "Name", headerName: "Name", width: 230 },
        ]);
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const filteredStockRows = stockRows.filter((row) => {
    const searchString = searchQuery.toLowerCase();
    return row.Ticker.toLowerCase().includes(searchString) || row.Name.toLowerCase().includes(searchString);
  });

  const getStockPrice = (ticker) => {
    return axios
      .get("http://localhost:8080/api/StockInfo/getStockInfo/ticker/" + ticker)
      .then((res) => {
        console.log("== getStockPrice ==");
        const todayPrice = res.data.data[0]?.todayPrice || 0;
        console.log(todayPrice); // Logging for verification
        return todayPrice;
      })
      .catch((error) => {
        console.error("Error fetching stock price:", error);
        return 0; // Return 0 in case of an error
      });
  };

  const handleRowSelectionModelChange = async (newSelection) => {
    const getPricePromises = newSelection.map((id) => {
      const row = filteredStockRows.find((row) => row.id === id);
      return getStockPrice(row.Ticker);
    });

    try {
      const prices = await Promise.all(getPricePromises);
      const updatedRows = newSelection.map((id, index) => {
        const row = filteredStockRows.find((row) => row.id === id);
        const total = prices[index] * (row.Quantity || 1);
        return {
          ...row,
          id: row.id, // ensure you keep the ID
          Ticker: row.Ticker,
          Name: row.Name,
          Price: prices[index],
          Quantity: row.Quantity || 1, // default to 1 if Quantity is not set
          Total: total,
        };
      });
      setSelectedRows(updatedRows);
    } catch (error) {
      console.error("Error fetching stock prices:", error);
    }
  };

  return (
    <div className={style.createPortfolioWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              Create Portfolio
            </Typography>
          </div>
        </Container>
      </div>
      <Container maxWidth="xl">
        <Box component="form" noValidate autoComplete="off">
          <Grid container spacing={4} mt={0}>
            <Grid item md={2} xs={12}>
              <Card className={style.inputWrapper}>
                <CardContent>
                  <div className={style.cardTitle}>Portfolio Name</div>

                  {/* <small className={style.cardDesc}>This will be the name that is displayed</small> */}

                  <TextField
                    required
                    fullWidth
                    id="outlined-required"
                    label="Portfolio Name"
                    defaultValue=""
                    onChange={(e) => setPortfolioName(e.target.value)}
                    sx={{ mt: 2 }}
                  />
                </CardContent>

                <CardContent>
                  <div className={style.cardTitle}>Description</div>

                  {/* <small className={style.cardDesc}>This will be the description that is displayed</small> */}

                  <TextField
                    required
                    fullWidth
                    id="outlined-required"
                    label="Description"
                    defaultValue=""
                    onChange={(e) => setPortfolioDescription(e.target.value)}
                    sx={{ mt: 1 }}
                  />
                </CardContent>

                <CardContent>
                  <div className={style.cardTitle}>Amount of Capital</div>

                  <TextField
                    required
                    fullWidth
                    id="outlined-required"
                    label="Amount of Capital"
                    type="number"
                    onChange={(e) => setPortfolioCapital(e.target.value)}
                    sx={{ mt: 1 }}
                  />
                </CardContent>
              </Card>
            </Grid>
            <Grid item md={5} xs={12}>
              <Card className={style.currentStocksWrapper}>
                <CardContent className={style.currentStockMarket}>
                  <div className={style.cardTitle}>Current Stock Market</div>

                  <Grid container mt={1} spacing={1}>
                    <Grid item md={12} xs={12}>
                      <TextField
                        id="outlined-basic"
                        label="Search by ticker or name"
                        multiline
                        maxRows={4}
                        variant="outlined"
                        value={searchQuery}
                        onChange={(e) => setSearchQuery(e.target.value)}
                        sx={{ width: "100%", mr: "2rem", mb: "1rem" }}
                      />
                    </Grid>
                  </Grid>

                  <div style={{ height: 400, width: "100%" }}>
                    <DataGrid
                      rows={filteredStockRows}
                      columns={stockColumns}
                      checkboxSelection
                      onRowSelectionModelChange={handleRowSelectionModelChange}
                    />
                  </div>
                </CardContent>
              </Card>
            </Grid>

            <Grid item md={5} xs={12}>
              <Card className={style.selectedStocksWrapper}>
                <CardContent>
                  {/* space between */}
                  <Stack
                    spacing={{ xs: 1, sm: 2 }}
                    direction="row"
                    useFlexGap
                    flexWrap="wrap"
                    justifyContent={"space-between"}
                  >
                    <div>
                      <div className={style.cardTitle}>Selected Stocks</div>
                      {/* <small className={style.cardDesc}>Your chosen stocks</small> */}
                    </div>
                    <div>
                      <span className={style.cardTitle}>Total</span>
                      <span> ${getTotalPrice()}</span>
                    </div>
                  </Stack>

                  <div className={style.space}></div>

                  <div style={{ height: 400, width: "100%" }}>
                    <DataGrid
                      sx={{ mt: 2 }}
                      rows={selectedRows}
                      columns={selectedStockColumns}
                      initialState={{
                        pagination: {
                          paginationModel: { page: 0, pageSize: 5 },
                        },
                      }}
                    />
                  </div>

                  <div className={style.actionButtons}>
                    <div>
                      <Button variant="outlined" className={style.cancelButton}>
                        Cancel
                      </Button>
                      <Button
                        variant="outlined"
                        className={style.resetButton}
                        color="warning"
                        onClick={() => onResetSelectedRows()}
                      >
                        Reset
                      </Button>
                    </div>
                    <div>
                      <Button variant="contained" className={style.saveButton} onClick={submitFormHandler}>
                        Create Portfolio
                      </Button>
                    </div>
                  </div>
                </CardContent>
              </Card>
            </Grid>
          </Grid>
        </Box>
      </Container>
    </div>
  );
};

export default CreatePortfolio;
