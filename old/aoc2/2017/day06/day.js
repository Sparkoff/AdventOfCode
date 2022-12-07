function reallocation(memBank) {
	memBank = memBank.split(/\s/).map(b => parseInt(b))
	let n = memBank.length
	let known = []
	let step = 0

	let current = memBank.join(',')
	while (!known.includes(current)) {
		step++
		known.push(current)

		let max = Math.max(...memBank)
		let index = memBank.indexOf(max)
		let blocks = memBank[index]
		memBank[index] = 0

		for (let i = index + 1; i < n; i++) {
			memBank[i]++
			blocks--

			if (blocks == 0) {
				break
			}
		}

		let remains = blocks % n
		blocks -= remains
		for (let i = 0; i < remains; i++) {
			memBank[i]++
		}

		let balance = blocks / n
		for (let i = 0; i < n; i++) {
			memBank[i] += balance
		}

		current = memBank.join(',')
	}

	return {
		patternSize: step,
		loopSize: step - known.indexOf(current)
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		let cycle = reallocation(input)
		return {
			part1: cycle.patternSize,
			part2: cycle.loopSize
		}
	},

	part1: 7864,
	part2: 1695
}