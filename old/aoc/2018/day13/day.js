const DIR = {
	UP: 0,
	RIGHT: 1,
	DOWN: 2,
	LEFT: 3
}
const GO = {
	[DIR.UP]: { x: 0, y: -1 },
	[DIR.RIGHT]: { x: 1, y: 0 },
	[DIR.DOWN]: { x: 0, y: 1 },
	[DIR.LEFT]: { x: -1, y: 0 }
}
const TURN = {
	LEFT: 0,
	STRAIGHT: 1,
	RIGHT: 2
}

class CartCrash {
	constructor(tracks) {
		this.map = new Map()
		this.carts = new Map()

		this.crashes = []
		this.lastPosition = ''

		this.init(tracks)
		this.run()
	}

	init(tracks) {
		this.map.set('X', tracks[0].length)
		this.map.set('Y', tracks.length)

		tracks.forEach((row, y) => {
			row = row.split('')
			row.forEach((slot, x) => {
				const id = this.idx(x, y)
				switch (slot) {
					case '^':
						this.map.set(id, '|')
						this.carts.set(id, this.newCart(x, y, DIR.UP))
						break
					case 'v':
						this.map.set(id, '|')
						this.carts.set(id, this.newCart(x, y, DIR.DOWN))
						break
					case '<':
						this.map.set(id, '-')
						this.carts.set(id, this.newCart(x, y, DIR.LEFT))
						break
					case '>':
						this.map.set(id, '-')
						this.carts.set(id, this.newCart(x, y, DIR.RIGHT))
						break
					case '/':
					case '\\':
					case '-':
					case '|':
					case '+':
						this.map.set(id, slot)
						break
				}
			})
		})
	}
	newCart(x, y, dir) {
		return {
			pt: { x, y },
			dir,
			turn: TURN.LEFT,
			crash: false
		}
	}

	idx(x, y) {
		return y * this.map.get('X') + x
	}

	move(cart) {
		cart.pt.x += GO[cart.dir].x
		cart.pt.y += GO[cart.dir].y

		let id = this.idx(cart.pt.x, cart.pt.y)
		let track = this.map.get(id)

		switch (track) {
			case '/':
				if (cart.dir == DIR.UP || cart.dir == DIR.DOWN) {
					cart.dir++
				} else {
					cart.dir--
				}
				break;
			case '\\':
				if (cart.dir == DIR.UP || cart.dir == DIR.DOWN) {
					cart.dir = (cart.dir + 3) % 4
				} else {
					cart.dir = (cart.dir + 1) % 4
				}
				break;
			case '+':
				if (cart.turn == TURN.LEFT) {
					cart.dir = (cart.dir + 3) % 4
				} else if (cart.turn == TURN.RIGHT) {
					cart.dir = (cart.dir + 1) % 4
				}
				cart.turn = (cart.turn + 1) % 3
				break;
		}

		return {
			pt: cart.pt,
			id,
			toString: `${cart.pt.x},${cart.pt.y}`,
			cart
		}
	}

	run() {
		let carts = new Map(this.carts)

		while (carts.size > 1) {
			let crashes = []

			const sortedIds = [...carts.keys()].sort((a, b) => a - b)

			sortedIds.forEach(id => {
				let cart = carts.get(id)
				if (cart) {
					carts.delete(id)

					let newPt = this.move(cart)

					if (carts.has(newPt.id)) {
						carts.delete(newPt.id)
						crashes.push(newPt.toString)
					} else {
						carts.set(newPt.id, cart)
					}
				}
			})

			this.crashes = this.crashes.concat(crashes)
		}

		if (carts.size == 1) {
			const [winner] = carts.values()
			this.lastPosition = `${winner.pt.x},${winner.pt.y}`
		}
	}

	get part1() {
		return this.crashes[0]
	}

	get part2() {
		return this.lastPosition
	}
}

module.exports = {
	answer: function (input) {
		return new CartCrash(input)
	},

	part1: '118,112',
	part2: '50,21'
}