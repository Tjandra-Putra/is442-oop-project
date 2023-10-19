import React from "react";
import style from "./PortfolioDetailed.module.css";
import { useParams } from "react-router-dom";
import StockCard from "../../components/StockCard/StockCard";
import PortfolioStocksAllocationChart from "../../components/Charts/PortfolioStocksAllocationChart/PortfolioStocksAllocationChart";
import PortfolioStocksBySegmentChart from "../../components/Charts/PortfolioStocksBySegmentChart/PortfolioStocksBySegmentChart";
import PortfolioStocksChart from "../../components/Charts/PortfolioStocksChart/PortfolioStocksChart";
import StockGrid from "../../components/StockGrid/StockGrid";
import toast from "react-hot-toast";

import { Card, Grid, CardContent, Stack, Button, Typography, Box, Modal, Container, TextField } from "@mui/material";

const PortfolioDetailed = () => {
  // get id from url paramter
  const { id } = useParams();
  const notifyError = (message) => toast.error(message, { duration: 5000 });
  const notifySuccess = (message) => toast.success(message, { duration: 5000 });

  // get portfolio name by id
  // ...

  // modal update portfolio
  const [updatePortfolioModalOpen, setUpdatePortfolioModalOpen] = React.useState(false);
  const handleUpdatePortfolioModalOpen = () => setUpdatePortfolioModalOpen(true);
  const handleUpdatePortfolioModalClose = () => setUpdatePortfolioModalOpen(false);

  const [portfolioTitle, setPortfolioTitle] = React.useState("");
  const [portfolioDescription, setPortfolioDescription] = React.useState("");
  const [portfolioCapital, setPortfolioCapital] = React.useState("");

  const onSubmitUpdatePortfolioUpdate = () => {
    // update portfolio
    // ...
    notifySuccess("Portfolio Updated");
  };

  return (
    <div className={style.portfolioDetailedWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              {id}
            </Typography>
          </div>
        </Container>
      </div>

      {/* Second Container */}
      <Container>
        <div className={style.buttonGroups}>
          <Stack direction="row" spacing={1} justifyContent={"end"}>
            <Button
              variant="outlined"
              style={{ borderColor: "#12294d", color: "#12294d" }}
              onClick={handleUpdatePortfolioModalOpen}
            >
              <i class="fa-regular fa-pen-to-square" style={{ marginRight: "7px" }}></i> Edit
            </Button>
            <Modal
              open={updatePortfolioModalOpen}
              onClose={handleUpdatePortfolioModalClose}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
            >
              <Box className={style.updatePortfolioModal}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                  Edit Portfolio
                </Typography>
                <TextField
                  id="outlined-basic"
                  label="Portfolio title"
                  variant="outlined"
                  style={{ width: "100%", marginBottom: "1rem", marginTop: "1rem" }}
                  value={portfolioTitle}
                  onChange={(e) => setPortfolioTitle(e.target.value)}
                />
                <br />
                <TextField
                  id="outlined-basic"
                  label="Portfolio Description"
                  variant="outlined"
                  style={{ width: "100%", marginBottom: "1rem" }}
                  value={portfolioDescription}
                  onChange={(e) => setPortfolioDescription(e.target.value)}
                />
                <br />
                <TextField
                  id="outlined-basic"
                  label="Portfolio Capital"
                  variant="outlined"
                  style={{ width: "100%", marginBottom: "1rem" }}
                  type="number"
                  value={portfolioCapital}
                  onChange={(e) => setPortfolioCapital(e.target.value)}
                />

                <Button variant="contained" style={{ float: "right" }} onClick={() => onSubmitUpdatePortfolioUpdate()}>
                  Save Changes
                </Button>
              </Box>
            </Modal>

            <Button variant="outlined" style={{ borderColor: "darkred", color: "darkred" }}>
              <i class="fa-solid fa-trash" style={{ marginRight: "7px" }}></i> Delete
            </Button>
          </Stack>

          <div className={style.portfolioDescriptionWrapper}>
            <div className={style.portfolioDescriptionTitle}>Portfolio Description</div>

            <Typography variant="body1" className={style.portfolioDescription}>
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Quisquam voluptatum, quibusdam, voluptate, quia
              voluptas quod quos voluptatibus quae doloribus quidem voluptatem. Quisquam voluptatum, quibusdam,
              voluptate, quia voluptas quod quos voluptatibus quae doloribus quidem voluptatem. Quisquam voluptatum,
              quibusdam, voluptate, quia voluptas quod quos voluptatibus quae doloribus
            </Typography>
          </div>
        </div>

        {/* ============================ LEFT SECTION ============================ */}
        <Grid container rowSpacing={5} columnSpacing={{ xs: 2, sm: 2, md: 1 }}>
          <Grid item xs={12} md={6} lg={6}>
            <div className={style.firstContainer}>
              <StockCard name="Portfolio Value" value="$6,364" />
              <StockCard name="Total Stocks" value="10" />
            </div>

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Stocks Allocation</div>
                <PortfolioStocksAllocationChart />
              </CardContent>
            </Card>
          </Grid>

          {/* ============================ RIGHT SECTION ============================ */}
          <Grid item xs={12} md={6} lg={6}>
            <div className={style.firstContainer}>
              <StockCard name="Net Value" value="20% " />
              <StockCard name="Capital" value="$20,000" />
            </div>

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Stocks by Segment</div>
                <PortfolioStocksBySegmentChart />
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Grid item xs={12} md={12} lg={12}>
          <Card className={style.cardCustom}>
            <CardContent>
              <div className={style.cardTitle}>Stocks Performance</div>
              <br />
              <br />
              <PortfolioStocksChart />
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={12} lg={12}>
          <Card className={style.cardCustom}>
            <CardContent>
              <StockGrid />
            </CardContent>
          </Card>
        </Grid>
      </Container>
    </div>
  );
};

export default PortfolioDetailed;
