console.log("** DAY 24 **");
console.log("");


////// Part 1

function parseUnits(input, type) {
	var units = [];

	for (let i = 0; i < input.length; i++) {
		var unit = {
			id: i + 1,
			type: type,
			units: Number(input[i].match(/(\d+) unit/)[1]),
			hit: Number(input[i].match(/(\d+) hit point/)[1]),
			attack: Number(input[i].match(/(\d+) [a-z]+ damage/)[1]),
			attackType: input[i].match(/\d+ ([a-z]+) damage/)[1],
			initiative: Number(input[i].match(/initiative (\d+)/)[1]),
			weak: [],
			immune: [],
			getEffectivePower: function () {
				return this.units * this.attack;
			},
			target: -1,
			isTargeted: false,
			resetTarget: function () {
				this.target = -1;
				this.isTargeted = false;
			}
		};

		var m = input[i].match(/\((.*)\)/);
		if (m) {
			m = m[1].split("; ");
			for (let j = 0; j < m.length; j++) {
				if (m[j].indexOf("weak") != -1) {
					unit.weak = m[j].substr(8).split(", ");
				} else {
					unit.immune = m[j].substr(10).split(", ");
				}
			}
		}

		units.push(unit);
	}

	return units;
}

function targetingSort(A, B) {
	if (A.getEffectivePower() != B.getEffectivePower()) {
		return B.getEffectivePower() - A.getEffectivePower();
	} else {
		return B.initiative - A.initiative;
	}
}

function attackingSort(A, B) {
	return B.initiative - A.initiative;
}

function evaluateDammage(attackingGroup, defendingGroup) {
	var damage = attackingGroup.getEffectivePower();
	if (defendingGroup.weak.indexOf(attackingGroup.attackType) != -1) {
		damage *= 2;
	} else if (defendingGroup.immune.indexOf(attackingGroup.attackType) != -1) {
		damage = 0;
	}
	return damage;
}

function selectTarget(attackingGroup, defenders) {
	var damage = 0;
	var targets = [];
	for (let i = 0; i < defenders.length; i++) {
		if (!defenders[i].isTargeted) {
			var d = evaluateDammage(attackingGroup, defenders[i]);
			console.log((attackingGroup.type == "immune" ? "Immune System" : "Infection") + " group " + attackingGroup.id + " would deal defending group " + defenders[i].id + " " + d + " damage");
			if (d > damage) {
				damage = d;
				targets = [];
				targets.push(i);
			} else if (d == damage) {
				targets.push(i);
			}
		}
	}

	if (targets.length == 1) {
		defenders[targets[0]].isTargeted = true;
		return targets[0];
	} else if (targets.length > 1) {
		var id = -1;
		var effectivePower = 0;
		var initiative = 0;
		for (let i = 0; i < targets.length; i++) {
			if (defenders[targets[i]].getEffectivePower() > effectivePower) {
				id = targets[i];
				effectivePower = defenders[targets[i]].getEffectivePower();
				initiative = defenders[targets[i]].initiative;
			} else if (defenders[targets[i]].getEffectivePower() == effectivePower) {
				if (defenders[targets[i]].initiative > initiative) {
					id = targets[i];
					initiative = defenders[targets[i]].initiative;
				}
			}
		}
		defenders[id].isTargeted = true;
		return id;
	} else {
		return -1;
	}
}

function printGroups(immunes, infections) {
	console.log("Immune System:");
	for (let i = 0; i < immunes.length; i++) {
		console.log("Group " + immunes[i].id + " contains " + immunes[i].units + " units with effective power " + immunes[i].getEffectivePower() + " and initiative " + immunes[i].initiative);
	}
	console.log("Infection:");
	for (let i = 0; i < infections.length; i++) {
		console.log("Group " + infections[i].id + " contains " + infections[i].units + " units with effective power " + infections[i].getEffectivePower() + " and initiative " + infections[i].initiative);
	}
}

function fight(input) {
	var immunes = parseUnits(input.immuneSystem, "immune");
	var infections = parseUnits(input.infection, "infection");
	//printGroups(immunes, infections);
	console.log(immunes);
	console.log(infections);

	while (immunes.length != 0 && infections.length != 0) {
		console.log("-----------------");
		printGroups(immunes, infections);

		var attacking = immunes.concat(infections);
		attacking.sort(targetingSort);
		for (let i = 0; i < attacking.length; i++) {
			attacking[i].resetTarget();
		}

		console.log("");
		for (let i = 0; i < attacking.length; i++) {
			var target = -1;
			if (attacking[i].type == "immune") {
				target = selectTarget(attacking[i], infections);
			} else {
				target = selectTarget(attacking[i], immunes);
			}
			if (target != -1) {
				attacking[i].target = target;
			}
		}

		console.log("");
		attacking.sort(attackingSort);
		for (let i = 0; i < attacking.length; i++) {
			if (attacking[i].target != -1 && attacking[i].units != 0) {
				if (attacking[i].type == "immune") {
					var damage = evaluateDammage(attacking[i], infections[attacking[i].target]);
					var killed = Math.floor(damage / infections[attacking[i].target].hit);
					if (infections[attacking[i].target].units < killed) {
						killed = infections[attacking[i].target].units;
					}
					console.log("Immune System group " + attacking[i].id + " attacks defending group " + infections[attacking[i].target].id + ", killing " + killed + " units");
					infections[attacking[i].target].units -= killed;
				} else {
					var damage = evaluateDammage(attacking[i], immunes[attacking[i].target]);
					var killed = Math.floor(damage / immunes[attacking[i].target].hit);
					if (immunes[attacking[i].target].units < killed) {
						killed = immunes[attacking[i].target].units;
					}
					console.log("Infection group " + attacking[i].id + " attacks defending group " + immunes[attacking[i].target].id + ", killing " + killed + " units");
					immunes[attacking[i].target].units -= killed;
				}
			}
		}

		for (let i = 0; i < immunes.length; i++) {
			if (immunes[i].units == 0) {
				immunes.splice(i, 1);
				i--;
			}
		}
		for (let i = 0; i < infections.length; i++) {
			if (infections[i].units == 0) {
				infections.splice(i, 1);
				i--;
			}
		}
	}

	var survivors = 0;
	for (let i = 0; i < immunes.length; i++) {
		survivors += immunes[i].units;
	}
	for (let i = 0; i < infections.length; i++) {
		survivors += infections[i].units;
	}
	return survivors
}


function testFight(input, expected) {
	let t1 = fight(input);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


// console.log("Unit tests part 1:");
// testFight({
// 	immuneSystem: [
// 		"17 units each with 5390 hit points(weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2",
// 		"989 units each with 1274 hit points(immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3"
// 	],
// 	infection: [
// 		"801 units each with 4706 hit points(weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1",
// 		"4485 units each with 2961 hit points(immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4"
// 	]
// }, 5216);
// console.log("");


////// Part 2

/*function getTeleportDistance2(input) {
	
}


function testGetTeleportDistance2(input, expected) {
	let t2 = getTeleportDistance2(input);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testGetTeleportDistance2([
	"pos=<-1,-1,-1>, r=2",
	"pos=<10,12,12>, r=2",
	"pos=<12,14,12>, r=2",
	"pos=<16,12,12>, r=4",
	"pos=<14,14,14>, r=6",
	"pos=<50,50,50>, r=200",
	"pos=<10,10,10>, r=5"
], 36);
console.log("");*/


////// Day Answer

function dayAnswer(file) {
	let output = {
		immuneSystem: [],
		infection: []
	};
	let current = "";

	const readline = require('readline');
	const fs = require('fs');

	const rl = readline.createInterface({
		input: fs.createReadStream(file)
	});

	rl.on('line', (input) => {
		if (input == "Immune System:") {
			current = "immuneSystem";
		} else if (input == "Infection:") {
			current = "infection";
		} else if (input != "") {
			output[current].push(input);
		}
	});

	rl.on('close', () => {
		console.log("Remaining units count: " + fight(output));
		//console.log("Last cart survivor: " + getTeleportDistance2(output));
	});
}

dayAnswer("./day24-input");