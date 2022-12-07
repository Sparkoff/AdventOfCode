function neightboorSum (table, pt) {
	let sum = 0
	let x = pt.x
	let y = pt.y

	if (x == 0 && y == 0) {
		return 1
	}

	([
		[1, 0], [-1, 0], [0, 1], [0, -1],
		[1, 1], [1, -1], [-1, 1], [-1, -1],
	]).forEach(c => {
		const label = `${x + c[0]},${y + c[1]}`
		if (table.hasOwnProperty(label)) {
			sum += table[label]
		}
	})

	return sum
}

function calcGraph (final) {
	let graph = {
		table: {},
		final: {},
		first: -1
	}

	let next = {
		x: 0,
		y: 0
	}
	let dir = 'down'
	for (let i = 1; i <= final; i++) {
		const value = neightboorSum(graph.table, next)
		graph.table[next.x + ',' + next.y] = value

		if (value > final && graph.first == -1) {
			graph.first = value
		}

		if (i == final) {
			graph.final = next
		} else {
			switch (dir) {
				case 'right':
					if (!graph.table.hasOwnProperty(next.x + ',' + (next.y + 1))) {
						next.y++
						dir = 'up'
					} else {
						next.x++
					}
					break;
				case 'up':
					if (!graph.table.hasOwnProperty((next.x - 1) + ',' + next.y)) {
						next.x--
						dir = 'left'
					} else {
						next.y++
					}
					break;
				case 'left':
					if (!graph.table.hasOwnProperty(next.x + ',' + (next.y - 1))) {
						next.y--
						dir = 'down'
					} else {
						next.x--
					}
					break;
				case 'down':
					if (!graph.table.hasOwnProperty((next.x + 1) + ',' + next.y)) {
						next.x++
						dir = 'right'
					} else {
						next.y--
					}
					break;
			}
		}
	}

	return graph
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		let graph = calcGraph(parseInt(input))
		return {
			part1: Math.abs(graph.final.x) + Math.abs(graph.final.y),
			part2: graph.first
		}
	},

	part1: 371,
	part2: 369601
}