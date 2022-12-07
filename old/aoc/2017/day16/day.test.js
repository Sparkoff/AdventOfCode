const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer('s1,x3/4,pe/b', 'abcde')

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe('baedc')
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe('abcde')
		})
	})
})
