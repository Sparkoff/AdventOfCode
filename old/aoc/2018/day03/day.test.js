const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'#1 @ 1,3: 4x4',
		'#2 @ 3,1: 4x4',
		'#3 @ 5,5: 2x2'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(4)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(3)
		})
	})
})
