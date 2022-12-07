const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'0/2',
		'2/2',
		'2/3',
		'3/4',
		'3/5',
		'0/1',
		'10/1',
		'9/10'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(31)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(19)
		})
	})
})
