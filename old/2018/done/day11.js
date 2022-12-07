console.log("** DAY 11 **");
console.log("");


////// Part 1

function powerLevel(x, y, serial) {
	var rackID = x + 10;
	var powStart = (rackID * y) + serial;
	powStart *= rackID;
	return ((powStart % 1000 - powStart % 100) / 100) - 5;
}

function poweredSquare3x3(serial) {
	var grid = new Array(300).fill("");
	for (let i = 0; i < 300; i++) {
		grid[i] = new Array(300).fill(0);
	}

	for (let i = 0; i < 300; i++) {
		for (let j = 0; j < 300; j++) {
			grid[i][j] = powerLevel(i, j, serial);
		}
	}

	var bestCoord = "0,0";
	var bestPower = 0;
	for (let i = 0; i < 297; i++) {
		for (let j = 0; j < 297; j++) {
			var power = grid[i][j] + grid[i][j + 1] + grid[i][j + 2];
			power += grid[i + 1][j] + grid[i + 1][j + 1] + grid[i + 1][j + 2];
			power += grid[i + 2][j] + grid[i + 2][j + 1] + grid[i + 2][j + 2];

			if (power > bestPower) {
				bestCoord = i + "," + j;
				bestPower = power;
			}
		}
	}

	return bestCoord;
}

function testPowerLevel(x, y, serial, expected) {
	let t1 = powerLevel(x, y, serial);
	if (t1 == expected) {
		console.log("- " + x + "," + y + "," + serial + " -> " + t1 + " : ok");
	} else {
		console.error("- " + x + "," + y + "," + serial + " -> result was " + t1 + ", but expected " + expected);
	}
}
function testPoweredSquare3x3(serial, expected) {
	let t1 = poweredSquare3x3(serial);
	if (t1 == expected) {
		console.log("- " + serial + " -> " + t1 + " : ok");
	} else {
		console.error("- " + serial + " -> result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testPowerLevel(3, 5, 8, 4);
testPowerLevel(122, 79, 57, -5);
testPowerLevel(217, 196, 39, 0);
testPowerLevel(101, 153, 71, 4);
testPoweredSquare3x3(18, "33,45");
testPoweredSquare3x3(42, "21,61");
console.log("");


////// Part 2

function poweredSquare(serial) {
	var grid = new Array(300).fill("");
	for (let i = 0; i < 300; i++) {
		grid[i] = new Array(300).fill(0);
	}

	for (let i = 0; i < 300; i++) {
		for (let j = 0; j < 300; j++) {
			grid[i][j] = powerLevel(i, j, serial);
		}
	}

	var bestCoord = "0,0,n";
	var bestPower = 0;
	for (let n = 1; n <= 300; n++) {
		for (let i = 0; i < 300 - n + 1; i++) {
			for (let j = 0; j < 300 - n + 1; j++) {
				var power = 0;
				for (let i1 = i; i1 < i + n; i1++) {
					for (let j1 = j; j1 < j + n; j1++) {
						power += grid[i1][j1];
					}
				}

				if (power > bestPower) {
					bestCoord = i + "," + j + "," + n;
					bestPower = power;
				}
			}
		}
	}

	return bestCoord;
}


function testPoweredSquare(serial, expected) {
	let t2 = poweredSquare(serial);
	if (t2 == expected) {
		console.log("- " + serial + " -> " + t2 + " : ok");
	} else {
		console.error("- " + serial + " -> result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests part 2:");
testPoweredSquare(18, "90,269,16");
testPoweredSquare(42, "232,251,12");
console.log("");


////// Day Answer

function dayAnswer() {
	console.log("High power coordinate 3x3: " + poweredSquare3x3(7989));
	console.log("High power coordinate nxn: " + poweredSquare(7989));
}

dayAnswer();