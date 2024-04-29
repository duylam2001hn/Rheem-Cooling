// Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
// Chart.defaults.global.defaultFontColor = '#292b2c';


$(document).ready(function () {

console.log(dataBar);


    var transaction_x = dataBar.map(x => x["name"])
    var transaction_y = dataBar.map(x => x["total"])

    var ctx = document.getElementById("myAreaChart");

    var myLineChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: transaction_x,
            datasets: [{
                label: "Thống kê tiền",
                backgroundColor: "rgba(2,117,216,1)",
                borderColor: "rgba(2,117,216,1)",
                data: transaction_y,
            }],
        },
        options: {

            maintainAspectRatio: false,
            scales: {
                xAxes: [{
                    time: {
                        unit: 'month'
                    },
                    gridLines: {
                        display: false
                    },
                    ticks: {
                        maxTicksLimit: 2
                    },
                    maxBarThickness: 60,
                }],
                yAxes: [{
                    ticks: {
                        min: 0,
                        max: 1000000000,
                        maxTicksLimit: 10,
                        padding:5
                    },
                    gridLines: {
                        display: true
                    }
                }],
            },
            legend: {
                display: false
            }
        }
    });










});








// Pie Chart Example
// var ctx = document.getElementById("myPieChart");
// var myPieChart = new Chart(ctx, {
//     type: 'pie',
//     data: {
//         labels: ["Blue", "Red", "Yellow", "Green"],
//         datasets: [{
//             data: [12.21, 15.58, 11.25, 8.32],
//             backgroundColor: ['#007bff', '#dc3545', '#ffc107', '#28a745'],
//         }],
//     },
// });