const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'1, 1',
		'1, 6',
		'8, 3',
		'3, 4',
		'5, 5',
		'8, 9'
	], 32)

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(17)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(16)
		})
	})
})
