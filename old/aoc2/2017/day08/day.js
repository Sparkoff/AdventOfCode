class CPU {
	constructor(instructions) {
		this.instructions = []
		this.registers = {}
		this.memoryLoad = 0

		this.parseInstructions(instructions)
		this.execute()
	}

	parseInstructions(instructions) {
		for (let i = 0; i < instructions.length; i++) {
			let line = instructions[i].split(/\sif\s/)
			let update = line[0].match(/(\w+)\s(inc|dec)\s(\-?\d+)/)
			let condifition = line[1].match(/(\w+)\s([><=!]+)\s(\-?\d+)/)

			let instruction = {
				update: {
					register: update[1],
					operation: update[2],
					value: parseInt(update[3])
				},
				condifition: {
					register: condifition[1],
					operator: condifition[2],
					value: parseInt(condifition[3])
				}
			}

			if (!this.registers.hasOwnProperty(instruction.update.register)) {
				this.registers[instruction.update.register] = 0
			}
			if (!this.registers.hasOwnProperty(instruction.condifition.register)) {
				this.registers[instruction.condifition.register] = 0
			}

			this.instructions.push(instruction)
		}
	}

	execute() {
		for (let i = 0; i < this.instructions.length; i++) {
			let inst = this.instructions[i]
			let modify = false
			switch (inst.condifition.operator) {
				case '<':
					modify = (this.registers[inst.condifition.register] < inst.condifition.value)
					break
				case '>':
					modify = (this.registers[inst.condifition.register] > inst.condifition.value)
					break
				case '<=':
					modify = (this.registers[inst.condifition.register] <= inst.condifition.value)
					break
				case '>=':
					modify = (this.registers[inst.condifition.register] >= inst.condifition.value)
					break
				case '==':
					modify = (this.registers[inst.condifition.register] == inst.condifition.value)
					break
				case '!=':
					modify = (this.registers[inst.condifition.register] != inst.condifition.value)
					break
			}
			if (modify) {
				this.registers[inst.update.register] += (inst.update.operation == 'inc') ? inst.update.value : -1 * inst.update.value
				if (this.memoryLoad < this.registers[inst.update.register]) {
					this.memoryLoad = this.registers[inst.update.register]
				}
			}
		}
	}

	get largestValue() {
		let value = null
		for (const r in this.registers) {
			if (value === null) {
				value = this.registers[r]
			} else if (value < this.registers[r]) {
				value = this.registers[r]
			}
		}
		return value
	}

	get part1() {
		return this.largestValue
	}

	get part2() {
		return this.memoryLoad
	}
}


module.exports = {
	answer: function (input) {
		return new CPU(input)
	},

	part1: 4567,
	part2: 5636
}