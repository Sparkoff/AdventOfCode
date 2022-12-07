const interpreter = require('../day16/day').answer()

class Flow {
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

	run() {
		while (this.registers[this.ip] < this.instructions.length) {
			let inst = this.instructions[this.registers[this.ip]]
			this.registers = interpreter.exec(inst.cmd, inst.A, inst.B, inst.C, this.registers)
			this.registers[this.ip]++
		}
	}

	// #ip 3				->		 register = [0: a, 1: b, 2: c, 3: ip, 4: e, 5: f]
	//  0: addi 3 16 3		->		  0: ip += 16
	//  1: seti 1 8 1		->		  1: b = 1
	//  2: seti 1 3 4		->		  2: e = 1
	//  3: mulr 1 4 2		->		  3: c = b * e
	//  4: eqrr 2 5 2		->		  4: c = (c == f) ? 1 : 0
	//  5: addr 2 3 3		->		  5: ip += c
	//  6: addi 3 1 3		->		  6: ip++
	//  7: addr 1 0 0		->		  7: a += b
	//  8: addi 4 1 4		->		  8: e++
	//  9: gtrr 4 5 2		->		  9: c = (e > f) ? 1 : 0
	// 10: addr 3 2 3		->		 10: ip += c
	// 11: seti 2 6 3		->		 11: ip = 2
	// 12: addi 1 1 1		->		 12: b++
	// 13: gtrr 1 5 2		->		 13: c = (b > f) ? 1 : 0
	// 14: addr 2 3 3		->		 14: ip += c
	// 15: seti 1 5 3		->		 15: ip = 1
	// 16: mulr 3 3 3		->		 16: ip = ip * ip
	// 17: addi 5 2 5		->		 17: f += 2
	// 18: mulr 5 5 5		->		 18: f = f * f
	// 19: mulr 3 5 5		->		 19: f *= ip
	// 20: muli 5 11 5		->		 20: f *= 11
	// 21: addi 2 5 2		->		 21: c += 5
	// 22: mulr 2 3 2		->		 22: c *= ip
	// 23: addi 2 21 2		->		 23: c += 21
	// 24: addr 5 2 5		->		 24: f += c
	// 25: addr 3 0 3		->		 25: ip += a
	// 26: seti 0 4 3		->		 26: ip = 0
	// 27: setr 3 1 2		->		 27: c = ip
	// 28: mulr 2 3 2		->		 28: c *= ip
	// 29: addr 3 2 2		->		 29: c += ip
	// 30: mulr 3 2 2		->		 30: c *= ip
	// 31: muli 2 14 2		->		 31: c *= 14
	// 32: mulr 2 3 2		->		 32: c *= ip
	// 33: addr 5 2 5		->		 33: f += c
	// 34: seti 0 3 0		->		 34: a = 0
	// 35: seti 0 6 3		->		 35: ip = 0


	// switch (ip) {
	// 	case 0:
	// 		ip = 17
	// 		break
	// 	case 1:
	// 		b = 1
	// 		ip = 2
	// 		break
	// 	case 2:
	// 		e = 1
	// 		ip = 3
	// 		break
	// 	case 3:
	// 		if (b * e == f) {
	// 			a += b
	// 		}
	// 		e++
	// 		if (e > f) {
	// 			b++
	// 			if (b > f) {
	// 				return a
	// 			} else {
	// 				ip = 2
	// 			}
	// 		} else {
	// 			ip = 3
	// 		}
	// 		break
	// 	case 17:
	// 		f = Math.pow(f + 2,2) * 19 * 11
	// 		c = (c + 5) * 22 + 21
	// 		f += c
	// 		if (a == 1) {
	// 			c = (27 * 28 + 29) * 30 * 14 * 32
	// 			f += c
	// 			a = 0
	// 		} else if (a > 1) {
	// 			return a
	// 		}
	// 		ip = 1
	// 		break
	// }


	// registers = [a: debugMode + primeSum, b: firstPrime, c: tmpValue, d: ip, e: secondPrime, f: numberToInvestigate]
	//
	// f = 4 * 19 * 11 + (5 * 22 + 21)
	// if (a != 0) {
	// 	f += (27 * 28 + 29) * 30 * 14 * 32
	// 	a = 0
	// }
	// b = 1
	//
	// while (b <= f) {
	// 	e = 1
	// 	while (e <= f) {
	// 		if (b * e == f) {  <-- search for prime numbers
	// 			a += b  <-- sum prime numbers found
	// 		}
	// 		e++
	// 	}
	// 	b++
	// }
	//
	// return a  <-- This program return the sum of all prime numbers of f (including 1 and f)

	sumPrimeNumbers(debugMode) {
		let a = 0
		let f = 0

		f = 967
		if (!debugMode) {
			f += 10550400
		}

		for (let b = 1; b < Math.floor(Math.sqrt(f)); b++) { // Optimisation : research a prime number of f, the greatess would be sqrt(f) rounded to inferior integer
			if (f % b == 0) { // Optimisation: search for division with null remains : the division result and b would be primes
				a += b + f / b
			}
		}

		return a
	}


	get part1() {
		this.run()
		return this.registers[0]
	}

	get part2() {
		return this.sumPrimeNumbers()
	}
}

module.exports = {
	answer: function (input) {
		return new Flow(input)
	},

	part1: 968,
	part2: 10557936
}
