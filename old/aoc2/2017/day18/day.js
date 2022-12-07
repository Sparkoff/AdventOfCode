class Duet {
	constructor(instructions) {
		this.registers = {}
		this.instructions = this.parseInstructions(instructions)

		this.programs = {}
	}

	parseInstructions(instructions) {
		return instructions.map(i => {
			i = i.split(/\s/)

			const parse = x => {
				if (x.match(/[a-z]/)) {
					if (!this.registers.hasOwnProperty(x)) {
						this.registers[x] = 0
					}
					return {
						type: 'register',
						value: x
					}
				} else {
					return {
						type: 'integer',
						value: parseInt(x)
					}
				}
			}

			i[1] = parse(i[1])
			if (i[2]) {
				i[2] = parse(i[2])
			}

			return {
				cmd: i[0],
				X: i[1],
				Y: i[2]
			}
		})
	}

	cmd_snd() { }
	cmd_sound(program, X) {
		program.queue.push((X.type == 'register') ? program.registers[X.value] : X.value)
		program.current++
	}
	cmd_send(program, X) {
		this.programs[1 - program.id].queue.push((X.type == 'register') ? program.registers[X.value] : X.value)
		program.send++
		program.current++
	}
	cmd_set(program, X, Y) {
		program.registers[X.value] = (Y.type == 'register') ? program.registers[Y.value] : Y.value
		program.current++
	}
	cmd_add(program, X, Y) {
		program.registers[X.value] += (Y.type == 'register') ? program.registers[Y.value] : Y.value
		program.current++
	}
	cmd_mul(program, X, Y) {
		program.registers[X.value] *= (Y.type == 'register') ? program.registers[Y.value] : Y.value
		program.current++
	}
	cmd_mod(program, X, Y) {
		program.registers[X.value] %= (Y.type == 'register') ? program.registers[Y.value] : Y.value
		program.current++
	}
	cmd_rcv() { }
	cmd_recover(program, X) {
		if (((X.type == 'register') ? program.registers[X.value] : X.value) > 0) {
			program.waiting = true
		} else {
			program.current++
		}
	}
	cmd_receive(program, X) {
		if (program.queue.length > 0) {
			program.registers[X.value] = program.queue.shift()
			program.current++
		} else {
			program.waiting = true
		}
	}
	cmd_jpz(program, X, Y) {
		if (((X.type == 'register') ? program.registers[X.value] : X.value) > 0) {
			program.current += (Y.type == 'register') ? program.registers[Y.value] : Y.value
		} else {
			program.current++
		}
	}

	runProgram(id) {
		const inst = this.instructions[this.programs[id].current]

		if (!this.programs[id].waiting) {
			switch (inst.cmd) {
				case 'snd':
					this.cmd_snd(this.programs[id], inst.X)
					break
				case 'set':
					this.cmd_set(this.programs[id], inst.X, inst.Y)
					break
				case 'add':
					this.cmd_add(this.programs[id], inst.X, inst.Y)
					break
				case 'mul':
					this.cmd_mul(this.programs[id], inst.X, inst.Y)
					break
				case 'mod':
					this.cmd_mod(this.programs[id], inst.X, inst.Y)
					break
				case 'rcv':
					this.cmd_rcv(this.programs[id], inst.X)
					break
				case 'jgz':
					this.cmd_jpz(this.programs[id], inst.X, inst.Y)
					break

				default:
					console.log(`run : unknwon instruction ${inst[0]}`)
					this.programs[id].current++
					break
			}
		} else if (this.programs[id].queue.length > 0) {
			this.programs[id].registers[inst.X.value] = this.programs[id].queue.shift()
			this.programs[id].waiting = false
			this.programs[id].current++
		}
	}

	initProgram(id) {
		this.programs[id] = {
			id: id,
			registers: Object.assign({}, this.registers),
			queue: [],
			current: 0,
			waiting: false,
			send: 0
		}
		this.programs[id].registers.p = id
	}

	get part1() {
		this.initProgram(0)

		this.cmd_snd = this.cmd_sound
		this.cmd_rcv = this.cmd_recover

		while (!this.programs[0].waiting) {
			this.runProgram(0)
		}

		return this.programs[0].queue.pop()
	}

	get part2() {
		this.initProgram(0)
		this.initProgram(1)

		this.cmd_snd = this.cmd_send
		this.cmd_rcv = this.cmd_receive

		while (!(this.programs[0].waiting && this.programs[1].waiting)) {
			this.runProgram(0, 'sendMode')
			this.runProgram(1, 'sendMode')
		}

		return this.programs[1].send
	}
}

module.exports = {
	answer: function (input) {
		return new Duet(input)
	},

	part1: 7071,
	part2: 8001
}