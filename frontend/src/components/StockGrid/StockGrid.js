import * as React from "react";
import Box from "@mui/material/Box";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
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

import style from "./StockGrid.module.css";

export default function StockGrid({ portfolioId }) {
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

  // modal
  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

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
    // First, ask the user for confirmation using a native browser confirm dialog
    const firstConfirmation = window.confirm(
      "This action is irreversible. Are you absolutely sure you want to delete this item?"
    );

    // If the user confirms the first dialog, ask for confirmation again
    if (firstConfirmation) {
      console.log("Deleted: ", id);
      setRows(rows.filter((row) => row.id !== id));
    }
  };

  const handleCancelClick = (id) => () => {
    setRowModesModel({
      ...rowModesModel,
      [id]: { mode: GridRowModes.View, ignoreModifications: true },
    });

    const editedRow = rows.find((row) => row.id === id);
    if (editedRow.isNew) {
      setRows(rows.filter((row) => row.id !== id));
    }
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
    { field: "id", headerName: "ID", width: 150 },
    { field: "name", headerName: "Name", width: 200 },
    {
      field: "description",
      headerName: "Description",
      width: 300,
      // type: "singleSelect",
      // valueOptions: ["Market", "Finance", "Development"],
    },
    {
      field: "price",
      headerName: "Price",
      type: "number",
      width: 110,
    },
    {
      field: "quantity",
      headerName: "Quantity",
      type: "number",
      width: 110,
      editable: true,
    },
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
          <GridActionsCellItem icon={<DeleteIcon />} label="Delete" onClick={handleDeleteClick(id)} color="inherit" />,
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
        <Button onClick={handleOpen}>Add Stocks</Button>
        <Modal
          open={open}
          onClose={handleClose}
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
                          // add dollar sign but make it an integer
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
          rows={rows}
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
    </>
  );
}
