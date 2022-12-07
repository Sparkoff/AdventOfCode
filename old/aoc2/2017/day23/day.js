class Coprocessor {
	constructor(instructions, debug) {
		this.instructions = this.parseInstructions(instructions)
		this.debug = !!debug
	}

	parseInstructions(instructions) {
		return instructions.map(i => {
			i = i.split(/\s/)

			const parse = x => (x.match(/[a-z]/)) ? {
				type: 'register',
				value: x
			} : {
				type: 'integer',
				value: parseInt(x)
			}
			i[1] = parse(i[1])
			i[2] = parse(i[2])

			return {
				cmd: i[0],
				X: i[1],
				Y: i[2]
			}
		})
	}

	run() {
		let register = 'abcdefgh'.split('').reduce((a, c) => {
			a[c] = 0
			return a
		}, {})
		let current = 0
		let count = 0

		while (current >= 0 && current < this.instructions.length) {
			const inst = this.instructions[current]

			if (inst.X.type == 'register' && !register.hasOwnProperty(inst.X.value)) {
				register[inst.X.value] = 0
			}
			if (inst.Y && inst.Y.type == 'register' && !register.hasOwnProperty(inst.Y.value)) {
				register[inst.Y.value] = 0
			}

			switch (inst.cmd) {
				case 'set':
					register[inst.X.value] = (inst.Y.type == 'register') ? register[inst.Y.value] : inst.Y.value
					current++
					break
				case 'sub':
					register[inst.X.value] -= (inst.Y.type == 'register') ? register[inst.Y.value] : inst.Y.value
					current++
					break
				case 'mul':
					register[inst.X.value] *= (inst.Y.type == 'register') ? register[inst.Y.value] : inst.Y.value
					current++
					count++
					break
				case 'jnz':
					if (((inst.X.type == 'register') ? register[inst.X.value] : inst.X.value) != 0) {
						current += (inst.Y.type == 'register') ? register[inst.Y.value] : inst.Y.value
					} else {
						current++
					}
					break

				default:
					console.log(`run : unknwon instruction ${inst[0]}`)
					current++
					break
			}
		}

		return count
	}



	// set b 99
	// set c b
	//
	// jnz a 2
	//   jnz 1 5
	//
	//   mul b 100
	//   sub b -100000
	//   set c b
	//   sub c -17000
	//
	//   set f 1
	//   set d 2
	//     set e 2
	//       set g d
	//       mul g e
	//       sub g b
	//       jnz g 2
	//         set f 0
	//
	//       sub e -1
	//       set g e
	//       sub g b
	//       jnz g -8
	//     sub d -1
	//     set g d
	//     sub g b
	//     jnz g -13
	//
	//   jnz f 2
	//     sub h -1
	//
	//     set g b
	//     sub g c
	//
	//     jnz g 2
	//       jnz 1 3
	//
	//     sub b -17
	//   jnz 1 -23

	// b = 99
	// c = b
	// if (a != 0) {
	//   b = b * 100 + 1E5
	//   c = b + 17000
	// }
	// while (true) {
	//   f = 1
	//   d = 2
	//   while (g != 0) {
	//     e = 2
	//     while (g != 0) {
	//       g = (d * e) - b
	//       if (g == 0) {
	//         f = 0
	//       }
	//       e += 1
	//       g = d - b
	//     }
	//     d += 1
	//     g = d - b
	//   }
	//   if (f == 0) {
	//     h += 1
	//   }
	//   g = b - c
	//   if (g == 0) {
	//     break
	//   }
	//   b += 17
	// }

	// b = a == 0 ? 99 : 109900
	// c = a == 0 ? 99 : 126900
	// 
	// for (b; b <= c; b += 17) {
	//   f = 1
	//   for (d = 2; d <= b; d++) {
	//     for (e = 2; e <= b; e++) {
	//       if (d * e == b) { // search for prime number
	//         f = 0
	//       }
	//     }
	//   }
	//   if (f == 0) {
	//     h += 1
	//   }
	// }


	runOptimized() {
		let b = 0, c = 0, d = 0, h = 0

		b = this.debug ? 79 : 109900
		c = this.debug ? 99 : 126900

		for (b; b <= c; b += 17) {
			for (d = 2; d <= Math.floor(Math.sqrt(b)); d++) {
				if (b % d == 0) {
					h += 1
					break
				}
			}
		}
		return h
	}

	get part1() {
		return this.run()
	}

	get part2() {
		return this.runOptimized()
	}
}

module.exports = {
	answer: function (input, debug) {
		return new Coprocessor(input, debug)
	},

	part1: 9409,
	part2: 913
}