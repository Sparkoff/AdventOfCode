const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			], 7).part1).toBe(5)
		})

		test('test 2', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			], 70).part1).toBe(41)
		})

		test('test 3', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			]).part1).toBe(5587)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			], 100).part2).toBe(26)
		})

		test('test 2', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			]).part2).toBe(2511944)
		})
	})
})
