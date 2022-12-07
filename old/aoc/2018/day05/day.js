class Alchemy {
	constructor(polymer) {
		this.polymer = polymer
	}

	react(polymer) {
		let reduced = []

		for (const letter of polymer.split('')) {
			if (
				reduced.length == 0 ||
				(reduced[reduced.length - 1].charCodeAt() ^ letter.charCodeAt()) !== 32
			) {
				reduced.push(letter)
			} else {
				reduced.pop()
			}
		}
		return reduced.join('')
	}

	polymentImprovement(polymer) {
		let known = []
		let improvements = []

		for (let i = 0; i < polymer.length - 1; i++) {
			let character = polymer[i].toLowerCase()

			if (known.indexOf(character) == -1) {
				known.push(character)

				const pattern = `[${character}${character.toUpperCase()}]`
				const regex = new RegExp(pattern, 'g')
				improvements.push(this.react(polymer.replace(regex, '')).length)
			}
		}

		return Math.min(...improvements)
	}

	get part1() {
		return this.react(this.polymer).length
	}

	get part2() {
		return this.polymentImprovement(this.polymer)
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new Alchemy(input)
	},

	part1: 10766,
	part2: 6538
}