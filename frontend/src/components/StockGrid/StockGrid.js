import React from "react";
import { DataGrid } from "@mui/x-data-grid";

const StockGrid = () => {
  const columns = [
    { field: "id", headerName: "ID", width: 70 },
    { field: "Name", headerName: "Name", width: 130 },
    { field: "Description", headerName: "Description", width: 130 },
    {
      field: "Price",
      headerName: "Price",
      type: "number",
      width: 90,
    },
    {
      field: "Quantity",
      headerName: "Quantity",
      type: "number",
      width: 90,
    },
    {
      field: "Total",
      headerName: "Total",
      type: "number",
      width: 90,
    },
  ];

  const rows = [
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
    { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
  ];

  return (
    <div style={{ height: 400, width: "100%" }}>
      <DataGrid
        rows={rows}
        columns={columns}
        initialState={{
          pagination: {
            paginationModel: { page: 0, pageSize: 5 },
          },
        }}
        pageSizeOptions={[5, 10]}
        checkboxSelection
      />
    </div>
  );
};

export default StockGrid;
