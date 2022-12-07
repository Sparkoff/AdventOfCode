class ProgramAlarm {
	constructor(program, debug) {
		this.program = program
		this.debug = !!debug
	}

	run(noun, verb) {
		let program = this.program.split(',').map(f => parseInt(f))
		let playHead = 0

		if (noun !== undefined && verb != undefined) {
			program[1] = noun
			program[2] = verb
		}

		while (true) {
			const opcode = program[playHead]
			const in1 = program[playHead + 1]
			const in2 = program[playHead + 2]
			const out = program[playHead + 3]

			switch (opcode) {
				case 1:
					program[out] = program[in1] + program[in2]
					break
		
				case 2:
					program[out] = program[in1] * program[in2]
					break

				case 99:
					return program[0]
			
				default:
					throw `Bad opcode ${opcode}`
			}

			playHead += 4
		}
	}

	get part1() {
		if (this.debug) {
			return this.run()
		} else {
			return this.run(12, 2)
		}
	}

	get part2() {
		for (let noun = 0; noun <= 99; noun++) {
			for (let verb = 0; verb <= 99; verb++) {
				if (this.run(noun, verb) == 19690720) {
					return 100 * noun + verb
				}
			}
		}
	}
}

module.exports = {
	answer: function (input, debug) {
		input = (Array.isArray(input)) ? input[0] : input
		return new ProgramAlarm(input, debug)
	},

	part1: 4023471,
	part2: 8051
}