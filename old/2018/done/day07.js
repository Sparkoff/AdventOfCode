console.log("** DAY 07 **");
console.log("");


////// Part 1

function assemblyOrder(input) {
	var afters = {};
	var befores = {};
	for (let index = 0; index < input.length; index++) {
		var first = input[index][5];
		var next = input[index][36];

		if (!afters.hasOwnProperty(first)) {
			afters[first] = [];
		}
		afters[first].push(next);

		if (!befores.hasOwnProperty(next)) {
			befores[next] = [];
		}
		befores[next].push(first);
	}
	var firsts = Object.keys(afters);
	var nexts = Object.keys(befores);

	var inits = [];
	for (let index = 0; index < firsts.length; index++) {
		if (nexts.indexOf(firsts[index]) == -1) {
			inits.push(firsts[index]);
		}
	}

	var output = "";
	var dones = [];
	var remains = nexts.concat(inits);
	var nexts = inits;
	while (remains.length != 0) {
		nexts = nexts.sort();
		var next = nexts.shift();
		output += next;

		dones.push(next);
		var index = remains.indexOf(next);
		remains.splice(index, 1);

		var followings = afters[next];
		if (followings) {
			for (let i = 0; i < followings.length; i++) {
				if (nexts.indexOf(followings[i]) == -1) {
					var priors = befores[followings[i]];
					var inject = true;
					for (let j = 0; j < priors.length; j++) {
						if (dones.indexOf(priors[j]) == -1) {
							inject = false;
						}
					}
					if (inject) {
						nexts.push(followings[i]);
					}
				}
			}
		}
	}

	return output;
}


function testAssemblyOrder(input, expected) {
	let t1 = assemblyOrder(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testAssemblyOrder([
	"Step C must be finished before step A can begin.",
	"Step C must be finished before step F can begin.",
	"Step A must be finished before step B can begin.",
	"Step A must be finished before step D can begin.",
	"Step B must be finished before step E can begin.",
	"Step D must be finished before step E can begin.",
	"Step F must be finished before step E can begin."
], "CABDFE");
console.log("");


////// Part 2

function assemblyTime(input, workers, stepDuration) {
	var afters = {};
	var befores = {};
	for (let index = 0; index < input.length; index++) {
		var first = input[index][5];
		var next = input[index][36];

		if (!afters.hasOwnProperty(first)) {
			afters[first] = [];
		}
		afters[first].push(next);

		if (!befores.hasOwnProperty(next)) {
			befores[next] = [];
		}
		befores[next].push(first);
	}
	var firsts = Object.keys(afters);
	var nexts = Object.keys(befores);

	var inits = [];
	for (let index = 0; index < firsts.length; index++) {
		if (nexts.indexOf(firsts[index]) == -1) {
			inits.push(firsts[index]);
		}
	}

	var remains = nexts.concat(inits).sort();
	var tasksDuration = {};
	for (let index = 0; index < remains.length; index++) {
		var alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		tasksDuration[remains[index]] = stepDuration + alphabet.indexOf(remains[index]) + 1;
	}

	var output = "";
	var dones = [];
	var inProgress = {};
	var nexts = inits;
	var time = 0;
	var availableWorkers = workers;
	while (remains.length != 0) {
		var tasks = Object.keys(inProgress);
		for (let index = 0; index < tasks.length; index++) {
			inProgress[tasks[index]]--;
			if (inProgress[tasks[index]] == 0) {
				delete inProgress[tasks[index]];
				availableWorkers++;
				
				dones.push(tasks[index]);
				var i = remains.indexOf(tasks[index]);
				remains.splice(i, 1);

				var followings = afters[tasks[index]];
				if (followings) {
					for (let i = 0; i < followings.length; i++) {
						if (nexts.indexOf(followings[i]) == -1) {
							var priors = befores[followings[i]];
							var inject = true;
							for (let j = 0; j < priors.length; j++) {
								if (dones.indexOf(priors[j]) == -1) {
									inject = false;
								}
							}
							if (inject) {
								nexts.push(followings[i]);
							}
						}
					}
				}
			}
		}

		nexts = nexts.sort();
		while (availableWorkers != 0 && nexts.length != 0) {
			var next = nexts.shift();
			inProgress[next] = tasksDuration[next];
			availableWorkers--;
		}
		
		if (Object.keys(inProgress) != 0) {
			time++;
		}
	}

	return time;
}


function testAssemblyTime(input, worker, stepDuration, expected) {
	let t2 = assemblyTime(input, worker, stepDuration);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests part 2:");
testAssemblyTime([
	"Step C must be finished before step A can begin.",
	"Step C must be finished before step F can begin.",
	"Step A must be finished before step B can begin.",
	"Step A must be finished before step D can begin.",
	"Step B must be finished before step E can begin.",
	"Step D must be finished before step E can begin.",
	"Step F must be finished before step E can begin."
], 2, 0, 15);
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
		console.log("Ordered steps: " + assemblyOrder(output));
		console.log("Assembly duration: " + assemblyTime(output, 6, 60));
	});
}

dayAnswer("./day07-input");