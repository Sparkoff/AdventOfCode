class GameOfLife {
	constructor(rules) {
		this.initialState = ''
		this.rules = {}

		this.patterns = []

		this.init(rules)
		this.run()
	}

	init(rules) {
		this.initialState = rules.shift().substring(15)
		rules.shift()

		rules.forEach(r => {
			r = r.split(/\s=>\s/)
			this.rules[r[0]] = r[1]
		})
	}

	nextGeneration(current) {
		let index = current.index - 3
		current = `.....${current.state}.....`
		let next = ''

		for (let i = 0; i < current.length - 4; i++) {
			const pattern = current.substring(i, i + 5)
			if (this.rules.hasOwnProperty(pattern)) {
				next += this.rules[pattern]
			} else {
				next += '.'
			}
		}

		// trim starting and ending '.'
		for (let i = 0; i < next.length; i++) {
			if (next[i] == '.') {
				next = next.slice(1)
				i--
				index++
			} else {
				break
			}
		}
		for (let i = next.length - 1; i >= 0; i--) {
			if (next[i] == '.') {
				next = next.slice(0, -1)
			} else {
				break
			}
		}

		return {
			state: next,
			index
		}
	}

	run() {
		let current = {
			state: this.initialState,
			index: 0
		}
		this.patterns.push(current)

		let i = 1
		while (true) {
			current = this.nextGeneration(current)
			this.patterns.push(current)

			// search for pattern, like
			// ..#..#.##
			// ...#..#.##
			// ....#..#.##
			// .....#..#.##
			// ......#..#.##
			if (this.patterns[i].state == this.patterns[i - 1].state) {
				break
			}
			i++
		}
	}
	
	sum(generation) {
		const length = this.patterns.length

		let state = ''
		let index = 0

		if (generation > length) {
			state = this.patterns[length - 1].state
			index = generation - (length - this.patterns[length - 1].index - 1 )
		} else {
			state = this.patterns[generation].state
			index = this.patterns[generation].index
		}

		return state.split('').reduce((a, c, i) => {
			if (c == '#') {
				a += i + index
			}
			return a
		}, 0)
	}

	get part1() {
		return this.sum(20)
	}

	get part2() {
		return this.sum(5E10)
	}
}

module.exports = {
	answer: function (input) {
		return new GameOfLife(input)
	},

	part1: 2444,
	part2: 750000000697
}