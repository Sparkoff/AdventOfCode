class Recipe {
	constructor(limit) {
		this.limit = limit

		this.tenRecipes = ''
		this.priorRecipes = 0

		this.run()
	}

	run() {
		let first = this.makeLinkedNode(3)
		let elf1 = first
		let elf2 = this.makeLinkedNode(7, first)

		let scoreCount = 2

		let recipeCount = parseInt(this.limit)
		let sequenceFound = false
		let seq = ''

		while (scoreCount < recipeCount + 10 || !sequenceFound) {
			let score = [elf1.value + elf2.value]
			if (score[0] >= 10) {
				score = [1, score[0] - 10]
			}

			score.forEach(s => {
				this.makeLinkedNode(s, first.previous)

				scoreCount++

				if (scoreCount >= recipeCount + 1 && scoreCount <= recipeCount + 10) {
					this.tenRecipes += s
				}

				if (!sequenceFound) {
					if (seq.length == this.limit.length) {
						seq = seq.slice(1) + s
						if (seq == this.limit) {
							sequenceFound = true
							this.priorRecipes = scoreCount - this.limit.length
						}
					} else {
						seq += s
					}
				}
			})

			let score1 = elf1.value
			for (let i = 0; i < score1 + 1; i++) {
				elf1 = elf1.next
			}
			let score2 = elf2.value
			for (let i = 0; i < score2 + 1; i++) {
				elf2 = elf2.next
			}
		}
	}

	makeLinkedNode(value, previous) {
		let linkedNode = {
			previous: null,
			next: null,
			value: value || 0
		}

		linkedNode.previous = previous ? previous : linkedNode
		linkedNode.next = previous ? previous.next : linkedNode

		if (previous) {
			previous.next.previous = linkedNode
			previous.next = linkedNode
		}

		return linkedNode
	}

	get part1() {
		return this.tenRecipes
	}

	get part2() {
		return this.priorRecipes
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new Recipe(input)
	},

	part1: '1631191756',
	part2: 20219475
}
