console.log("** DAY 09 **");
console.log("");


////// Part 1

function insertInCircle(circle, currentMarble, marble) {
	if (circle.length == 1) {
		currentMarble = 1;
		circle.push(marble);
	} else {
		currentMarble += 2;
		if (currentMarble > circle.length) {
			currentMarble -= circle.length;
		}
		circle.splice(currentMarble, 0, marble);
	}

	return {
		circle: circle,
		currentMarbleIndex: currentMarble
	};
}

function getHighscore(playerCount, lastMarble) {
	var playerScores = new Array(playerCount).fill(0);
	var circle = [];
	circle.push(0);
	var currentMarbleIndex = 0;
	var currentPlayer = 0;

	for (let i = 1; i <= lastMarble; i++) {
		if (i % 10000 == 0) {
			console.log(i, lastMarble-i);
		}
		currentPlayer++;
		if (currentPlayer > playerCount) {
			currentPlayer = 1;
		}

		if (i % 23 == 0) {
			playerScores[currentPlayer - 1] += i;
			currentMarbleIndex -= 7;
			if (currentMarbleIndex < 0) {
				currentMarbleIndex += circle.length;
			}
			playerScores[currentPlayer - 1] += circle.splice(currentMarbleIndex, 1)[0];
		} else {
			var nextState = insertInCircle(circle, currentMarbleIndex, i);
			circle = nextState.circle;
			currentMarbleIndex = nextState.currentMarbleIndex;
		}
	}

	return playerScores.sort(function (a,b) {
		return Number(a) - Number(b);
	})[playerScores.length-1];
}


function testHighscore(playerCount, lastMarble, expected) {
	let t1 = getHighscore(playerCount, lastMarble);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testHighscore(9, 25, 32);
testHighscore(10, 1618, 8317);
testHighscore(13, 7999, 146373);
testHighscore(17, 1104, 2764);
testHighscore(21, 6111, 54718);
testHighscore(30, 5807, 37305);
console.log("");


////// Part 2 

function getDynamicHighscore(playerCount, lastMarble) {
	var playerScores = new Array(playerCount).fill(0);
	var firstMarble = {
		next: null,
		prev: null,
		value: 0
	};
	var currentMarble = firstMarble;
	var currentPlayer = 0;

	for (let i = 1; i <= lastMarble; i++) {
		if (i % 10000 == 0) {
			console.log(i, lastMarble - i);
		}
		currentPlayer++;
		if (currentPlayer > playerCount) {
			currentPlayer = 1;
		}

		if (i % 23 == 0) {
			playerScores[currentPlayer - 1] += i;
			for (let p = 0; p < 7; p++) {
				currentMarble = currentMarble.prev;
			}
			playerScores[currentPlayer - 1] += currentMarble.value;
			currentMarble.prev.next = currentMarble.next;
			currentMarble.next.prev = currentMarble.prev;
			currentMarble = currentMarble.next;
		} else if (i == 1) {
			var newMarble = {
				next: firstMarble,
				prev: firstMarble,
				value: i
			};
			firstMarble.next = newMarble;
			firstMarble.prev = newMarble;
			currentMarble = newMarble;
		} else {
			currentMarble = currentMarble.next;
			var newMarble = {
				next: currentMarble.next,
				prev: currentMarble,
				value: i
			};
			currentMarble.next.prev = newMarble;
			currentMarble.next = newMarble;
			currentMarble = newMarble;
		}
	}

	return playerScores.sort(function (a, b) {
		return Number(a) - Number(b);
	})[playerScores.length - 1];
}


function testDynamicHighscore(playerCount, lastMarble, expected) {
	let t2 = getDynamicHighscore(playerCount, lastMarble);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testDynamicHighscore(9, 25, 32);
testDynamicHighscore(10, 1618, 8317);
testDynamicHighscore(13, 7999, 146373);
testDynamicHighscore(17, 1104, 2764);
testDynamicHighscore(21, 6111, 54718);
testDynamicHighscore(30, 5807, 37305);
console.log("");


////// Day Answer

function dayAnswer() {
	console.log("Highest score: " + getHighscore(491, 71058));
	//console.log("Highest score in new game: " + getHighscore(491, 7105800));
	console.log("Highest score in new game: " + getDynamicHighscore(491, 7105800));
}

dayAnswer();