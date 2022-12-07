class Duel {
	constructor(start, limit) {
		this.A = parseInt(start[0].substring(24))
		this.B = parseInt(start[1].substring(24))
		this.limit = limit
	}

	judge(criteria) {
		let limit = this.limit || (criteria ? 5E6 : 4E7)

		let A = this.A
		let B = this.B
		let score = 0

		for (let i = 0; i < limit; i++) {
			do {
				A = (A * 16807) % 2147483647
			} while (criteria && A % 4 != 0)

			do {
				B = (B * 48271) % 2147483647
			} while (criteria && B % 8 != 0)

			if ((A & 0xFFFF) == (B & 0xFFFF)) {
				score++
			}
		}
		return score
	}

	get part1() {
		return this.judge()
	}

	get part2() {
		return this.judge('criteria')
	}
}

module.exports = {
	answer: function (input, limit) {
		return new Duel(input, limit)
	},

	part1: 626,
	part2: 306
}