const interpreter = require('../day16/day').answer()

class Underflow {
	constructor(program) {
		this.registers = new Array(6).fill(0)
		this.ip = 0
		this.instructions = []

		this.parseProgram(program)
	}

	parseProgram(program) {
		this.ip = parseInt(program.shift().slice(-1))

		while (program.length != 0) {
			let p = program.shift().split(/\s/)
			this.instructions.push({
				cmd: p[0],
				A: parseInt(p[1]),
				B: parseInt(p[2]),
				C: parseInt(p[3])
			})
		}
	}

	run(a) {
		let reg = this.registers.slice(0)
		reg[0] = a
		while (reg[this.ip] < this.instructions.length) {
			let inst = this.instructions[reg[this.ip]]
			reg = interpreter.exec(inst.cmd, inst.A, inst.B, inst.C, reg)
			reg[this.ip]++
		}

		return [reg[0], reg[1], reg[4]]
	}

	// #ip 3					->		 register = [0: a, 1: b, 2: c, 3: ip, 4: e, 5: f]
	//  0: seti 123 0 4			->		  0: e = 123
	//  1: bani 4 456 4			->		  1: e &= 456
	//  2: eqri 4 72 4			->		  2: e = (e == 72) ? 1 : 0
	//  3: addr 4 3 3			->		  3: ip += e
	//  4: seti 0 0 3			->		  4: ip = 0
	//  5: seti 0 6 4			->		  5: e = 0
	//  6: bori 4 65536 1		->		  6: b = e | 65536
	//  7: seti 678134 1 4		->		  7: e = 678134
	//  8: bani 1 255 5			->		  8: f = b & 255
	//  9: addr 4 5 4			->		  9: e += f
	// 10: bani 4 16777215 4	->		 10: e &= 16777215
	// 11: muli 4 65899 4		->		 11: e *= 65899
	// 12: bani 4 16777215 4	->		 12: e &= 16777215
	// 13: gtir 256 1 5			->		 13: f = (256 > b) ? 1 : 0
	// 14: addr 5 3 3			->		 14: ip += f
	// 15: addi 3 1 3			->		 15: ip += 1
	// 16: seti 27 8 3			->		 16: ip = 27
	// 17: seti 0 1 5			->		 17: f = 0
	// 18: addi 5 1 2			->		 18: c = f + 1
	// 19: muli 2 256 2			->		 19: c *= 256
	// 20: gtrr 2 1 2			->		 20: c = (c > b) ? 1 : 0
	// 21: addr 2 3 3			->		 21: ip += c
	// 22: addi 3 1 3			->		 22: ip++
	// 23: seti 25 7 3			->		 23: ip = 25
	// 24: addi 5 1 5			->		 24: f++
	// 25: seti 17 1 3			->		 25: ip = 17
	// 26: setr 5 3 1			->		 26: b = f
	// 27: seti 7 8 3			->		 27: ip = 7
	// 28: eqrr 4 0 5			->		 28: f = (e == a) ? 1 : 0
	// 29: addr 5 3 3			->		 29: ip += f
	// 30: seti 5 4 3			->		 30: ip = 5


	// e = 123
	// ip = 1
	// switch (ip) {
	// 	case 1:
	// 		e &= 456
	// 		if (e != 72) {
	// 			ip = 1 // JUMP 1
	// 		} else {
	// 			e = 0
	// 			ip = 6 // JUMP 6
	// 		}
	// 		break
	// 	case 6:
	// 		b = e | 65536
	// 		e = 678134
	// 		ip = 8 // JUMP 8
	// 		break
	// 	case 8:
	// 		f = b & 255
	// 		e += f
	// 		e &= 16777215
	// 		e *= 65899
	// 		e &= 16777215
	// 		if (256 > b) {
	// 			if (e == a) {
	// 				return
	// 			} else {
	// 				ip = 6 // JUMP 6
	// 			}
	// 		} else {
	// 			f = 0
	// 			ip = 18 // JUMP 18
	// 		}
	// 		break
	// 	case 18:
	// 		c = f + 1
	// 		c *= 256
	// 		if (c > b) {
	// 			b = f
	// 			ip = 8 // JUMP 8
	// 		} else {
	// 			f++
	// 			ip = 18 // JUMP 18
	// 		}
	// 		break
	// }


	// registers = [a: target, b: searchParam, c: tmpValue, d: ip, e: searchParam, f: tmpValue]
	//
	// e = 123
	// while (e != 72) {
	// 	e &= 456
	// }
	// e = 0
	// do {
	// 	b = e | 65536
	// 	e = 678134
	// 	while (b >= 256) {
	// 		f = b & 255
	// 		e += f
	// 		e &= 16777215
	// 		e *= 65899
	// 		e &= 16777215
	// 		f = 0
	// 		while (true) {
	// 			c = f + 1
	// 			c *= 256
	//			if (c < b) {
	//				f++
	//			} else {
	// 				b = f
	// 				break
	//			}
	// 		}
	// 	}
	// } while (e != a)

	runSimplify(a) {
		let b = 0
		let e = 0

		// e = 123 & 456
		// while (e != 72) { }
		// e = 0
		while (true) {
			b = e | 65536
			e = 678134

			while (true) {
				e += b & 255
				e &= 16777215
				e *= 65899
				e &= 16777215

				if (b >= 256) {
					b = Math.floor(b / 256)
				} else {
					break
				}
			}

			if (e == a) {
				return [a, b, e]
			}
		}
	}


	get part1() {
		let b = 65536
		let e = 678134
		while (true) {
			e += b & 255
			e &= 16777215
			e *= 65899
			e &= 16777215

			if (b >= 256) {
				b = Math.floor(b / 256)
			} else {
				return e
			}
		}
	}

	get part2() {
		let knowns = new Set()

		let b = 0
		let e = 0
		while (true) {
			b = e | 65536
			e = 678134

			while (true) {
				e += b & 255
				e &= 16777215
				e *= 65899
				e &= 16777215

				if (b >= 256) {
					b = Math.floor(b / 256)
				} else {
					break
				}
			}

			if (knowns.has(e)) {
				return [...knowns.values()].pop()
			} else {
				knowns.add(e)
			}
		}
	}
}

module.exports = {
	answer: function (input) {
		return new Underflow(input)
	},

	part1: 10961197,
	part2: 8164934
}
