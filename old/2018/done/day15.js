console.log("** DAY 15 **");
console.log("");


////// Part 1

function init(input, elfAttack) {
	var dimX = input[0].length;
	var dimY = input.length;

	var cave = [];

	// init cave
	for (let y = 0; y < dimY; y++) {
		cave.push([]);
		for (let x = 0; x < dimX; x++) {
			var element = {
				type: "#",
				unit: null
			};
			if (input[y][x] != "#") {
				element.type = ".";
			}
			if (input[y][x] == "E" || input[y][x] == "G") {
				element.unit = {
					type: input[y][x],
					hp: 200,
					attack: 3
				};
				if (input[y][x] == "E") {
					element.unit.attack = elfAttack;
				}
			}
			cave[y].push(element);
		}
	}
	return cave;
}
function printCave(cave) {
	for (let y = 0; y < cave.length; y++) {
		var line = "";
		var hps = [];
		for (let x = 0; x < cave[y].length; x++) {
			if (cave[y][x].unit != null) {
				line += cave[y][x].unit.type;
				hps.push(cave[y][x].unit.type + "(" + cave[y][x].unit.hp + ")")
			} else if (cave[y][x].weight != undefined) {
				line += cave[y][x].weight;
			} else {
				line += cave[y][x].type;
			}
		}
		if (hps.length != 0) {
			line += "   " + hps.join(", ");
		}
		console.log(line);
	}
}

function aStar(unitCoord, cave) {
	var unitType = cave[unitCoord.y][unitCoord.x].unit.type;

	var explored = [];
	var newCave = [];
	for (let y = 0; y < cave.length; y++) {
		newCave.push([]);
		for (let x = 0; x < cave[y].length; x++) {
			if (cave[y][x].unit != null) {
				if (cave[y][x].unit.type != unitType) {
					if (Math.abs(unitCoord.x - x) + Math.abs(unitCoord.y - y) == 1) {
						return unitCoord;
					}
					explored.push({
						x: x,
						y: y
					});
					newCave[y].push({
						type: cave[y][x].unit.type
					});
				} else if (unitCoord.x == x && unitCoord.y == y) {
					newCave[y].push({
						type: cave[y][x].unit.type
					});
				} else {
					newCave[y].push({
						type: "#"
					});
				}
			} else if (cave[y][x].type == ".") {
				newCave[y].push({
					type: "."
				});
			} else {
				newCave[y].push({
					type: "#"
				});
			}
		}
	}
	//printCave(newCave);

	var weight = 1;
	var finished = false;
	while (!finished) {
		var newExplored = [];
		for (let i = 0; i < explored.length; i++) {
			var x = explored[i].x;
			var y = explored[i].y;
			if (newCave[y][x + 1] && newCave[y][x + 1].type == "." && (newCave[y][x + 1].weight == undefined || newCave[y][x + 1].weight > weight)) {
				newCave[y][x + 1].weight = weight;
				newExplored.push({
					x: x + 1,
					y: y
				});
			}
			if (newCave[y][x - 1] && newCave[y][x - 1].type == "." && (newCave[y][x - 1].weight == undefined || newCave[y][x - 1].weight > weight)) {
				newCave[y][x - 1].weight = weight;
				newExplored.push({
					x: x - 1,
					y: y
				});
			}
			if (newCave[y + 1][x] && newCave[y + 1][x].type == "." && (newCave[y + 1][x].weight == undefined || newCave[y + 1][x].weight > weight)) {
				newCave[y + 1][x].weight = weight;
				newExplored.push({
					x: x,
					y: y + 1
				});
			}
			if (newCave[y - 1][x] && newCave[y - 1][x].type == "." && (newCave[y - 1][x].weight == undefined || newCave[y - 1][x].weight > weight)) {
				newCave[y - 1][x].weight = weight;
				newExplored.push({
					x: x,
					y: y - 1
				});
			}
		}

		if (newExplored.length == 0) {
			finished = true;
		} else {
			explored = newExplored;
		}
		weight++;
	}
	//printCave(newCave);

	var newX = unitCoord.x;
	var newY = unitCoord.y;
	weight = 1000;
	if (newCave[unitCoord.y - 1][unitCoord.x].weight != undefined && newCave[unitCoord.y - 1][unitCoord.x].weight < weight) {
		weight = newCave[unitCoord.y - 1][unitCoord.x].weight;
		newX = unitCoord.x;
		newY = unitCoord.y - 1;
	}
	if (newCave[unitCoord.y][unitCoord.x - 1].weight != undefined && newCave[unitCoord.y][unitCoord.x - 1].weight < weight) {
		weight = newCave[unitCoord.y][unitCoord.x - 1].weight;
		newX = unitCoord.x - 1;
		newY = unitCoord.y;
	}
	if (newCave[unitCoord.y][unitCoord.x + 1].weight != undefined && newCave[unitCoord.y][unitCoord.x + 1].weight < weight) {
		weight = newCave[unitCoord.y][unitCoord.x + 1].weight;
		newX = unitCoord.x + 1;
		newY = unitCoord.y;
	}
	if (newCave[unitCoord.y + 1][unitCoord.x].weight != undefined && newCave[unitCoord.y + 1][unitCoord.x].weight < weight) {
		weight = newCave[unitCoord.y + 1][unitCoord.x].weight;
		newX = unitCoord.x;
		newY = unitCoord.y + 1;
	}

	return {
		x: newX,
		y: newY
	};
}

function fight(unitCoord, cave) {
	var x = unitCoord.x;
	var y = unitCoord.y;
	var unitType = cave[y][x].unit.type;
	
	var enemyX = x;
	var enemyY = y;
	var hp = 1000;
	
	if (cave[y - 1][x].unit != null && cave[y - 1][x].unit.type != unitType && cave[y - 1][x].unit.hp < hp) {
		hp = cave[y - 1][x].unit.hp;
		enemyX = x;
		enemyY = y - 1;
	}
	if (cave[y][x - 1].unit != null && cave[y][x - 1].unit.type != unitType && cave[y][x - 1].unit.hp < hp) {
		hp = cave[y][x - 1].unit.hp;
		enemyX = x - 1;
		enemyY = y;
	}
	if (cave[y][x + 1].unit != null && cave[y][x + 1].unit.type != unitType && cave[y][x + 1].unit.hp < hp) {
		hp = cave[y][x + 1].unit.hp;
		enemyX = x + 1;
		enemyY = y;
	}
	if (cave[y + 1][x].unit != null && cave[y + 1][x].unit.type != unitType && cave[y + 1][x].unit.hp < hp) {
		hp = cave[y + 1][x].unit.hp;
		enemyX = x;
		enemyY = y + 1;
	}

	return {
		x: enemyX,
		y: enemyY
	};
}

function countUnits(cave) {
	var units = {
		elfs: 0,
		goblins: 0
	};
	for (let y = 0; y < cave.length; y++) {
		for (let x = 0; x < cave[y].length; x++) {
			if (cave[y][x].unit != null) {
				if (cave[y][x].unit.type == "E") {
					units.elfs++;
				} else {
					units.goblins++;
				}
			}
		}
	}
	return units;
}

function runCycle(input, elfAttack) {
	var dimX = input[0].length;
	var dimY = input.length;

	var cave = init(input, elfAttack);
	//printCave(cave);

	var elfsCountStart = countUnits(cave).elfs;

	var turn = 1;
	var finished = false;
	var finishedDuringTurn = false;
	while (!finished) {
		// console.log("");
		// console.log(turn);

		var remains = countUnits(cave);
		if (remains.elfs == 0 || remains.goblins == 0) {
			//console.log(turn, "start turn");
			finished = true;
		} else {
			var unitList = [];
		
			for (let y = 0; y < dimY; y++) {
				for (let x = 0; x < dimX; x++) {
					if (cave[y][x].unit != null) {
						unitList.push({
							x:x,
							y:y
						});
					}
				}
			}
			//console.log(unitList.length, elfsCount);

			for (let i = 0; i < unitList.length; i++) {
				remains = countUnits(cave);
				if (remains.elfs == 0 || remains.goblins == 0) {
					//console.log(turn, "in turn");
					finishedDuringTurn = true;
					break;
				} else {
					if (cave[unitList[i].y][unitList[i].x].unit != null) {
						var newCoord = aStar(unitList[i], cave);

						if (newCoord.x != unitList[i].x || newCoord.y != unitList[i].y) {
							cave[newCoord.y][newCoord.x].unit = cave[unitList[i].y][unitList[i].x].unit;
							cave[unitList[i].y][unitList[i].x].unit = null;
						}

						var enemy = fight(newCoord, cave);
						if (enemy.x != newCoord.x || enemy.y != newCoord.y) {
							cave[enemy.y][enemy.x].unit.hp -= cave[newCoord.y][newCoord.x].unit.attack;
							if (cave[enemy.y][enemy.x].unit.hp <= 0) {
								cave[enemy.y][enemy.x].unit = null
							}
						}
					}
				}
			}
			//console.log("After " + turn + " rounds:");
			//printCave(cave);

			remains = countUnits(cave);

			if (remains.elfs != 0 && remains.goblins != 0) {
				//console.log(turn, "both units remaining");
				turn++;
			}
		}
	}

	var hp = 0;
	for (let y = 0; y < dimY; y++) {
		for (let x = 0; x < dimX; x++) {
			if (cave[y][x].unit != null) {
				hp += cave[y][x].unit.hp;
			}
		}
	}
	var remainingElfs = countUnits(cave).elfs;
	// console.log("");
	// console.log("After " + turn + " rounds:");
	// printCave(cave);
	console.log("turn " + (finishedDuringTurn ? "incompleted" : "completed  ") + ": " + hp + "*[" + turn + "," + (turn - 1) + "]", (hp * turn), hp * (turn - 1), "elfs:", elfAttack, elfsCountStart, remainingElfs);
	
	return {
		outcome: finishedDuringTurn ? hp * (turn - 1) : hp * turn,
		elfsCountStart: elfsCountStart,
		elfCount: remainingElfs
	};
}

function runGame(input) {
	return runCycle(input, 3).outcome;
}


function testRunGame(input, expected) {
	let t1 = runGame(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testRunGame([
	"#######",
	"#.G...#",
	"#...EG#",
	"#.#.#G#",
	"#..G#E#",
	"#.....#",
	"#######"
], 27730);
testRunGame([
	"#######",
	"#G..#E#",
	"#E#E.E#",
	"#G.##.#",
	"#...#E#",
	"#...E.#",
	"#######"
], 36334);
testRunGame([
	"#######",
	"#E..EG#",
	"#.#G.E#",
	"#E.##E#",
	"#G..#.#",
	"#..E#.#",
	"#######"
], 39514);
testRunGame([
	"#######",
	"#E.G#.#",
	"#.#G..#",
	"#G.#.G#",
	"#G..#.#",
	"#...E.#",
	"#######"
], 27755);
testRunGame([
	"#######",
	"#.E...#",
	"#.#..G#",
	"#.###.#",
	"#E#G#G#",
	"#...#G#",
	"#######"
], 28944);
testRunGame([
	"#########",
	"#G......#",
	"#.E.#...#",
	"#..##..G#",
	"#...##..#",
	"#...#...#",
	"#.G...G.#",
	"#.....G.#",
	"#########"
], 18740);
console.log("");


////// Part 2

function runGameNoLoss(input) {
	var attack = 4;
	while (true) {
		var result = runCycle(input, attack);
		if (result.elfsCountStart == result.elfCount) {
			return result.outcome;
		} else {
			attack++;
		}
	}
}


function testRunGameNoLoss(input, expected) {
	let t2 = runGameNoLoss(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testRunGameNoLoss([
	"#######",
	"#.G...#",
	"#...EG#",
	"#.#.#G#",
	"#..G#E#",
	"#.....#",
	"#######"
], 4988);
testRunGameNoLoss([
	"#######",
	"#E..EG#",
	"#.#G.E#",
	"#E.##E#",
	"#G..#.#",
	"#..E#.#",
	"#######"
], 31284);
testRunGameNoLoss([
	"#######",
	"#E.G#.#",
	"#.#G..#",
	"#G.#.G#",
	"#G..#.#",
	"#...E.#",
	"#######"
], 3478);
testRunGameNoLoss([
	"#######",
	"#.E...#",
	"#.#..G#",
	"#.###.#",
	"#E#G#G#",
	"#...#G#",
	"#######"
], 6474);
testRunGameNoLoss([
	"#########",
	"#G......#",
	"#.E.#...#",
	"#..##..G#",
	"#...##..#",
	"#...#...#",
	"#.G...G.#",
	"#.....G.#",
	"#########"
], 1140);
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
		console.log("Final outcome: " + runGame(output));
		console.log("Final outcome without losses: " + runGameNoLoss(output));
	});
}

dayAnswer("./day15-input");