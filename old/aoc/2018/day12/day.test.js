const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'initial state: #..#.#..##......###...###',
		'',
		'...## => #',
		'..#.. => #',
		'.#... => #',
		'.#.#. => #',
		'.#.## => #',
		'.##.. => #',
		'.#### => #',
		'#.#.# => #',
		'#.### => #',
		'##.#. => #',
		'##.## => #',
		'###.. => #',
		'###.# => #',
		'####. => #'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(325)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(999999999374)
		})
	})
})
