const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'x=495, y=2..7',
		'y=7, x=495..501',
		'x=501, y=3..7',
		'x=498, y=2..4',
		'x=506, y=1..2',
		'x=498, y=10..13',
		'x=504, y=10..13',
		'y=13, x=498..504'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(57)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(29)
		})
	})
})
