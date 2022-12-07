class Village {
	constructor(records) {
		this.map = {}

		this.buildMap(records)
	}

	buildMap(records) {
		for (let i = 0; i < records.length; i++) {
			let record = records[i].split(/\s<\->\s/)
			record[0] = parseInt(record[0])
			this.map[record[0]] = record[1].split(/,\s/).map(p => parseInt(p))
		}
	}

	getGroup(origin) {
		let next = [origin]
		let group = []

		while (next.length > 0) {
			let current = next.shift()
			if (!group.includes(current)) {
				group.push(current)
				next = next.concat(this.map[current])
			}
		}

		return group
	}

	get groups() {
		let programs = Object.keys(this.map).map(p => parseInt(p))
		let groups = []

		while (programs.length > 0) {
			let group = this.getGroup(programs[0])
			programs = programs.filter(p => !group.includes(p))
			groups.push(group)
		}

		return groups
	}

	get part1() {
		return this.getGroup(0).length
	}

	get part2() {
		return this.groups.length
	}
}

module.exports = {
	answer: function (input) {
		return new Village(input)
	},

	part1: 175,
	part2: 213
}