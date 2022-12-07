console.log("** DAY 13 **");
console.log("");


////// Part 1

function init(input) {
	var dimX = input[0].length;
	var dimY = input.length;

	var table = [];

	// init table
	for (let y = 0; y < dimY; y++) {
		table.push([]);
		for (let x = 0; x < dimX; x++) {
			if (input[y][x] != "") {
				var element = {
					type: "",
					cart: null
				};
				if (input[y][x] == "^") {
					element.type = "|";
					element.cart = {
						dir: 0,
						type: "^"
					};
				} else if (input[y][x] == "v") {
					element.type = "|";
					element.cart = {
						dir: 0,
						type: "v"
					};
				} else if (input[y][x] == "<") {
					element.type = "-";
					element.cart = {
						dir: 0,
						type: "<"
					};
				} else if (input[y][x] == ">") {
					element.type = "-";
					element.cart = {
						dir: 0,
						type: ">"
					};
				} else {
					element.type = input[y][x];
				}
				table[y].push(element);
			} else {
				table[y].push(null);
			}
		}
	}
	return table;
}
function printTable(table) {
	for (let y = 0; y < table.length; y++) {
		var line = "";
		for (let x = 0; x < table[y].length; x++) {
			if (table[y][x] == null) {
				line += " ";
			} else if (table[y][x].cart == null) {
				line += table[y][x].type;
			} else {
				line += table[y][x].cart.type;
			}
		}
		console.log(line);
	}
}

function runCycle(input) {
	var dimX = input[0].length;
	var dimY = input.length;
	
	var table = init(input);
	//printTable(table);

	var crash = null;
	while (crash == null) {
		var newTable = [];
		
		// make empty table
		for (let y = 0; y < dimY; y++) {
			newTable.push([]);
			for (let x = 0; x < dimX; x++) {
				if (table[y][x]) {
					newTable[y].push({
						type: table[y][x].type,
						cart: null
					});
				} else {
					newTable[y].push(null);
				}
			}
		}

		// populate with carts
		for (let y = 0; y < dimY; y++) {
			for (let x = 0; x < dimX; x++) {
				if (table[y][x] && table[y][x].cart) {
					if (table[y][x].cart.type == "^") {
						if (newTable[y - 1][x].cart != null) {
							newTable[y - 1][x].cart.type = "X";
							newTable[y - 1][x].cart.dir = -1;
							crash = x + "," + (y - 1);
						} else if (newTable[y - 1][x].type == "|") {
							newTable[y - 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y - 1][x].type == "\\") {
							newTable[y - 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: "<"
							};
						} else if (newTable[y - 1][x].type == "/") {
							newTable[y - 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: ">"
							};
						} else {
							newTable[y - 1][x].cart = table[y][x].cart;
							if (newTable[y - 1][x].cart.dir == 0) {
								newTable[y - 1][x].cart = {
									dir: 1,
									type: "<"
								};
							} else if (newTable[y - 1][x].cart.dir == 1) {
								newTable[y - 1][x].cart = {
									dir: 2,
									type: "^"
								};
							} else if (newTable[y - 1][x].cart.dir == 2) {
								newTable[y - 1][x].cart = {
									dir: 0,
									type: ">"
								};
							}
						}
					} else if (table[y][x].cart.type == "v") {
						if (table[y + 1][x].cart != null) {
							newTable[y + 1][x].cart = {
								dir: -1,
								type: "X"
							};
							table[y + 1][x].cart = null;
							crash = x + "," + (y + 1);
						} else if (newTable[y + 1][x].type == "|") {
							newTable[y + 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y + 1][x].type == "\\") {
							newTable[y + 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: ">"
							};
						} else if (newTable[y + 1][x].type == "/") {
							newTable[y + 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: "<"
							};
						} else {
							newTable[y + 1][x].cart = table[y][x].cart;
							if (newTable[y + 1][x].cart.dir == 0) {
								newTable[y + 1][x].cart = {
									dir: 1,
									type: ">"
								};
							} else if (newTable[y + 1][x].cart.dir == 1) {
								newTable[y + 1][x].cart = {
									dir: 2,
									type: "v"
								};
							} else if (newTable[y + 1][x].cart.dir == 2) {
								newTable[y + 1][x].cart = {
									dir: 0,
									type: "<"
								};
							}
						}
					} else if (table[y][x].cart.type == "<") {
						if (newTable[y][x - 1].cart != null) {
							newTable[y][x - 1].cart.type = "X";
							newTable[y][x - 1].cart.dir = -1;
							crash = (x - 1) + "," + y;
						} else if (newTable[y][x - 1].type == "-") {
							newTable[y][x - 1].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y][x - 1].type == "\\") {
							newTable[y][x - 1].cart = {
								dir: table[y][x].cart.dir,
								type: "^"
							};
						} else if (newTable[y][x - 1].type == "/") {
							newTable[y][x - 1].cart = {
								dir: table[y][x].cart.dir,
								type: "v"
							};
						} else {
							newTable[y][x - 1].cart = table[y][x].cart;
							if (newTable[y][x - 1].cart.dir == 0) {
								newTable[y][x - 1].cart = {
									dir: 1,
									type: "v"
								};
							} else if (newTable[y][x - 1].cart.dir == 1) {
								newTable[y][x - 1].cart = {
									dir: 2,
									type: "<"
								};
							} else if (newTable[y][x - 1].cart.dir == 2) {
								newTable[y][x - 1].cart = {
									dir: 0,
									type: "^"
								};
							}
						}
					} else if (table[y][x].cart.type == ">") {
						if (newTable[y][x + 1].cart != null) {
							newTable[y][x + 1].cart.type = "X";
							newTable[y][x + 1].cart.dir = -1;
							crash = (x + 1) + "," + y;
						} else if (table[y][x + 1].cart != null) {
							newTable[y][x + 1].cart = {
								dir: -1,
								type: "X"
							};
							table[y][x + 1].cart = null;
							crash = (x + 1) + "," + y;
						} else if (newTable[y][x + 1].type == "-") {
							newTable[y][x + 1].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y][x + 1].type == "\\") {
							newTable[y][x + 1].cart = {
								dir: table[y][x].cart.dir,
								type: "v"
							};
						} else if (newTable[y][x + 1].type == "/") {
							newTable[y][x + 1].cart = {
								dir: table[y][x].cart.dir,
								type: "^"
							};
						} else {
							newTable[y][x + 1].cart = table[y][x].cart;
							if (newTable[y][x + 1].cart.dir == 0) {
								newTable[y][x + 1].cart = {
									dir: 1,
									type: "^"
								};
							} else if (newTable[y][x + 1].cart.dir == 1) {
								newTable[y][x + 1].cart = {
									dir: 2,
									type: ">"
								};
							} else if (newTable[y][x + 1].cart.dir == 2) {
								newTable[y][x + 1].cart = {
									dir: 0,
									type: "v"
								};
							}
						}
					}
				}
			}
		}

		// if (crash) {
		// 	printTable(table)
		// }
		// printTable(newTable);
		table = newTable;
	}
	//printTable(table);

	return crash;
}


function testRunCycle(input, expected) {
	let t1 = runCycle(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testRunCycle([
	"/->-\\        ",
	"|   |  /----\\",
	"| /-+--+-\\  |",
	"| | |  | v  |",
	"\\-+-/  \\-+--/",
	"  \\------/   "
], "7,3");
console.log("");


////// Part 2

function countCarts(table) {
	var count = 0;
	for (let y = 0; y < table.length; y++) {
		for (let x = 0; x < table[y].length; x++) {
			if (table[y][x] && table[y][x].cart) {
				count++;
			}
		}
	}
	return count;
}

function runCycleLastSurvivor(input) {
	var dimX = input[0].length;
	var dimY = input.length;

	var table = init(input);
	//printTable(table);

	var activeCart = countCarts(table);
	while (activeCart > 1) {
		var newTable = [];

		// make empty table
		for (let y = 0; y < dimY; y++) {
			newTable.push([]);
			for (let x = 0; x < dimX; x++) {
				if (table[y][x]) {
					newTable[y].push({
						type: table[y][x].type,
						cart: null
					});
				} else {
					newTable[y].push(null);
				}
			}
		}

		// populate with carts
		for (let y = 0; y < dimY; y++) {
			for (let x = 0; x < dimX; x++) {
				if (table[y][x] && table[y][x].cart) {
					if (table[y][x].cart.type == "^") {
						if (newTable[y - 1][x].cart != null) {
							newTable[y - 1][x].cart.type = "X";
							newTable[y - 1][x].cart.dir = -1;
							crash = x + "," + (y - 1);
						} else if (newTable[y - 1][x].type == "|") {
							newTable[y - 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y - 1][x].type == "\\") {
							newTable[y - 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: "<"
							};
						} else if (newTable[y - 1][x].type == "/") {
							newTable[y - 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: ">"
							};
						} else {
							newTable[y - 1][x].cart = table[y][x].cart;
							if (newTable[y - 1][x].cart.dir == 0) {
								newTable[y - 1][x].cart = {
									dir: 1,
									type: "<"
								};
							} else if (newTable[y - 1][x].cart.dir == 1) {
								newTable[y - 1][x].cart = {
									dir: 2,
									type: "^"
								};
							} else if (newTable[y - 1][x].cart.dir == 2) {
								newTable[y - 1][x].cart = {
									dir: 0,
									type: ">"
								};
							}
						}
					} else if (table[y][x].cart.type == "v") {
						if (table[y + 1][x].cart != null) {
							newTable[y + 1][x].cart = {
								dir: -1,
								type: "X"
							};
							table[y + 1][x].cart = null;
							crash = x + "," + (y + 1);
						} else if (newTable[y + 1][x].type == "|") {
							newTable[y + 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y + 1][x].type == "\\") {
							newTable[y + 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: ">"
							};
						} else if (newTable[y + 1][x].type == "/") {
							newTable[y + 1][x].cart = {
								dir: table[y][x].cart.dir,
								type: "<"
							};
						} else {
							newTable[y + 1][x].cart = table[y][x].cart;
							if (newTable[y + 1][x].cart.dir == 0) {
								newTable[y + 1][x].cart = {
									dir: 1,
									type: ">"
								};
							} else if (newTable[y + 1][x].cart.dir == 1) {
								newTable[y + 1][x].cart = {
									dir: 2,
									type: "v"
								};
							} else if (newTable[y + 1][x].cart.dir == 2) {
								newTable[y + 1][x].cart = {
									dir: 0,
									type: "<"
								};
							}
						}
					} else if (table[y][x].cart.type == "<") {
						if (newTable[y][x - 1].cart != null) {
							newTable[y][x - 1].cart.type = "X";
							newTable[y][x - 1].cart.dir = -1;
							crash = (x - 1) + "," + y;
						} else if (newTable[y][x - 1].type == "-") {
							newTable[y][x - 1].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y][x - 1].type == "\\") {
							newTable[y][x - 1].cart = {
								dir: table[y][x].cart.dir,
								type: "^"
							};
						} else if (newTable[y][x - 1].type == "/") {
							newTable[y][x - 1].cart = {
								dir: table[y][x].cart.dir,
								type: "v"
							};
						} else {
							newTable[y][x - 1].cart = table[y][x].cart;
							if (newTable[y][x - 1].cart.dir == 0) {
								newTable[y][x - 1].cart = {
									dir: 1,
									type: "v"
								};
							} else if (newTable[y][x - 1].cart.dir == 1) {
								newTable[y][x - 1].cart = {
									dir: 2,
									type: "<"
								};
							} else if (newTable[y][x - 1].cart.dir == 2) {
								newTable[y][x - 1].cart = {
									dir: 0,
									type: "^"
								};
							}
						}
					} else if (table[y][x].cart.type == ">") {
						if (newTable[y][x + 1].cart != null) {
							newTable[y][x + 1].cart.type = "X";
							newTable[y][x + 1].cart.dir = -1;
							crash = (x + 1) + "," + y;
						} else if (table[y][x + 1].cart != null) {
							newTable[y][x + 1].cart = {
								dir: -1,
								type: "X"
							};
							table[y][x + 1].cart = null;
							crash = (x + 1) + "," + y;
						} else if (newTable[y][x + 1].type == "-") {
							newTable[y][x + 1].cart = {
								dir: table[y][x].cart.dir,
								type: table[y][x].cart.type
							};
						} else if (newTable[y][x + 1].type == "\\") {
							newTable[y][x + 1].cart = {
								dir: table[y][x].cart.dir,
								type: "v"
							};
						} else if (newTable[y][x + 1].type == "/") {
							newTable[y][x + 1].cart = {
								dir: table[y][x].cart.dir,
								type: "^"
							};
						} else {
							newTable[y][x + 1].cart = table[y][x].cart;
							if (newTable[y][x + 1].cart.dir == 0) {
								newTable[y][x + 1].cart = {
									dir: 1,
									type: "^"
								};
							} else if (newTable[y][x + 1].cart.dir == 1) {
								newTable[y][x + 1].cart = {
									dir: 2,
									type: ">"
								};
							} else if (newTable[y][x + 1].cart.dir == 2) {
								newTable[y][x + 1].cart = {
									dir: 0,
									type: "v"
								};
							}
						}
					}
				}
			}
		}


		for (let y = 0; y < dimY; y++) {
			for (let x = 0; x < dimX; x++) {
				if (newTable[y][x] && newTable[y][x].cart && newTable[y][x].cart.type == "X") {
					newTable[y][x].cart = null;
				}
			}
		}
		activeCart = countCarts(newTable);
		if (activeCart == 1) {
			for (let y = 0; y < dimY; y++) {
				for (let x = 0; x < dimX; x++) {
					if (newTable[y][x] && newTable[y][x].cart) {
						return x + "," + y;
					}
				}
			}
		}

		// if (crash) {
		// 	printTable(table)
		// }
		//printTable(newTable);
		table = newTable;
	}
	//printTable(table);

	return "no cart remains";
}


function testRunCycleLastSurvivor(input, expected) {
	let t2 = runCycleLastSurvivor(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testRunCycleLastSurvivor([
	"/>-<\\  ",
	"|   |  ",
	"| /<+-\\",
	"| | | v",
	"\\>+</ |",
	"  |   ^",
	"  \\<->/"
], "6,4");
console.log("");


////// Day Answer

function dayAnswer(file) {
	let output = [];

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		output.push(input);
	});

	rl.on('close', () => {
		console.log("Crash location: " + runCycle(output));
		console.log("Last cart survivor: " + runCycleLastSurvivor(output));
	});
}

dayAnswer("./day13-input");