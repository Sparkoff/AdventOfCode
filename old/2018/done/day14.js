console.log("** DAY 14 **");
console.log("");


////// Part 1

function getLastTen(recipesCount) {
	var firstScore = {
		next: null,
		prev: null,
		value: 3,
		rank: 1
	};
	var secondScore = {
		next: firstScore,
		prev: firstScore,
		value: 7,
		rank: 2
	};
	firstScore.next = secondScore;
	firstScore.prev = secondScore;

	firstElf = firstScore;
	secondElf = secondScore;

	while (firstScore.prev.rank < recipesCount + 10) {
		var currentRecipeScore = firstElf.value + secondElf.value;
		
		if (currentRecipeScore < 10) {
			var newRecipe = {
				next: firstScore,
				prev: firstScore.prev,
				value: currentRecipeScore,
				rank: firstScore.prev.rank + 1
			};
			firstScore.prev.next = newRecipe;
			firstScore.prev = newRecipe;
		} else {
			var newRecipeDozen = {
				next: null,
				prev: firstScore.prev,
				value: 1,
				rank: firstScore.prev.rank + 1
			};
			var newRecipeUnit = {
				next: firstScore,
				prev: newRecipeDozen,
				value: currentRecipeScore - 10,
				rank: newRecipeDozen.rank + 1
			};
			newRecipeDozen.next = newRecipeUnit;

			firstScore.prev.next = newRecipeDozen;
			firstScore.prev = newRecipeUnit;
		}

		var firstElfMove = firstElf.value + 1;
		var secondElfMove = secondElf.value + 1;
		for (let i = 0; i < firstElfMove; i++) {
			firstElf = firstElf.next;
		}
		for (let i = 0; i < secondElfMove; i++) {
			secondElf = secondElf.next;
		}
	}

	var currentRecipe = firstScore.prev;
	var lastTen = "";
	while (currentRecipe.rank != recipesCount) {
		if (currentRecipe.rank <= recipesCount + 10) {
			lastTen = currentRecipe.value.toString() + lastTen;
		}
		currentRecipe = currentRecipe.prev;
	}

	return lastTen;
}


function testLastTen(recipesCount, expected) {
	let t1 = getLastTen(recipesCount);
	if (t1 == expected) {
		console.log("- " + t1 + " : ok");
	} else {
		console.error("- result was " + t1 + ", but expected " + expected);
	}
}


console.log("Unit tests part 1:");
testLastTen(9, "5158916779");
testLastTen(5, "0124515891");
testLastTen(18, "9251071085");
testLastTen(2018, "5941429882");
console.log("");


////// Part 2 

function countBeforeSequence(recipesSequence) {
	var firstScore = {
		next: null,
		prev: null,
		value: 3,
		rank: 1
	};
	var secondScore = {
		next: firstScore,
		prev: firstScore,
		value: 7,
		rank: 2
	};
	firstScore.next = secondScore;
	firstScore.prev = secondScore;

	firstElf = firstScore;
	secondElf = secondScore;

	var sequenceFound = false;
	while (!sequenceFound) {
		var currentRecipeScore = firstElf.value + secondElf.value;

		if (currentRecipeScore < 10) {
			var newRecipe = {
				next: firstScore,
				prev: firstScore.prev,
				value: currentRecipeScore,
				rank: firstScore.prev.rank + 1
			};
			firstScore.prev.next = newRecipe;
			firstScore.prev = newRecipe;
		} else {
			var newRecipeDozen = {
				next: null,
				prev: firstScore.prev,
				value: 1,
				rank: firstScore.prev.rank + 1
			};
			var newRecipeUnit = {
				next: firstScore,
				prev: newRecipeDozen,
				value: currentRecipeScore - 10,
				rank: newRecipeDozen.rank + 1
			};
			newRecipeDozen.next = newRecipeUnit;

			firstScore.prev.next = newRecipeDozen;
			firstScore.prev = newRecipeUnit;
		}

		var firstElfMove = firstElf.value + 1;
		var secondElfMove = secondElf.value + 1;
		for (let i = 0; i < firstElfMove; i++) {
			firstElf = firstElf.next;
		}
		for (let i = 0; i < secondElfMove; i++) {
			secondElf = secondElf.next;
		}

		var seq = "";
		var currentRecipe = firstScore.prev;
		for (let i = 0; i < recipesSequence.length; i++) {
			seq = currentRecipe.value.toString() + seq;
			currentRecipe = currentRecipe.prev;
		}
		sequenceFound = (seq == recipesSequence);
		
		if (!sequenceFound) {
			seq = "";
			currentRecipe = firstScore.prev.prev;
			for (let i = 0; i < recipesSequence.length; i++) {
				seq = currentRecipe.value.toString() + seq;
				currentRecipe = currentRecipe.prev;
			}
			sequenceFound = (seq == recipesSequence);
		}
	}

	return currentRecipe.rank;
}


function testCountBeforeSequence(sequence, expected) {
	let t2 = countBeforeSequence(sequence);
	if (t2 == expected) {
		console.log("- " + t2 + " : ok");
	} else {
		console.error("- result was " + t2 + ", but expected " + expected);
	}
}


console.log("Unit tests part 2:");
testCountBeforeSequence("51589", 9);
testCountBeforeSequence("01245", 5);
testCountBeforeSequence("92510", 18);
testCountBeforeSequence("59414", 2018);
console.log("");


////// Day Answer

function dayAnswer() {
	console.log("Ten recipes scores after first 409551: " + getLastTen(409551));
	console.log("Recipes count before sequence 409551: " + countBeforeSequence("409551"));
}

dayAnswer();