DROP SCHEMA IF EXISTS GS;
CREATE DATABASE GS;
USE GS;

CREATE TABLE USER (
    userID int NOT NULL,
    email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
    PRIMARY KEY (userID),
    UNIQUE (email)
);

CREATE TABLE PORTFOLIO (
	portfolioID int NOT NULL,
    portfolioName varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    capitalAmount float NOT NULL,
    userID int NOT NULL,
    PRIMARY KEY (portfolioID),
    FOREIGN KEY (userID) REFERENCES USER(userID)
);

CREATE TABLE STOCK (
	ticker varchar(255) NOT NULL,
	stockName varchar(255) NOT NULL,
    PRIMARY KEY (ticker)
);

CREATE TABLE STOCK_INFO (
    ticker varchar(255) NOT NULL,
    industry varchar(255) NOT NULL,
    sector varchar(255) NOT NULL,
    country varchar(255) NOT NULL,
    currency varchar(255) NOT NULL,
    FOREIGN KEY (ticker) REFERENCES STOCK(ticker)
);

CREATE TABLE PORTFOLIO_STOCK (
	portfolioID int NOT NULL,
    ticker varchar(255) NOT NULL,
    quantity int NOT NULL,
    PRIMARY KEY (portfolioID, ticker),
    FOREIGN KEY (portfolioID) REFERENCES PORTFOLIO(portfolioID),
    FOREIGN KEY (ticker) REFERENCES STOCK(ticker)
);

CREATE TABLE HISTORY (
	date date NOT NULL,
	ticker varchar(255) NOT NULL,
    adjustedClosePrice float NOT NULL,
    PRIMARY KEY (date, ticker),
    FOREIGN KEY (ticker) REFERENCES STOCK(ticker)
);
    