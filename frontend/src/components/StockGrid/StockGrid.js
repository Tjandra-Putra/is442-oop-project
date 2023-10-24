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

import style from "./StockGrid.module.css";

export default function StockGrid({ portfolioId }) {
  const [apiMyStocks, setApiMyStocks] = React.useState([]);

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
      return { id: index + 1, ...item };
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

  const handleDeleteClick = (id, ticker) => () => {
    // First, ask the user for confirmation using a native browser confirm dialog
    const firstConfirmation = window.confirm(
      "This action is irreversible. Are you absolutely sure you want to delete this item?"
    );

    // If the user confirms the first dialog, ask for confirmation again
    if (firstConfirmation) {
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
    }
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

  // const columns = [
  //   { field: "id", headerName: "ID", width: 150 },
  //   { field: "name", headerName: "Name", width: 200 },
  //   {
  //     field: "description",
  //     headerName: "Description",
  //     width: 300,
  //     // type: "singleSelect",
  //     // valueOptions: ["Market", "Finance", "Development"],
  //   },
  //   {
  //     field: "price",
  //     headerName: "Price",
  //     type: "number",
  //     width: 110,
  //   },
  //   {
  //     field: "quantity",
  //     headerName: "Quantity",
  //     type: "number",
  //     width: 110,
  //     editable: true,
  //   },
  //   {
  //     field: "total",
  //     headerName: "Total",
  //     type: "number",
  //     width: 110,
  //   },
  //   {
  //     field: "actions",
  //     type: "actions",
  //     headerName: "Actions",
  //     width: 100,
  //     cellClassName: "actions",
  //     getActions: ({ id }) => {
  //       const isInEditMode = rowModesModel[id]?.mode === GridRowModes.Edit;

  //       if (isInEditMode) {
  //         return [
  //           <GridActionsCellItem
  //             icon={<SaveIcon />}
  //             label="Save"
  //             sx={{
  //               color: "primary.main",
  //             }}
  //             onClick={handleSaveClick(id)}
  //           />,
  //           <GridActionsCellItem
  //             icon={<CancelIcon />}
  //             label="Cancel"
  //             className="textPrimary"
  //             onClick={handleCancelClick(id)}
  //             color="inherit"
  //           />,
  //         ];
  //       }

  //       return [
  //         <GridActionsCellItem
  //           icon={<EditIcon />}
  //           label="Edit"
  //           className="textPrimary"
  //           onClick={handleEditClick(id)}
  //           color="inherit"
  //         />,
  //         <GridActionsCellItem icon={<DeleteIcon />} label="Delete" onClick={handleDeleteClick(id)} color="inherit" />,
  //         <GridActionsCellItem icon={<FullscreenIcon />} label="View" onClick={handleViewClick(id)} color="inherit" />,
  //       ];
  //     },
  //   },
  // ];

  // ============================== Add new stocks section ==============================
  const getTotalPrice = () => {
    let totalPrice = 0;
    selectedRows.forEach((row) => {
      totalPrice += row.Total;
    });
    return totalPrice;
  };

  const [selectedRows, setSelectedRows] = React.useState([]);

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

  // Material UI DataGrid
  const selectedStockColumns = [
    { field: "id", headerName: "Ticker", width: 120 },
    {
      field: "Name",
      headerName: "Name",
      width: 130,
    },
    {
      field: "Price",
      headerName: "Price",
      type: "number",
      width: 100,
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
      // editable
    },
    {
      field: "Total",
      headerName: "Total",
      type: "number",
      width: 100,
    },
  ];

  // return state as one object including quantity, portfolio name, description, capital
  const getSelectedRows = () => {
    const selectedRowsWithQuantity = selectedRows.map((row) => {
      return {
        ...row,
        Quantity: quantityValues[row.id],
      };
    });
  };

  // submit form with validation
  const submitFormHandler = () => {
    let formData = {
      portfolioId: portfolioId,
      selectedStocks: getSelectedRows(),
    };

    console.log(formData);
  };

  return (
    <>
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
                          Price: row.Price,
                          Total: row.Total ? row.Total : row.Price,
                        };
                      });
                      setSelectedRows(selectedRows);
                    }}
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
