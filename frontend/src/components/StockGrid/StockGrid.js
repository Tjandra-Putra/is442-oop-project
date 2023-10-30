import * as React from "react";
import Box from "@mui/material/Box";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
import FullscreenIcon from "@mui/icons-material/Fullscreen";
import SaveIcon from "@mui/icons-material/Save";
import CancelIcon from "@mui/icons-material/Close";
import Modal from "@mui/material/Modal";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Stack } from "@mui/material";
import { GridRowModes, DataGrid, GridActionsCellItem, GridRowEditStopReasons } from "@mui/x-data-grid";
import { randomTraderName, randomId } from "@mui/x-data-grid-generator";
import TextField from "@mui/material/TextField";
import Grid from "@mui/material/Grid";
import PortfolioStockCandleStickChart from "../Charts/PortfolioStockCandleStickChart/PortfolioStockCandleStickChart";
import axios from "axios";
import Swal from "sweetalert2";
import { useEffect, useState } from "react";
import toast from "react-hot-toast";

import style from "./StockGrid.module.css";

export default function StockGrid({ portfolioId }) {
  const notifyError = (message) => toast.error(message, { duration: 5000 });
  const notifySuccess = (message) => toast.success(message, { duration: 5000 });

  const [apiMyStocks, setApiMyStocks] = React.useState([]);
  const [stockMarket, setStockMarket] = useState([]);
  const [stockRows, setStockRows] = useState([]);
  const [stockColumns, setStockColumns] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");

  React.useEffect(() => {
    axios
      .get(`http://localhost:8080/api/portfolioStock/getPortfolioStock/${portfolioId}`)
      .then((res) => {
        const dataWithUniqueIds = addUniqueIds(res.data.data);
        setApiMyStocks(dataWithUniqueIds);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [portfolioId]);

  const addUniqueIds = (data) => {
    return data.map((item, index) => {
      const total = item.quantity * item.price; // Calculate total value
      return { id: index + 1, ...item, total }; // Include total value in the object
    });
  };

  const initialRows = [
    {
      id: randomId(),
      name: randomTraderName(),
      description: "Jon",
      price: 350000,
      quantity: 35,
      total: 35000239,
    },
    {
      id: randomId(),
      name: randomTraderName(),
      description: "Jon",
      price: 35,
      quantity: 35,
      total: 35,
    },
  ];
  const [rows, setRows] = React.useState(initialRows);
  const [rowModesModel, setRowModesModel] = React.useState({});
  const roles = ["Market", "Finance", "Development"];

  // ============================== View Stock Modal section ==============================
  const [viewStockModalOpen, setViewStockModalOpen] = React.useState(false);
  const handleViewStockModalOpen = () => setViewStockModalOpen(true);
  const handleViewStockModalClose = () => setViewStockModalOpen(false);

  // ============================== Add Stocks Modal section ==============================
  const [addStocksModalOpen, setAddStocksModalOpen] = React.useState(false);
  const handleAddStocksModalOpen = () => setAddStocksModalOpen(true);
  const handleAddStocksModalClose = () => setAddStocksModalOpen(false);

  const handleRowEditStop = (params, event) => {
    if (params.reason === GridRowEditStopReasons.rowFocusOut) {
      event.defaultMuiPrevented = true;
    }
  };

  const handleEditClick = (id) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.Edit } });
  };

  const handleSaveClick = (id) => () => {
    setRowModesModel({ ...rowModesModel, [id]: { mode: GridRowModes.View } });
  };

  const handleDeleteClick = (id) => () => {
    Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#d23457",
      cancelButtonColor: "#7a6b78",
      confirmButtonText: "Yes, delete it!",
    }).then((result) => {
      if (result.isConfirmed) {
        // Filter the stock with the specific id
        const stockToDelete = apiMyStocks.find((stock) => stock.id === id);

        if (stockToDelete) {
          axios
            .delete(
              `http://localhost:8080/api/portfolioStock/deletePortfolioStock/${stockToDelete.portfolioId}/${stockToDelete.ticker}`
            )
            .then(() => {
              // Update state by removing the row with the specified ID
              setRows(rows.filter((row) => row.id !== id));

              // Update apiMyStocks state after successful deletion
              setApiMyStocks((prevStocks) => prevStocks.filter((stock) => stock.id !== id));
            })
            .catch((error) => {
              // Handle any error that may occur during the API call
              console.error("Error deleting portfolio stock:", error);
            });
        } else {
          console.error(`Stock with id ${id} not found.`);
        }

        Swal.fire("Deleted!", "Your file has been deleted.", "success");
      } else {
        Swal.fire("Cancelled", "Your stock is safe :)", "error");
      }
    });
  };

  const handleCancelClick = (id) => () => {
    setRowModesModel({
      ...rowModesModel,
      [id]: { mode: GridRowModes.View, ignoreModifications: true },
    });

    const editedRow = rows.find((row) => row.id === id);

    if (editedRow && editedRow.isNew) {
      setRows(rows.filter((row) => row.id !== id));
    }
  };

  const handleViewClick = (id) => () => {
    handleViewStockModalOpen();
  };

  const processRowUpdate = (newRow) => {
    const updatedRow = { ...newRow, isNew: false };
    setRows(rows.map((row) => (row.id === newRow.id ? updatedRow : row)));
    return updatedRow;
  };

  const handleRowModesModelChange = (newRowModesModel) => {
    setRowModesModel(newRowModesModel);
  };

  const columns = [
    { field: "ticker", headerName: "Ticker", width: 150 },
    { field: "quantity", headerName: "Quantity", width: 150 },
    { field: "buyDate", headerName: "Buy Date", width: 150 },
    { field: "price", headerName: "Price", width: 150 },
    {
      field: "total",
      headerName: "Total",
      type: "number",
      width: 110,
    },
    {
      field: "actions",
      type: "actions",
      headerName: "Actions",
      width: 100,
      cellClassName: "actions",
      getActions: ({ id }) => {
        const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;

        if (isInEditMode) {
          return [
            <GridActionsCellItem
              icon={<SaveIcon />}
              label="Save"
              sx={{
                color: "primary.main",
              }}
              onClick={handleSaveClick(id)}
            />,
            <GridActionsCellItem
              icon={<CancelIcon />}
              label="Cancel"
              className="textPrimary"
              onClick={handleCancelClick(id)}
              color="inherit"
            />,
          ];
        }

        return [
          <GridActionsCellItem
            icon={<EditIcon />}
            label="Edit"
            className="textPrimary"
            onClick={handleEditClick(id)}
            color="inherit"
          />,
          <GridActionsCellItem
            icon={<DeleteIcon />}
            label="Delete"
            onClick={handleDeleteClick(id)} // Pass the 'ticker' here
            color="inherit"
          />,
          <GridActionsCellItem icon={<FullscreenIcon />} label="View" onClick={handleViewClick(id)} color="inherit" />,
        ];
      },
    },
  ];

  // ============================== Add new stocks section ==============================
  const getTotalPrice = () => {
    let totalPrice = 0;
    selectedRows.forEach((row) => {
      totalPrice += row.Total;
    });
    return totalPrice;
  };

  const [selectedRows, setSelectedRows] = React.useState([]);

  React.useEffect(() => {
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

  const [quantityValues, setQuantityValues] = React.useState(Object.fromEntries(stockRows.map((row) => [row.id, 1])));

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
      width: 100,
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
      width: 150,
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
            className={style.buyDateInput}
            fullWidth
          />
        );
      },
      width: 150, // Adjust the width as needed
    },
    {
      field: "Total",
      headerName: "Total",
      type: "number",
      width: 70,
    },
  ];

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

  // submit form with validation
  const submitFormHandler = () => {
    if (selectedRows.length === 0) {
      notifyError("Please select at least one stock");
      return;
    }

    const userId = 1;
  };

  return (
    <>
      {/* ============================ ADD STOCKS MODAL ============================ */}
      <Stack direction="row" justifyContent="space-between" alignItems="center" spacing={2} mb={1}>
        <div class={style.cardTitle}>My Stocks</div>
        <Button onClick={handleAddStocksModalOpen}>Add Stocks</Button>
        <Modal
          open={addStocksModalOpen}
          onClose={handleAddStocksModalClose}
          aria-labelledby="modal-modal-title"
          aria-describedby="modal-modal-description"
        >
          <Box className={style.modal}>
            <Typography id="modal-modal-title" variant="h6" component="h2">
              Add Stocks
            </Typography>

            <Grid container mt={0} spacing={2}>
              <Grid item md={6} xs={12}>
                {/* Search  */}
                <Grid container mt={2} spacing={1}>
                  <Grid item md={9} xs={12}>
                    <TextField
                      id="outlined-basic"
                      label="Search for stocks by ticker or name"
                      multiline
                      maxRows={4}
                      variant="outlined"
                      sx={{ width: "100%", mr: "2rem" }}
                      size="small"
                      value={searchQuery}
                      onChange={(e) => setSearchQuery(e.target.value)}
                    />
                  </Grid>
                  <Grid item md={3} sx={12}>
                    <Button variant="outlined" sx={{ height: "2.5rem", width: "100%", mb: "1rem" }}>
                      Seach
                    </Button>
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
              </Grid>
              <Grid item md={6} xs={12}>
                <Stack
                  spacing={{ xs: 1, sm: 2 }}
                  direction="row"
                  useFlexGap
                  flexWrap="wrap"
                  justifyContent={"space-between"}
                  className={style.selectedStockHeader}
                >
                  <div>
                    <div className={style.cardTitle}>Selected Stocks</div>
                    {/* <small className={style.cardDesc}>Your chosen stocks</small> */}
                  </div>
                  <div>
                    <span className={style.cardTitle}>Total: </span>
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
                  </div>
                  <div>
                    <Button variant="contained" className={style.saveButton} onClick={submitFormHandler}>
                      Add Stocks
                    </Button>
                  </div>
                </div>
              </Grid>
            </Grid>
          </Box>
        </Modal>
      </Stack>
      {/* MY STOCKS TABLE */}
      <Box
        sx={{
          height: 350,
          width: "100%",
          "& .actions": {
            color: "text.secondary",
          },
          "& .textPrimary": {
            color: "text.primary",
          },
        }}
      >
        <DataGrid
          rows={apiMyStocks}
          columns={columns}
          editMode="row"
          rowModesModel={rowModesModel}
          onRowModesModelChange={handleRowModesModelChange}
          onRowEditStop={handleRowEditStop}
          processRowUpdate={processRowUpdate}
          // slots={{
          //   toolbar: EditToolbar,
          // }}
          slotProps={{
            toolbar: { setRows, setRowModesModel },
          }}
        />
      </Box>
      {/* View Stock Modal */}
      <Modal
        open={viewStockModalOpen}
        onClose={handleViewStockModalClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box className={style.modal}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Stock Name: Apple
          </Typography>
          <PortfolioStockCandleStickChart />
        </Box>
      </Modal>
    </>
  );
}
