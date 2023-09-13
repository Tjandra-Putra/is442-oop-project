CREATE TABLE USER (
    userID varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    username varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
    PRIMARY KEY (userID)
);

CREATE TABLE PORTFOLIO (
	portfolioID varchar(255) NOT NULL,
    portfolioName varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    capitalAmount float NOT NULL,
    userID varchar(255) NOT NULL,
    PRIMARY KEY (portfolioID),
    FOREIGN KEY (userID) REFERENCES USER(userID)
);

CREATE TABLE STOCK (
	ticker varchar(255) NOT NULL,
    industry varchar(255) NOT NULL,
    sector varchar(255) NOT NULL,
    country varchar(255) NOT NULL,
	stockName varchar(255) NOT NULL,
    PRIMARY KEY (ticker)
);

CREATE TABLE PORTFOLIO_STOCK (
	portfolioID varchar(255) NOT NULL,
    ticker varchar(255) NOT NULL,
    buyDate date NOT NULL,
    quantity int NOT NULL,
    price float NOT NULL,
    PRIMARY KEY (buyDate, portfolioID, ticker),
    FOREIGN KEY (portfolioID) REFERENCES PORTFOLIO(portfolioID),
    FOREIGN KEY (ticker) REFERENCES STOCK(ticker)
);

CREATE TABLE HISTORY (
	stockHistoryDate date NOT NULL,
	ticker varchar(255) NOT NULL,
    adjustedClosePrice float NOT NULL,
    PRIMARY KEY (stockHistoryDate, ticker),
    FOREIGN KEY (ticker) REFERENCES STOCK(ticker)
);
    