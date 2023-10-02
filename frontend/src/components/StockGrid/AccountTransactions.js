import React from "react";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import style from "./AccountTransactions.module.css";
import { DataGrid } from "@mui/x-data-grid";
const AccountTransactions = () => {
    const columns = [
        { field: "id", headerName: "ID", minWidth: 20 },
        { field: "Name", headerName: "Amount ",minWidth: 70 },
        { field: "Description", headerName: "Stock Name" , minWidth: 70},
        {
          field: "Price",
          headerName: "Price",
          type: "number",
         
        },
        {
          field: "Quantity",
          headerName: "Quantity",
          type: "number",
          
        },
        {
          field: "Total",
          headerName: "Total",
          type: "number",
         
        },
      ];
    
      const rows = [
        { id: 1, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 2, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 3, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 4, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 5, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 6, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 7, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 8, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 9, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 10, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 11, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 12, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 13, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
        { id: 14, Name: "Snow", Description: "Jon", Price: 35, Quantity: 35, Total: 35 },
      ];
      return (
        <div>
         <Card className={style.cardCustom}>
              <CardContent>
                <div style={{ height: 280, width: "100%" }}>
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
              </CardContent>
         </Card>
        </div>
      );
    };


export default AccountTransactions;