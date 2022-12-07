class Dancing {
	constructor(sequence, dancers) {
		this.sequence = this.parseSequence(sequence)
		this.dancers = dancers || 'abcdefghijklmnop'

		this.lines = []

		this.run()
	}

	parseSequence(sequence) {
		return sequence.split(',').map(seq => {
			seq = seq.match(/(s|x|p)(\w+)\/?(\w+)?/)
			switch (seq[1]) {
				case 's':
					return ['s', parseInt(seq[2])]
				case 'x':
					return ['x', parseInt(seq[2]), parseInt(seq[3])]
				case 'p':
					return ['p', seq[2], seq[3]]
			}
		})
	}

	dance(dancers) {
		for (const step of this.sequence) {
			switch (step[0]) {
				case 's':
					let g1 = dancers.splice(-step[1])
					dancers = g1.concat(dancers)
					break;
				case 'x':
					dancers[step[1]] = dancers.splice(step[2], 1, dancers[step[1]])[0]
					break;
				case 'p':
					const d1 = dancers.indexOf(step[1])
					const d2 = dancers.indexOf(step[2])
					dancers[d1] = dancers.splice(d2, 1, dancers[d1])[0]
					break;

				default:
					console.log(`dance : unknwon move ${step[0]}`)
					break;
			}
		}

		return dancers
	}

	run() {
		let dancers = this.dancers.split('')
		this.lines.push(this.dancers)

		do {
			dancers = this.dance(dancers)
			this.lines.push(dancers.join(''))
		} while (dancers.join('') != this.lines[0])
	}

	get part1() {
		return this.lines[1]
	}

	get part2() {
		return this.lines[1E9 % (this.lines.length - 1)]
	}
}

module.exports = {
	answer: function (input, dancers) {
		input = (Array.isArray(input)) ? input[0] : input
		return new Dancing(input, dancers)
	},

	part1: 'pkgnhomelfdibjac',
	part2: 'pogbjfihclkemadn'
}