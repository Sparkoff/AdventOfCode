const ADDR = 'addr'
const ADDI = 'addi'
const MULR = 'mulr'
const MULI = 'muli'
const BANR = 'banr'
const BANI = 'bani'
const BORR = 'borr'
const BORI = 'bori'
const SETR = 'setr'
const SETI = 'seti'
const GTIR = 'gtir'
const GTRI = 'gtri'
const GTRR = 'gtrr'
const EQIR = 'eqir'
const EQRI = 'eqri'
const EQRR = 'eqrr'


class Interpreter {
	constructor(manual) {
		this.registers = new Array(4).fill(0)
		this.opcodes = new Map()
		this.opcodesGuessing = new Map()

		this.testInstructions = []
		this.instructions = []

		if (manual) {
			this.parseManual(manual)
		}
	}

	parseManual(manual) {
		while (manual[0]) {
			this.testInstructions.push({
				init: manual.shift().slice(9, -1).split(', ').map(i => parseInt(i)),
				cmd: manual.shift().split(' ').map(i => parseInt(i)),
				result: manual.shift().slice(9, -1).split(', ').map(i => parseInt(i)),
			})
			manual.shift()
		}
		manual.shift()
		manual.shift()
		while (manual.length != 0) {
			this.instructions.push(manual.shift().split(' ').map(i => parseInt(i)))
		}
	}

	exec(op, A, B, C, reg) {
		reg = reg || this.registers
		op = isNaN(op) ? op : this.opcodes.get(op)
		switch (op) {
			case ADDR:
				reg[C] = reg[A] + reg[B]
				break
			case ADDI:
				reg[C] = reg[A] + B
				break
			case MULR:
				reg[C] = reg[A] * reg[B]
				break
			case MULI:
				reg[C] = reg[A] * B
				break
			case BANR:
				reg[C] = reg[A] & reg[B]
				break
			case BANI:
				reg[C] = reg[A] & B
				break
			case BORR:
				reg[C] = reg[A] | reg[B]
				break
			case BORI:
				reg[C] = reg[A] | B
				break
			case SETR:
				reg[C] = reg[A]
				break
			case SETI:
				reg[C] = A
				break
			case GTIR:
				reg[C] = A > reg[B] ? 1 : 0
				break
			case GTRI:
				reg[C] = reg[A] > B ? 1 : 0
				break
			case GTRR:
				reg[C] = reg[A] > reg[B] ? 1 : 0
				break
			case EQIR:
				reg[C] = A == reg[B] ? 1 : 0
				break
			case EQRI:
				reg[C] = reg[A] == B ? 1 : 0
				break
			case EQRR:
				reg[C] = reg[A] == reg[B] ? 1 : 0
				break
			default:
				console.log(`Unknown opcode: ${op}`)
				break
		}
		return reg
	}

	prepareGuess() {
		let similarCount = 0

		this.testInstructions.forEach(test => {
			let opcode = test.cmd[0]
			let count = 0

			if (!this.opcodesGuessing.has(opcode)) {
				this.opcodesGuessing.set(opcode, new Set())
				this.opcodes.set(opcode, '')
			}

			function testExec(op) {
				if (this.exec(op, test.cmd[1], test.cmd[2], test.cmd[3], test.init.slice(0)).join('.') == test.result.join('.')) {
					this.opcodesGuessing.get(opcode).add(op)
					count++
				}
			}

			if (test.cmd[1] <= 3) {
				[ADDI, MULI, BANI, BORI, SETR, GTRI, EQRI].forEach(testExec.bind(this))
			}
			if (test.cmd[2] <= 3) {
				[GTIR, EQIR].forEach(testExec.bind(this))
			}
			if (test.cmd[1] <= 3 && test.cmd[2] <= 3) {
				[ADDR, MULR, BANR, BORR, GTRR, EQRR].forEach(testExec.bind(this))
			}
			[SETI].forEach(testExec.bind(this))

			if (count >= 3) {
				similarCount++
			}
		})


		return similarCount
	}

	guess() {
		let opLabels = new Set([ADDR, ADDI, MULR, MULI, BANR, BANI, BORR, BORI, SETR, SETI, GTIR, GTRI, GTRR, EQIR, EQRI, EQRR])

		while (opLabels.size != 0) {
			for (const op of [...this.opcodesGuessing.keys()]) {
				if (this.opcodesGuessing.get(op).size == 1) {
					let opLabel = this.opcodesGuessing.get(op).values().next().value
					opLabels.delete(opLabel)
					this.opcodesGuessing.delete(op)

					this.opcodes.set(op, opLabel)

					this.opcodesGuessing.forEach(guessList => {
						guessList.delete(opLabel)
					})
					break
				}
			}

		}
	}

	run() {
		for (const cmd of this.instructions) {
			this.exec.apply(this, cmd)
		}
	}

	get part1() {
		return this.prepareGuess()
	}

	get part2() {
		if (this.opcodesGuessing.size == 0) {
			this.prepareGuess()
		}

		this.guess()
		this.run()

		return this.registers[0]
	}
}

module.exports = {
	answer: function (input) {
		return new Interpreter(input)
	},

	part1: 646,
	part2: 681
}
