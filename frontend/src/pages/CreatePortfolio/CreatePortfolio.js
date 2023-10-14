import React, { useState } from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Grid from "@mui/material/Grid";
import Button from "@mui/material/Button";
import { DataGrid } from "@mui/x-data-grid";
import TextField from "@mui/material/TextField";
import Stack from "@mui/material/Stack";

import style from "./CreatePortfolio.module.css";

const CreatePortfolio = () => {
  const [selectedRows, setSelectedRows] = useState([]);
  const [portfolioName, setPortfolioName] = useState("");
  const [portfolioDescription, setPortfolioDescription] = useState("");
  const [portfolioCapital, setPortfolioCapital] = useState("");

  // get total price of selected stocks
  const getTotalPrice = () => {
    let totalPrice = 0;
    selectedRows.forEach((row) => {
      totalPrice += row.Total;
    });
    return totalPrice;
  };

  const stockColumns = [
    { field: "id", headerName: "Ticker", width: 100 },
    { field: "Name", headerName: "Name", width: 130 },
    {
      field: "Price",
      headerName: "Price",
      type: "number",
      width: 90,
    },
  ];

  const stockRows = [
    { id: 1, Name: "Snow", Price: 105 },
    { id: 2, Name: "Snow", Price: 35 },
    { id: 3, Name: "Snow", Price: 10 },
    { id: 4, Name: "Snow", Price: 35 },
    { id: 5, Name: "Snow", Price: 35 },
    { id: 6, Name: "Snow", Price: 35 },
  ];

  const [quantityValues, setQuantityValues] = useState(Object.fromEntries(stockRows.map((row) => [row.id, 1])));

  const handleQuantityChange = (id, newValue) => {
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

  // Material UI DataGrid
  const selectedStockColumns = [
    { field: "id", headerName: "Ticker", width: 80 },
    {
      field: "Name",
      headerName: "Name",
      width: 90,
    },
    {
      field: "Price",
      headerName: "Price",
      type: "number",
      width: 60,
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
      field: "Total",
      headerName: "Total",
      type: "number",
      width: 70,
    },
  ];

  const onResetSelectedRows = () => {
    // refresh page
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
      selectedRowsWithQuantity,
      portfolioName,
      portfolioDescription,
      // cast to int
      portfolioCapital: +portfolioCapital
        .split("")
        .filter((char) => char !== "$")
        .join(""),
    };
  };

  console.log(getSelectedRows());

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
        <Grid container spacing={4} mt={0}>
          <Grid item md={3} xs={12}>
            <div className={style.inputWrapper}>
              <Card className={style.cardCustom}>
                <CardContent>
                  <div className={style.cardTitle}>Portfolio Name</div>

                  <small className={style.cardDesc}>This will be the name that is displayed</small>

                  <TextField
                    required
                    fullWidth
                    id="outlined-required"
                    label="Portfolio Name"
                    defaultValue=""
                    onChange={(e) => setPortfolioName(e.target.value)}
                    sx={{ mt: 3 }}
                  />
                </CardContent>
              </Card>

              <Card className={style.cardCustom}>
                <CardContent>
                  <div className={style.cardTitle}>Description</div>

                  <small className={style.cardDesc}>This will be the description that is displayed</small>

                  <TextField
                    required
                    fullWidth
                    id="outlined-required"
                    label="Description"
                    defaultValue=""
                    onChange={(e) => setPortfolioDescription(e.target.value)}
                    sx={{ mt: 3 }}
                  />
                </CardContent>
              </Card>

              <Card className={style.cardCustom}>
                <CardContent>
                  <div className={style.cardTitle}>Amount of Capital</div>

                  <small className={style.cardDesc}>This will be the amount of capital that is displayed</small>

                  <TextField
                    required
                    fullWidth
                    id="outlined-required"
                    label="Amount of Capital"
                    defaultValue=""
                    onChange={(e) => setPortfolioCapital(e.target.value)}
                    sx={{ mt: 3 }}
                  />
                </CardContent>
              </Card>
            </div>
          </Grid>
          <Grid item md={5} xs={12}>
            <Card className={style.cardCustom}>
              <CardContent className={style.currentStockMarket}>
                <div className={style.cardTitle}>Current Stock Market</div>
                <small className={style.cardDesc}>This will be the current stock market that is displayed</small>
                <div className={style.space}></div>

                <div style={{ height: 400, width: "100%" }}>
                  <DataGrid
                    rows={stockRows}
                    columns={stockColumns}
                    checkboxSelection
                    // onchange
                    onRowSelectionModelChange={(newSelection) => {
                      const selectedRows = newSelection.map((id) => {
                        const row = stockRows.find((row) => row.id === id);
                        // Extracting only the desired properties
                        return {
                          id: row.id,
                          Name: row.Name,
                          // add dollar sign but make it an integer
                          Price: row.Price,
                          Total: row.Total ? row.Total : row.Price,
                        };
                      });
                      setSelectedRows(selectedRows);
                    }}
                  />
                </div>
              </CardContent>
            </Card>
          </Grid>

          <Grid item md={4} xs={12}>
            <Card className={style.cardCustom}>
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
                    <small className={style.cardDesc}>Your chosen stocks</small>
                  </div>
                  <div>
                    <span className={style.cardTitle}>Total</span>
                    <span> ${getTotalPrice()}</span>
                  </div>
                </Stack>

                <div className={style.space}></div>

                <div style={{ height: 345, width: "100%" }}>
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
                    <Button variant="contained" className={style.saveButton}>
                      Save
                    </Button>
                  </div>
                </div>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
};

export default CreatePortfolio;
