// Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
// Chart.defaults.global.defaultFontColor = '#292b2c';


$(document).ready(function () {

console.log(dataPie);


    var transaction_x = dataPie.map(x => x["name"])
    var transaction_y = dataPie.map(x => x["total"])

    var ctx = document.getElementById("myPieChart");

    var myPieChart = new Chart(ctx, {
        type: 'pie',
        data: {
            labels: transaction_x,
            datasets: [{
                data: transaction_y,
                backgroundColor: ['#007bff', '#dc3545', '#ffc107', '#28a745','#6C47FF'],
            }],
        },
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