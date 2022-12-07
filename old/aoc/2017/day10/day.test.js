const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('3,4,1,5', 5).part1).toBe(12)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer('').part2).toBe('a2582a3a0e66e6e86e3812dcb672a272')
		})

		test('test 2', () => {
			expect(Answer('AoC 2017').part2).toBe('33efeb34ea91902bb2f59c9920caa6cd')
		})

		test('test 3', () => {
			expect(Answer('1,2,3').part2).toBe('3efbe78a8d82f29979031a4aa0b16a9d')
		})

		test('test 4', () => {
			expect(Answer('1,2,4').part2).toBe('63960835bcdc130f0b66d7ff4f6a5a8e')
		})
	})
})
