### Build

- Go to root folder 
- Run mvn clean install

### Run Application

java -jar target/SportsScoreBoard-1.0-SNAPSHOT.war

### URLs to Test


`<Swagger-UI>` : <http://localhost:8080/swagger-ui.html> - You can call below listed APIs 
    - /game/create - Create new game that would be immediately become LIVE
	- /score/update - Update the score against each team
	-/game/end - End the game

`<Scoreboard-UI>` : <http://localhost:8080/scoreboard> - Score updated would be immediately reflected in screen.

`<H2 Database console>` : <http://localhost:8080/h2-console/> - Database console