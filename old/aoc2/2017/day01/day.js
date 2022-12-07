class Captcha {
	constructor(digits) {
		this.digits = digits.split("").map(d => parseInt(d))
		this.length = this.digits.length
	}

	sum(isHalfway) {
		let sequence = this.digits.slice(0)
		if (isHalfway) {
			sequence = sequence.concat(sequence.slice(0, this.length / 2))
		} else {
			sequence.push(sequence[0])
		}

		let sum = 0
		for (let i = 0; i < this.length; i++) {
			if (
				(isHalfway && sequence[i] == sequence[i + this.length / 2]) ||
				(!isHalfway && sequence[i] == sequence[i + 1])
			) {
				sum += sequence[i]
			}
		}
		return sum
	}

	get part1() {
		return this.sum()
	}

	get part2() {
		return this.sum('halfway')
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new Captcha(input)
	},

	part1: 1034,
	part2: 1356
}