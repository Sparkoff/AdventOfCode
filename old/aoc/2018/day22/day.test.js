const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'depth: 510',
		'target: 10, 10'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(114)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(45)
		})
	})
})
