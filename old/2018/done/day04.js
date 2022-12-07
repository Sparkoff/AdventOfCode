console.log("** DAY 04 **");
console.log("");


////// Part 1

function parseLogs(input) {
	input = input.sort();

	let guardShift = {};

	let currentGuard = 0;
	let sleep = -1;

	for (let index = 0; index < input.length; index++) {
		let log = input[index].split("] ");
		let msg = log[1];
		let date = log[0].substring(1).split(" ");

		if (msg.indexOf("Guard #") == 0) {
			let id = msg.match(/#(\d+)/)[1];
			currentGuard = id;

			if (!guardShift.hasOwnProperty(id)) {
				guardShift[id] = {};
				for (let min = 0; min < 60; min++) {
					guardShift[id][min] = 0;
				}
				guardShift[id].sum = 0;
			}
		} else {
			if (currentGuard == 0) {
				console.log("!!! guard not initiated");
			}

			if (msg == "falls asleep") {
				sleep = Number(date[1].split(":")[1]);
			} else {
				if (sleep == -1) {
					console.log("!!! sleep not initiated");
				}

				let end = Number(date[1].split(":")[1]);
				for (let t = sleep; t < end; t++) {
					guardShift[currentGuard][t]++;
					guardShift[currentGuard].sum++;
				}
				sleep = -1;
			}
		}
	}

	return guardShift;
}

function firstStrategy(input) {
	let guardShift = parseLogs(input);

	let guards = Object.keys(guardShift);
	currentGuard = 0;
	sleep = 0;
	for (let index = 0; index < guards.length; index++) {
		if (guardShift[guards[index]].sum > sleep) {
			sleep = guardShift[guards[index]].sum;
			currentGuard = guards[index];
		}
	}

	sleep = 0;
	let time = 0;
	for (let min = 0; min < 60; min++) {
		if (guardShift[currentGuard][min] > sleep) {
			sleep = guardShift[currentGuard][min];
			time = min;
		}
	}

	return currentGuard*time;
}


function testFirstStrategy(input, expected) {
	let t1 = firstStrategy(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testFirstStrategy([
	"[1518-11-01 00:00] Guard #10 begins shift",
	"[1518-11-01 00:05] falls asleep",
	"[1518-11-01 00:25] wakes up",
	"[1518-11-01 00:30] falls asleep",
	"[1518-11-01 00:55] wakes up",
	"[1518-11-01 23:58] Guard #99 begins shift",
	"[1518-11-02 00:40] falls asleep",
	"[1518-11-02 00:50] wakes up",
	"[1518-11-03 00:05] Guard #10 begins shift",
	"[1518-11-03 00:24] falls asleep",
	"[1518-11-03 00:29] wakes up",
	"[1518-11-04 00:02] Guard #99 begins shift",
	"[1518-11-04 00:36] falls asleep",
	"[1518-11-04 00:46] wakes up",
	"[1518-11-05 00:03] Guard #99 begins shift",
	"[1518-11-05 00:45] falls asleep",
	"[1518-11-05 00:55] wakes up"
], 240);
testFirstStrategy([
	"[1518-11-04 00:36] falls asleep",
	"[1518-11-04 00:02] Guard #99 begins shift",
	"[1518-11-04 00:46] wakes up",
	"[1518-11-03 00:24] falls asleep",
	"[1518-11-03 00:29] wakes up",
	"[1518-11-05 00:45] falls asleep",
	"[1518-11-05 00:55] wakes up",
	"[1518-11-03 00:05] Guard #10 begins shift",
	"[1518-11-01 00:30] falls asleep",
	"[1518-11-01 23:58] Guard #99 begins shift",
	"[1518-11-01 00:05] falls asleep",
	"[1518-11-01 00:00] Guard #10 begins shift",
	"[1518-11-05 00:03] Guard #99 begins shift",
	"[1518-11-02 00:50] wakes up",
	"[1518-11-01 00:25] wakes up",
	"[1518-11-02 00:40] falls asleep",
	"[1518-11-01 00:55] wakes up"
], 240);
console.log("");


////// Part 2

function secondStrategy(input) {
	let guardShift = parseLogs(input);

	let guard = 0;
	let time = 0;
	let count = 0;

	let guards = Object.keys(guardShift);
	for (let index = 0; index < guards.length; index++) {
		for (let min = 0; min < 60; min++) {
			if (guardShift[guards[index]][min] > count) {
				guard = guards[index];
				time = min;
				count = guardShift[guards[index]][min];
			}
		}
	}

	return guard * time;
}


function testSecondStrategy(input, expected) {
	let t2 = secondStrategy(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}

console.log("Unit tests part 2:");
testSecondStrategy([
	"[1518-11-01 00:00] Guard #10 begins shift",
	"[1518-11-01 00:05] falls asleep",
	"[1518-11-01 00:25] wakes up",
	"[1518-11-01 00:30] falls asleep",
	"[1518-11-01 00:55] wakes up",
	"[1518-11-01 23:58] Guard #99 begins shift",
	"[1518-11-02 00:40] falls asleep",
	"[1518-11-02 00:50] wakes up",
	"[1518-11-03 00:05] Guard #10 begins shift",
	"[1518-11-03 00:24] falls asleep",
	"[1518-11-03 00:29] wakes up",
	"[1518-11-04 00:02] Guard #99 begins shift",
	"[1518-11-04 00:36] falls asleep",
	"[1518-11-04 00:46] wakes up",
	"[1518-11-05 00:03] Guard #99 begins shift",
	"[1518-11-05 00:45] falls asleep",
	"[1518-11-05 00:55] wakes up"
], 4455);
testSecondStrategy([
	"[1518-11-04 00:36] falls asleep",
	"[1518-11-04 00:02] Guard #99 begins shift",
	"[1518-11-04 00:46] wakes up",
	"[1518-11-03 00:24] falls asleep",
	"[1518-11-03 00:29] wakes up",
	"[1518-11-05 00:45] falls asleep",
	"[1518-11-05 00:55] wakes up",
	"[1518-11-03 00:05] Guard #10 begins shift",
	"[1518-11-01 00:30] falls asleep",
	"[1518-11-01 23:58] Guard #99 begins shift",
	"[1518-11-01 00:05] falls asleep",
	"[1518-11-01 00:00] Guard #10 begins shift",
	"[1518-11-05 00:03] Guard #99 begins shift",
	"[1518-11-02 00:50] wakes up",
	"[1518-11-01 00:25] wakes up",
	"[1518-11-02 00:40] falls asleep",
	"[1518-11-01 00:55] wakes up"
], 4455);
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
		console.log("First strategy: " + firstStrategy(output));
		console.log("Second strategy: " + secondStrategy(output));
	});
}

dayAnswer("./day04-input");