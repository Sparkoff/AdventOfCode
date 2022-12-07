class Fractal {
	constructor(masks) {
		this.rules = masks.map(r => this.parseMask(r)).reduce(this.prepareRules, {})
		this.pattern = '.#./..#/###'
		this.step = 0
	}

	prepareRules(acc, rule) {
		if (!acc.hasOwnProperty(rule.size)) {
			acc[rule.size] = []
		}
		acc[rule.size].push(rule)
		return acc
	}
	parseMask(mask) {
		mask = mask.split(/\s=>\s/)

		let rule = {
			masks: [],
			result: mask[1],
			size: (mask[0].length == 5) ? 2 : 3
		}

		mask = mask[0]
		for (let i = 0; i < 4; i++) {
			rule.masks.push(mask)
			if (rule.size == 3) {
				rule.masks.push(this.flipMatrix(mask))
			}
			mask = this.rotateMatrix(mask)
		}

		return rule
	}
	rotateMatrix(m) {
		m = m.split('/').map(r => r.split(''))

		let corners = [
			m[0][0],
			m[0][m[0].length - 1],
			m[m[0].length - 1][m[0].length - 1],
			m[m[0].length - 1][0]
		]
		corners.push(corners.shift())
		m[0][0] = corners[0]
		m[0][m[0].length - 1] = corners[1]
		m[m[0].length - 1][m[0].length - 1] = corners[2]
		m[m[0].length - 1][0] = corners[3]

		if (m[0].length == 3) {
			let edges = [
				m[0][1],
				m[1][2],
				m[2][1],
				m[1][0]
			]
			edges.push(edges.shift())
			m[0][1] = edges[0]
			m[1][2] = edges[1]
			m[2][1] = edges[2]
			m[1][0] = edges[3]
		}

		return m.map(r => r.join('')).join('/')
	}
	flipMatrix(m) {
		m = m.split('/').map(r => r.split('').reverse())
		return m.map(r => r.join('')).join('/')
	}

	findMask(pattern, size) {
		for (let i = 0; i < this.rules[size].length; i++) {
			if (this.rules[size][i].masks.includes(pattern)) {
				return this.rules[size][i].result
			}
		}
		console.log(`no mask for pattern: ${pattern}`);
	}

	draw(limit) {
		let pattern = this.pattern.split('/')
		for (this.step; this.step < limit; this.step++) {
			let next = []

			let size = 0
			let maskSize = 0
			if (pattern[0].length % 2 == 0) {
				size = pattern[0].length / 2
				maskSize = 2
				for (let nr = 0; nr < size; nr++) {
					next.push([])
					for (let nc = 0; nc < size; nc++) {
						next[nr].push(`${pattern[2 * nr].substring(2 * nc, 2 * nc + 2)}/${pattern[2 * nr + 1].substring(2 * nc, 2 * nc + 2)}`)
					}
				}
			} else {
				size = pattern[0].length / 3
				maskSize = 3
				for (let nr = 0; nr < size; nr++) {
					next.push([])
					for (let nc = 0; nc < size; nc++) {
						next[nr].push(`${pattern[3 * nr].substring(3 * nc, 3 * nc + 3)}/${pattern[3 * nr + 1].substring(3 * nc, 3 * nc + 3)}/${pattern[3 * nr + 2].substring(3 * nc, 3 * nc + 3)}`)
					}
				}
			}

			for (let nr = 0; nr < size; nr++) {
				for (let nc = 0; nc < size; nc++) {
					next[nr][nc] = this.findMask(next[nr][nc], maskSize)
				}
			}

			pattern = []
			for (let nr = 0; nr < size; nr++) {
				for (let nc = 0; nc < size; nc++) {
					let p = next[nr][nc].split('/')
					if (nc == 0) {
						pattern.push(...(new Array(p.length)).fill(''))
					}
					for (let i = 0; i < p.length; i++) {
						pattern[p.length * nr + i] += p[i]
					}
				}
			}
		}

		this.pattern = pattern.join('/')
	}

	get stayOnCount() {
		return this.pattern.split('').reduce((a, c) => (c == '#') ? a + 1 : a, 0)
	}

	get part1() {
		this.draw(5)
		return this.stayOnCount
	}

	get part2() {
		this.draw(18)
		return this.stayOnCount
	}
}

module.exports = {
	answer: function (input) {
		return new Fractal(input)
	},

	part1: 152,
	part2: 1956174
}