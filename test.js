  // if (selectedFilterType === "yearly") {
  //   if (selectedFromYear && selectedToYear) {
  //     axios
  //       .get(`http://localhost:8080/api/stockHistory/getAnnualPortfolioValue/${portfolioId}`)
  //       .then((res) => {
  //         console.log("=========== Annual ===========");
  //         const response = res.data.data;
  //         const dataMap = {}; // Use an object to store segment data

  //         // Loop through the response to build the dataMap
  //         response.forEach((element) => {
  //           for (const year in element) {
  //             const yearData = element[year];
  //             dataMap[year] = dataMap[year] || {}; // Initialize the year if it doesn't exist
  //             for (const quarter in yearData) {
  //               dataMap[year][quarter] = yearData[quarter];
  //             }
  //           }
  //         });

  //         let processData = [["Year", "Returns"]];
  //         for (const year in dataMap) {
  //           const yearValue = dataMap[year];

  //           let currentRow = [];
  //           // Construct a date in the format "YYYY-QQ"
  //           const formattedYear = `${year}`;
  //           const fromDate = `${selectedFromYear}`;
  //           const toDate = `${selectedToYear}`;

  //           // Compare formattedQuarter to the selected range
  //           if (formattedYear >= fromDate && formattedYear <= toDate) {
  //             currentRow.push(formattedYear);
  //             currentRow.push(yearValue);
  //             processData.push(currentRow);
  //           }
  //         }

  //         setData(processData);
  //       })
  //       .catch((err) => {
  //         console.log(err);
  //       });
  //   }
  // }