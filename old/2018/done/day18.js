console.log("** DAY 18 **");
console.log("");


////// Part 1

function init(input) {
	var grid = [];
	for (let i = 0; i < input.length; i++) {
		grid.push(input[i].split(""));
	}

	return grid;
}

function print(grid) {
	console.log("");
	for (let y = 0; y < grid.length; y++) {
		var line = "";
		for (let x = 0; x < grid[y].length; x++) {
			line += grid[y][x];
		}
		console.log(line);
	}
}

function population(grid) {
	var pop = {
		open: 0,
		wooded: 0,
		lumberyard: 0
	};
	for (let y = 0; y < grid.length; y++) {
		for (let x = 0; x < grid[y].length; x++) {
			if (grid[y][x] == ".") {
				pop.open++;
			} else if (grid[y][x] == "|") {
				pop.wooded++;
			} else if (grid[y][x] == "#") {
				pop.lumberyard++;
			}
		}
	}
	return pop;
}

function runCollection(grid) {
	var newGrid = [];
	for (let y = 0; y < grid.length; y++) {
		newGrid.push([]);
		for (let x = 0; x < grid[y].length; x++) {
			var subGrid = [];

			if (grid[y - 1]) {
				if (grid[y - 1][x - 1]) {
					subGrid.push(grid[y - 1][x - 1]);
				}
				if (grid[y - 1][x + 1]) {
					subGrid.push(grid[y - 1][x + 1]);
				}
				subGrid.push(grid[y - 1][x]);
			}
			if (grid[y + 1]) {
				if (grid[y + 1][x - 1]) {
					subGrid.push(grid[y + 1][x - 1]);
				}
				if (grid[y + 1][x + 1]) {
					subGrid.push(grid[y + 1][x + 1]);
				}
				subGrid.push(grid[y + 1][x]);
			}
			if (grid[y][x - 1]) {
				subGrid.push(grid[y][x - 1]);
			}
			if (grid[y][x + 1]) {
				subGrid.push(grid[y][x + 1]);
			}

			var pop = population(subGrid);
			
			if (grid[y][x] == ".") {
				if (pop.wooded >= 3) {
					newGrid[y][x] = "|";
				} else {
					newGrid[y][x] = ".";
				}
			} else if (grid[y][x] == "|") {
				if (pop.lumberyard >= 3) {
					newGrid[y][x] = "#";
				} else {
					newGrid[y][x] = "|";
				}
			} else if (grid[y][x] == "#") {
				if (pop.lumberyard >= 1 && pop.wooded >= 1) {
					newGrid[y][x] = "#";
				} else {
					newGrid[y][x] = ".";
				}
			}
		}
	}
	return newGrid;
}

function runTenMinutes(input) {
	var grid = init(input);
	print(grid);

	for (let i = 0; i < 10; i++) {
		grid = runCollection(grid);
		print(grid);
	}

	var pop = population(grid);
	return pop.wooded * pop.lumberyard;
}


function testRunTenMinutes(input, expected) {
	let t1 = runTenMinutes(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testRunTenMinutes([
	".#.#...|#.",
	".....#|##|",
	".|..|...#.",
	"..|#.....#",
	"#.#|||#|#|",
	"...#.||...",
	".|....|...",
	"||...#|.#|",
	"|.||||..|.",
	"...#.|..|."
], 1147);
console.log("");


////// Part 2

function runYears(input) {
	var grid = init(input);
	//print(grid);

	var schema = [];

	var id = 0;
	for (let i = 1; i <= 1000000000; i++) {
		if (i < 4000) {
			grid = runCollection(grid);
		} else if (i >= 4000 && i < 4028) {
			grid = runCollection(grid);
			var pop = population(grid)
			//console.log(pop);
			//console.log(i, pop.wooded * pop.lumberyard);
			schema.push(pop.wooded * pop.lumberyard);
		} else if (i >= 4028 && i < 5000) {
			grid = runCollection(grid);
			var pop = population(grid)
			console.log(i, schema[id], pop.wooded * pop.lumberyard, schema[id] == pop.wooded * pop.lumberyard);
			id++;
			if (id == 28) {
				id = 0;
			}
		} else if (i == 1000000000) {
			var pop = population(grid)
			console.log(i, schema[id]);
		} else {
			id++;
			if (id == 28) {
				id = 0;
			}
		}
		
		//print(grid);
	}
	console.log(schema, schema.length);

	var minutes = 1000000000 - 4000;
	console.log(minutes % 28);
	console.log(schema[schema.length - (minutes % 28) - 1]);

	/*var id = 0;
	for (let i = 3999; i < 6000; i++) {
		grid = runCollection(grid);
		var pop = population(grid)
		console.log(i, pop.wooded * pop.lumberyard,  schema[id], pop.wooded * pop.lumberyard == schema[id]);
		id++;
		if (id == 27) {
			id = 0;
		}
	}*/
}



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
		console.log("Count trees and lumberyard after 10 minutes: " + runTenMinutes(output));
		console.log("Count trees and lumberyard after years: " + runYears(output));
	});
}

dayAnswer("./day18-input");