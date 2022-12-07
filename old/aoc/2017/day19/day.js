class Tubes {
	constructor(map) {
		this.map = map

		this.journey = this.run()
	}

	run() {
		let word = ''
		let direction = 'down'
		let pt = [0, this.map[0].indexOf('|')]
		let steps = 1

		while (true) {
			let current = this.map[pt[0]][pt[1]]
			if (current == '+') {
				if (direction != 'down' && this.map[pt[0] - 1][pt[1]] != ' ') {
					direction = 'up'
				} else if (direction != 'up' && this.map[pt[0] + 1][pt[1]] != ' ') {
					direction = 'down'
				} else if (direction != 'right' && this.map[pt[0]][pt[1] - 1] != ' ') {
					direction = 'left'
				} else if (direction != 'left' && this.map[pt[0]][pt[1] + 1] != ' ') {
					direction = 'right'
				} else {
					break
				}
			} else if (current == ' ') {
				steps--
				break
			} else if (current.match(/[A-Z]/)) {
				word += current
			}

			switch (direction) {
				case 'up':
					pt[0]--
					break;
				case 'down':
					pt[0]++
					break;
				case 'left':
					pt[1]--
					break;
				case 'right':
					pt[1]++
					break;
			}
			steps++
		}

		return {
			word: word,
			steps: steps
		}
	}

	get part1() {
		return this.journey.word
	}

	get part2() {
		return this.journey.steps
	}
}

module.exports = {
	answer: function (input) {
		return new Tubes(input)
	},

	part1: 'MKXOIHZNBL',
	part2: 17872
}