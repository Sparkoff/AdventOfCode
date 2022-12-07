const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'0 <-> 2',
		'1 <-> 1',
		'2 <-> 0, 3, 4',
		'3 <-> 2, 4',
		'4 <-> 2, 3, 6',
		'5 <-> 6',
		'6 <-> 4, 5'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(6)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(2)
		})
	})
})
