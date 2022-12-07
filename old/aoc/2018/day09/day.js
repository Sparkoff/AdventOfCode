class MarbleGame {
	constructor(setup) {
		setup = setup.match(/(\d+)/g)
		this.players = parseInt(setup[0])
		this.lastMarble = parseInt(setup[1])

		this.currentMarble = this.makeLinkedNode()
		this.currentPlayer = 0
		this.playerScores = new Array(this.players).fill(0)
	}

	play(extra) {
		const start = extra ? this.lastMarble + 1 : 1
		const stop = extra ? this.lastMarble * 100 : this.lastMarble

		for (let id = start; id <= stop; id++) {
			this.currentPlayer = (id % this.players) + 1

			if (id % 23 == 0) {
				this.currentMarble = this.currentMarble.previous.previous.previous.previous.previous.previous.previous
				
				this.playerScores[this.currentPlayer - 1] += id + this.currentMarble.value

				this.currentMarble = this.removeLinkedNode(this.currentMarble)
			} else {
				this.currentMarble = this.makeLinkedNode(id, this.currentMarble.next)
			}
		}

		return Math.max(...this.playerScores)
	}

	makeLinkedNode(value, previous) {
		let linkedNode = {
			previous: null,
			next: null,
			value: value || 0
		}

		linkedNode.previous = previous ? previous : linkedNode
		linkedNode.next = previous ? previous.next : linkedNode

		if (previous) {
			previous.next.previous = linkedNode
			previous.next = linkedNode
		}

		return linkedNode
	}
	removeLinkedNode(node) {
		let previous = node.previous
		let next = node.next

		previous.next = next
		next.previous = previous

		node = null

		return next
	}

	get part1() {
		return this.play()
	}

	get part2() {
		return this.play('extra')
	}
}

module.exports = {
	answer: function (input) {
		input = (Array.isArray(input)) ? input[0] : input
		return new MarbleGame(input)
	},

	part1: 361466,
	part2: 2945918550
}