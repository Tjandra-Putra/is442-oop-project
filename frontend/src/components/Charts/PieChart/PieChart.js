import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Pie } from "react-chartjs-2";
import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import style from "./PieChart.module.css";

ChartJS.register(ArcElement, Tooltip, Legend);

const data = {
  labels: ["one", "two", "three"],
  datasets: [
    {
      label: "Dataset 1",
      data: [3, 6, 9],
      backgroundColor: ["aqua", "blue", "purple"],
    },
  ],
};

const options = {
  type: "pie",
  data: data,
  options: {
    responsive: true,
    plugins: {
      legend: {
        position: "top",
      },
      title: {
        display: true,
        text: "Chart.js Pie Chart",
      },
    },
  },
};

function PieChart() {
 return <Pie data={data} options={options} />;
}

export default PieChart;
