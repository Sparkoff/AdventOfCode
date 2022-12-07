function checksum (spreadsheet) {
	spreadsheet = spreadsheet.map(row => row.split(/\s/).map(col => parseInt(col)))

	let output = {
		diff: 0,
		divisible: 0
	}
	for (let i = 0; i < spreadsheet.length; i++) {
		let row = spreadsheet[i]

		output.diff += Math.max(...row) - Math.min(...row)

		let found = false
		for (let j = 0; j < row.length; j++) {
			for (let k = j + 1; k < row.length; k++) {
				if (row[j] % row[k] == 0) {
					output.divisible += row[j] / row[k]
					found = true
				} else if (row[k] % row[j] == 0) {
					output.divisible += row[k] / row[j]
					found = true
				}
				if (found) {
					break
				}
			}
			if (found) {
				break
			}
		}
	}
	return output
}

module.exports = {
	answer: function (input) {
		let cs = checksum(input)
		return {
			part1: cs.diff,
			part2: cs.divisible
		}
	},

	part1: 42378,
	part2: 246
}