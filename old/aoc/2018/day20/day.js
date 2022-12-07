class RegexMap {
	constructor(map) {
		this.map = map.slice(1, -1)
	}

	get part1() {
		let map = this.map.slice(0)

		while (map.indexOf('(') != -1) {
			map = map.replace(/\([NEWS]+\|\)/g, '') // remove alternative path which are dead-end '(NEWS|)'

			let match = map.match(/\(([NEWS\|]+)\)/) // get alternalive paht like '(SW|NNNE)'
			if (match) {
				let longest = match[1].split('|').sort((a, b) => b.length - a.length)[0]
				map = map.replace(match[0], longest)
			}
		}

		return map.length
	}

	get part2() {
		let map = this.map.slice(0)
		let rooms = new Map()

		let dist = 0
		let x = 0
		let y = 0
		let alternates = []

		for (let i = 0; i < map.length; i++) {
			let current = map[i]

			if (current == '(') {
				// store origin of the alternates path
				alternates.push([x, y, dist])
			} else if (current == '|' || current == ')') {
				// an alternative path is ended, reset origin information before running next alternative
				// delete origin if end of alternates
				[x, y, dist] = (current == '|') ? alternates[alternates.length - 1] : alternates.pop()
			} else {
				if (current == 'E') x++
				if (current == 'W') x--
				if (current == 'S') y++
				if (current == 'N') y--

				dist++

				let label = `${x},${y}`
				if (!rooms.has(label) || rooms.get(label) > dist) {
					rooms.set(label, dist)
				}
			}
		}

		return ([...rooms.values()]).reduce((a, c) => a + ((c >= 1000) ? 1 : 0), 0)
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new RegexMap(input)
	},

	part1: 4214,
	part2: 8492
}
