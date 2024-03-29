import React, { useEffect, useState } from "react";
import style from "./PortfolioDetailed.module.css";
import { useNavigate, useParams } from "react-router-dom";
import StockCard from "../../components/StockCard/StockCard";
import StockGrid from "../../components/StockGrid/StockGrid";
import toast from "react-hot-toast";
import axios from "axios";
import { useSelector } from "react-redux";
import PortfolioStocksAllocationChart from "../../components/Charts/PortfolioStocksAllocationChart/PortfolioStocksAllocationChart";
import MarketExposureByCountryChart from "../../components/Charts/MarketExposureByCountryChart/MarketExposureByCountryChart";
import MarketExposureByCurrencyChart from "../../components/Charts/MarketExposureByCurrencyChart/MarketExposureByCurrencyChart";
import PortfolioReturnsChart from "../../components/Charts/PortfolioReturnsChart/PortfolioReturnsChart";
import PortfolioAnnualReturnsPercentage from "../../components/Charts/PortfolioAnnualReturnsPercentage/PortfolioAnnualReturnsPercentage";
import PortfolioStocksBySegmentChart from "../../components/Charts/PortfolioStocksBySegmentChart/PortfolioStocksBySegmentChart";
import {
  Card,
  Grid,
  CardContent,
  Button,
  Typography,
  Box,
  Modal,
  Container,
  TextField,
  Alert,
  Stack,
} from "@mui/material";

const PortfolioDetailed = () => {
  const navigate = useNavigate();

  const { user, loading, error, isAuth } = useSelector((state) => state.userReducer);

  // get id from url paramter
  const { id } = useParams();
  const [portfolio, setPortfolio] = React.useState(null);
  const [loadingNew, setLoadingNew] = React.useState(false);
  const [deleteMessage, setDeleteMessage] = React.useState("");
  const [dataFromChildStocksCount, setDataFromChildStocksCount] = useState(null);
  const [portfolioCapital, setPortfolioCapital] = React.useState("");

  const notifyError = (message) => toast.error(message, { duration: 5000 });
  const notifySuccess = (message) => toast.success(message, { duration: 5000 });

  // Callback function to receive data from the child component
  const receiveDataFromChildStockGrid = (data) => {
    setDataFromChildStocksCount(data);
  };

  useEffect(() => {
    setLoadingNew(true);
    axios
      .get(`http://localhost:8080/api/portfolio/getPortfolio/${user.userId}/${id}`, {
        headers: {
          Authorization: `Bearer ${user?.token}`, // Replace "yourTokenHere" with your actual token
        },
      })
      .then((res) => {
        let portfolioRes = res.data.data[0];
        setPortfolio(portfolioRes);

        setPortfolioTitle(portfolioRes.portfolioName);
        setPortfolioDescription(portfolioRes.description);
        setPortfolioCapital(portfolioRes.capitalAmt);
      })
      .catch((err) => {
        console.log(err);
      });

    setLoadingNew(false);
  }, [id]);

  // modal update portfolio
  const [updatePortfolioModalOpen, setUpdatePortfolioModalOpen] = React.useState(false);
  const [deletePortfolioModalOpen, setDeletePortfolioModalOpen] = React.useState(false);
  const handleUpdatePortfolioModalOpen = () => setUpdatePortfolioModalOpen(true);
  const handleUpdatePortfolioModalClose = () => setUpdatePortfolioModalOpen(false);
  const handleDeletePortfolioModalOpen = () => setDeletePortfolioModalOpen(true);
  const handleDeletePortfolioModalClose = () => setDeletePortfolioModalOpen(false);

  const [portfolioTitle, setPortfolioTitle] = React.useState("");
  const [portfolioDescription, setPortfolioDescription] = React.useState("");

  const onSubmitUpdatePortfolioUpdate = () => {
    if (portfolioCapital < 0) {
      notifyError("Capital cannot be negative");
      return;
    } else if (portfolioCapital < 0) {
      notifyError("Capital cannot be negative");
      return;
    } else if (portfolioTitle === "") {
      notifyError("Portfolio title cannot be empty");
      return;
    } else if (portfolioDescription === "") {
      notifyError("Portfolio description cannot be empty");
      return;
    }

    // update portfolio
    const postData = {
      data: [
        {
          fieldName: "capitalAmt",
          value: portfolioCapital,
        },
        {
          fieldName: "description",
          value: portfolioDescription,
        },
        { fieldName: "portfolioName", value: portfolioTitle },
      ],
    };

    axios
      .put(`http://localhost:8080/api/portfolio/editPortfolio/${user.userId}/${id}`, postData, {
        headers: {
          Authorization: `Bearer ${user?.token}`, // Replace "yourTokenHere" with your actual token
        },
      })
      .then((res) => {
        console.log("updateeeeeee");
        console.log(res.data);

        if (res.data && res.data.data === null) {
          notifyError(res.data.message);
        } else {
          notifySuccess(res.data.message);

          // Update the state with the new portfolio data
          setPortfolio((prevPortfolio) => ({
            ...prevPortfolio,
            capitalAmt: portfolioCapital,
            description: portfolioDescription,
            portfolioName: portfolioTitle,
          }));
        }
      })
      .catch((err) => {
        notifyError("Error updating portfolio");
      });
  };

  const onDeletePortfolio = () => {
    if (deleteMessage !== "delete/portfolio/" + portfolio?.portfolioName) {
      notifyError("Please type the correct message");
      return;
    }

    console.log("====================================");

    const portfolioId = parseInt(id, 10); // Parse the id as an integer
    console.log(portfolioId);

    axios
      .delete(`http://localhost:8080/api/portfolio/deletePortfolio/${user.userId}/${portfolioId}`, {
        headers: {
          Authorization: `Bearer ${user?.token}`, // Replace "yourTokenHere" with your actual token
        },
      })
      .then((res) => {
        console.log(res.data);

        if (res.data && res.data.data === null) {
          notifyError(res.data.message);
        } else {
          notifySuccess(res.data.message);
          navigate("/dashboard");
        }
      })
      .catch((err) => {
        notifyError("Error deleting portfolio");
      });
  };

  return (
    <div className={style.portfolioDetailedWrapper}>
      <div className={style.banner}>
        <Container maxWidth="xl">
          <div className="banner-content">
            <Typography variant="h5" style={{ letterSpacing: "1px" }}>
              {portfolio && portfolio.portfolioName}
            </Typography>
          </div>
        </Container>
      </div>

      {/* Second Container */}
      <Container>
        <div className={style.buttonGroups}>
          <Stack direction="row" spacing={1} justifyContent={"space-between"}>
            <div>
              <Button
                variant="outlined"
                style={{ borderColor: "black", color: "black", marginBottom: "1rem" }}
                onClick={() => navigate("/dashboard")}
              >
                <i className="fa-solid fa-arrow-left" style={{ marginRight: "7px" }}></i> Back
              </Button>
            </div>
            <div>
              <Button
                variant="contained"
                style={{ backgroundColor: "#12294d" }}
                onClick={handleUpdatePortfolioModalOpen}
              >
                <i className="fa-regular fa-pen-to-square" style={{ marginRight: "7px" }}></i> Edit
              </Button>
              <Modal
                open={updatePortfolioModalOpen}
                onClose={handleUpdatePortfolioModalClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
              >
                {!loadingNew && (
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

                    <Button
                      variant="contained"
                      style={{ float: "right" }}
                      onClick={() => onSubmitUpdatePortfolioUpdate()}
                    >
                      Save Changes
                    </Button>
                  </Box>
                )}
              </Modal>

              <Button
                variant="outlined"
                style={{ backgroundColor: "darkred", color: "white", marginLeft: "10px", borderColor: "darkred" }}
                // onClick={() => onDeletePortfolio()}
                onClick={handleDeletePortfolioModalOpen}
              >
                <i className="fa-solid fa-trash" style={{ marginRight: "7px" }}></i> Delete
              </Button>

              <Modal
                open={deletePortfolioModalOpen}
                onClose={handleDeletePortfolioModalClose}
                aria-labelledby="modal-modal-title"
                aria-describedby="modal-modal-description"
              >
                <Box className={style.updatePortfolioModal}>
                  <h3 className={style.deleteTitle}>Are you absolutely sure?</h3>

                  <Stack sx={{ width: "100%" }} spacing={2}>
                    <Alert severity="warning">Unexpected bad things will happen if you don’t read this!</Alert>
                  </Stack>

                  <p className={style.deleteMessage}>
                    This <strong>action</strong> cannot be undone. This will permanently delete the portfolio (
                    {portfolio?.portfolioName}) and all its contents.
                  </p>

                  <p className={style.deleteMessage}>
                    Please type <strong>delete/portfolio/{portfolio?.portfolioName}</strong> to confirm.
                  </p>

                  <TextField
                    id="outlined-basic"
                    // label="Outlined"
                    variant="outlined"
                    style={{ width: "100%" }}
                    placeholder={"delete/portfolio/" + portfolio?.portfolioName}
                    value={deleteMessage}
                    onChange={(e) => setDeleteMessage(e.target.value)}
                  />

                  <Button
                    variant="outlined"
                    style={{ float: "right", marginTop: "1rem", width: "100%" }}
                    onClick={() => onDeletePortfolio()}
                    color="error"
                  >
                    I understand, delete this portfolio
                  </Button>
                </Box>
              </Modal>
            </div>
          </Stack>

          <div className={style.portfolioDescriptionWrapper}>
            <div className={style.portfolioDescriptionTitle}>Portfolio Description</div>

            <Typography variant="body1" className={style.portfolioDescription}>
              {portfolio && portfolio.description}
            </Typography>
          </div>
        </div>

        {/* ============================ LEFT SECTION ============================ */}
        <Grid container rowSpacing={5} columnSpacing={{ xs: 2, sm: 2, md: 1 }}>
          <Grid item xs={12} md={6} lg={6}>
            {/* <div className={style.firstContainer}>
              <StockCard name="Portfolio Value" value={`$${portfolio && portfolio.portfolioValue}`} />
              <StockCard name="Total Stocks" value={dataFromChildStocksCount} />
            </div> */}

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Stocks Allocation</div>
                <PortfolioStocksAllocationChart portfolioId={id} />
              </CardContent>
            </Card>
          </Grid>

          {/* ============================ RIGHT SECTION ============================ */}
          <Grid item xs={12} md={6} lg={6}>
            {/* <div className={style.firstContainer}>
              <StockCard name="Net Value" value="-" />
              <StockCard name="Capital" value={`$${portfolio && portfolio.capitalAmt}`} />
            </div> */}

            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Stocks by Segment</div>
                <PortfolioStocksBySegmentChart portfolioId={id} />
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Grid item xs={12} md={12} lg={12}>
          <Card className={style.cardCustom}>
            <CardContent>
              <div className={style.cardTitle}>Portfolio Returns</div>
              <br />
              <PortfolioReturnsChart portfolioId={id} />
            </CardContent>
          </Card>
        </Grid>

        <Grid item xs={12} md={12} lg={12}>
          <Card className={style.cardCustom}>
            <CardContent>
              <div className={style.cardTitle}>Portfolio Annual Returns (%)</div>
              <br />
              <PortfolioAnnualReturnsPercentage portfolioId={id} />
            </CardContent>
          </Card>
        </Grid>

        <Grid container rowSpacing={5} columnSpacing={{ xs: 2, sm: 2, md: 1 }}>
          <Grid item xs={12} md={6} lg={6}>
            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Market Exposure by Currency</div>

                <br />
                <MarketExposureByCurrencyChart portfolioId={id} />
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} md={6} lg={6}>
            <Card className={style.cardCustom}>
              <CardContent>
                <div className={style.cardTitle}>Market Exposure by Country</div>

                <br />
                <MarketExposureByCountryChart portfolioId={id} />
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Grid item xs={12} md={6} lg={12}>
          <Card className={style.cardCustom}>
            <CardContent>
              <StockGrid
                portfolioId={id}
                sendDataToParent={receiveDataFromChildStockGrid}
                portfolioCapital={portfolioCapital}
              />
            </CardContent>
          </Card>
        </Grid>
      </Container>
    </div>
  );
};

export default PortfolioDetailed;
