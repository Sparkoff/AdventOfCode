console.log("** DAY 20 animated **");
console.log("");


function dimensions(input) {
	input = input.substr(1, input.length - 2);

	var grid = [];
	var x = 0;
	var y = 0;
	var last = [];
	var pos = "";
	for (let i = 0; i < input.length; i++) {
		if (input[i] == "(") {
			last.push({
				x: x,
				y: y
			});
		} else if (input[i] == ")") {
			var coord = last.pop();
			x = coord.x;
			y = coord.y;
		} else if (input[i] == "|") {
			var coord = last[last.length - 1];
			x = coord.x;
			y = coord.y;
		} else {
			if (input[i] == "E") {
				x++;
			} else if (input[i] == "W") {
				x--;
			} else if (input[i] == "S") {
				y++;
			} else if (input[i] == "N") {
				y--;
			}

			pos = x + "," + y;
			if (grid.indexOf(pos) == -1) {
				grid.push(pos);
			}
		}
	}

	var xmin = 0;
	var xmax = 0;
	var ymin = 0;
	var ymax = 0;
	for (let i = 0; i < grid.length; i++) {
		pos = grid[i].split(",");
		if (Number(pos[0]) < xmin) {
			xmin = Number(pos[0]);
		} else if (Number(pos[0]) > xmax) {
			xmax = Number(pos[0]);
		}
		if (Number(pos[1]) < ymin) {
			ymin = Number(pos[1]);
		} else if (Number(pos[1]) > ymax) {
			ymax = Number(pos[1]);
		}
	}

	return {
		xmin: (xmin * 2) - 1,
		xmax: (xmax * 2) + 1,
		ymin: (-ymax * 2) - 1,
		ymax: (-ymin * 2) + 1
	};
}

function print(grid) {
	console.clear();
	var line = "+";
	for (let x = 0; x < grid[0].length; x++) {
		line += "-";
	}
	console.log(line + "+");
	for (let y = 0; y < grid.length; y++) {
		line = "|";
		for (let x = 0; x < grid[y].length; x++) {
			line += grid[y][x];
		}
		console.log(line + "|");
	}
	var line = "+";
	for (let x = 0; x < grid[0].length; x++) {
		line += "-";
	}
	console.log(line + "+");
}

function addWalls(grid, x, y) {
	console.log(x,y);
	// walls
	if (grid[y - 1][x - 1] == " ") {
		grid[y - 1][x - 1] = "#";
	}
	if (grid[y - 1][x + 1] == " ") {
		grid[y - 1][x + 1] = "#";
	}
	if (grid[y + 1][x - 1] == " ") {
		grid[y + 1][x - 1] = "#";
	}
	if (grid[y + 1][x + 1] == " ") {
		grid[y + 1][x + 1] = "#";
	}

	//doors ?
	if (grid[y][x - 1] == " ") {
		grid[y][x - 1] = "?";
	}
	if (grid[y][x + 1] == " ") {
		grid[y][x + 1] = "?";
	}
	if (grid[y - 1][x] == " ") {
		grid[y - 1][x] = "?";
	}
	if (grid[y + 1][x] == " ") {
		grid[y + 1][x] = "?";
	}

	return grid;
}

function explore(input) {
	var dim = dimensions(input);

	var grid = [];
	var x0 = 1 - dim.xmin;
	var y0 = 1 - dim.ymin;
	for (let y = 0; y < dim.ymax - dim.ymin + 1; y++) {
		grid.push([]);
		for (let x = 0; x < dim.xmax - dim.xmin + 1; x++) {
			if (x == x0 && y == y0) {
				grid[y].push("X");
			} else {
				grid[y].push(" ");
			}
		}
	}
	grid = addWalls(grid, x0, y0);

	x1 = x0;
	y1 = x0;
	var last = [];
	for (let i = 0; i < input.length; i++) {
		grid[y0][x0] = ".";
		if (input[i] == "(") {
			last.push({
				x: x0,
				y: y0
			});
		} else if (input[i] == ")") {
			var coord = last.pop();
			x0 = coord.x;
			y0 = coord.y;
		} else if (input[i] == "|") {
			var coord = last[last.length - 1];
			x0 = coord.x;
			y0 = coord.y;
		} else {
			if (input[i] == "E") {
				x1 = x0 + 2;
				grid[y0][x0 + 1] = "|";
			} else if (input[i] == "W") {
				x1 = x0 - 2;
				grid[y0][x0 - 1] = "|";
			} else if (input[i] == "S") {
				y1 = y0 + 2;
				grid[y0 + 1][x0] = "-";
			} else if (input[i] == "N") {
				y1 = y0 - 2;
				grid[y0 - 1][x0] = "-";
			}
			grid = addWalls(grid, x1, y1);
			x0 = x1;
			y0 = y1;
		}
		grid[y0][x0] = "X";
		print(grid);
	}

	// terminal walls
	for (let y = 0; y < grid.length; y++) {
		for (let x = 0; x < grid[y].length; x++) {
			if (grid[y][x] == "?") {
				grid[y][x] = "#";
			}
		}
	}

	print(grid);
}


function animate(file) {
	let output = "";

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		output = input;
	});

	rl.on('close', () => {
		explore(output);
	});
}

animate("./day20-input");
