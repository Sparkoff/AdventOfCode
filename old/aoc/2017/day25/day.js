class TuringMachine {
	constructor (blueprint) {
		this.blueprint = this.parseBlueprint(blueprint)
		this.checksum = 0

		this.run()
	}

	parseBlueprint (blueprint) {
		let start = blueprint.shift().match(/state ([A-Z]+)\./)[1]
		let steps = parseInt(blueprint.shift().match(/after (\d+) steps\./)[1])
		let rules = {}

		while (blueprint.length != 0) {
			blueprint.shift()
			
			let state = blueprint.shift().match(/state ([A-Z]+):/)[1]
			rules[state] = []

			for (let i = 0; i <= 1; i++) {
				blueprint.shift()
				rules[state].push({
					value: parseInt(blueprint.shift().match(/value (\d)./)[1]),
					position: blueprint.shift().indexOf('right') != -1 ? 1 : -1,
					state: blueprint.shift().match(/state ([A-Z]+)\./)[1]
				})
			}
		}

		return {
			start: start,
			steps: steps,
			rules: rules
		}
	}

	run () {
		let state = this.blueprint.start
		let tape = {
			0: 0
		}
		let cursor = 0

		for (let i = 0; i < this.blueprint.steps; i++) {
			let next = this.blueprint.rules[state][tape[cursor]]

			tape[cursor] = next.value
			cursor += next.position
			state = next.state

			if (!tape.hasOwnProperty(cursor)) {
				tape[cursor] = 0
			}
		}

		this.checksum =  Object.values(tape).reduce((a, b) => a + b)
	}
}

const WIIIIIIIIIN = '50th star unlocked !!!!'

module.exports = {
	answer: function (input) {
		return {
			part1: (new TuringMachine(input)).checksum,
			part2: WIIIIIIIIIN
		}
	},

	part1: 5744,
	part2: WIIIIIIIIIN
}