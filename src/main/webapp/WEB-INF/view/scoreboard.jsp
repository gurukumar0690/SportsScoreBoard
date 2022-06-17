<html>
<head>
<meta http-equiv="refresh" content="200">
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js" integrity="sha512-iKDtgDyTHjAitUDdLljGhenhPwrbBfqTKWO1mkhSFH3A7blITC9MhYon6SjnMhp4o0rADGw9yAC6EW4t5a4K3g==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<style>
* {
  box-sizing: border-box;
}

body {
  font-family: Arial, Helvetica, sans-serif;
}

/* Float four columns side by side */
.column {
  float: left;
  width: 25%;
  padding: 0 10px;
}

/* Remove extra left and right margins, due to padding in columns */
.row {margin: 0 -5px;}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Style the counter cards */
.card {
  box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2); /* this adds the "card" effect */
  padding: 16px;
  text-align: center;
  background-color: #f1f1f1;
}
</style>
</head>
<body>
    <div class="row" id="scorecard">

     </div>
</body>
<script type="text/javascript">
        var stompClient = null;

        function connect() {
            var socket = new SockJS("http://localhost:8080/scoreboard");
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function () {
                stompClient.subscribe('/topic/score', function (data) {
                    var json = JSON.parse(data.body)
                    var scoreCard = "<div class='column'> <div class='card'>";
                    scoreCard+= "<h3>"+json.gameDescription+"</h3>";
                    scoreCard+= "<table style='width: 100%;margin-left: 20px;'><tr><td>"+json.teamAName+"</td><td>"+json.teamBName+"</td></tr><tr><td>"+json.teamAScore+"</td><td>"+json.teamBScore+"</td></tr></table>";
                    scoreCard+= "</div></div>";
                    document.getElementById("scorecard").innerHTML = scoreCard;
                });
            });
        }

        function disconnect() {
            if (stompClient != null) {
                stompClient.disconnect();
            }
            console.log("Disconnected");
        }

        connect();
</script>
</html>