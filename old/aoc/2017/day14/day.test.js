const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer('flqrgnkx')

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.part1).toBe(8108)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(answer.part2).toBe(1242)
		})
	})
})
