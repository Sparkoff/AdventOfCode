class StreamProcess {
	constructor(stream) {
		stream = stream.split('')
		stream.shift()
		this.stream = this.parseGroup(stream)
	}

	parseGroup(stream, score) {
		let group = {
			garbage: [],
			childs: [],
			score: score || 1,
			garbageCount: 0
		}

		let next = stream.shift()
		while (next != '}') {
			switch (next) {
				case '{':
					group.childs.push(this.parseGroup(stream, group.score + 1))
					break;
				case '<':
					var garbage = this.parseGarbage(stream)
					group.garbageCount += garbage.length
					group.garbage.push(garbage)
					break;
				case ',':
					break;

				default:
					console.log(`parseGroup error : ${next}`)
					break;
			}
			next = stream.shift()
		}
		return group
	}

	parseGarbage(stream) {
		let garbage = ""

		let next = stream.shift()
		while (next != '>') {
			switch (next) {
				case "!":
					stream.shift()
					break;
				default:
					garbage += next
					break;
			}
			next = stream.shift()
		}
		return garbage
	}

	getGroupScore(group) {
		group = group || this.stream
		let score = group.score

		for (let i = 0; i < group.childs.length; i++) {
			score += this.getGroupScore(group.childs[i])
		}

		return score
	}

	getGroupGarbage(group) {
		group = group || this.stream
		let count = group.garbageCount

		for (let i = 0; i < group.childs.length; i++) {
			count += this.getGroupGarbage(group.childs[i])
		}

		return count
	}

	get part1() {
		return this.getGroupScore()
	}

	get part2() {
		return this.getGroupGarbage()
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new StreamProcess(input)
	},

	part1: 12505,
	part2: 6671
}