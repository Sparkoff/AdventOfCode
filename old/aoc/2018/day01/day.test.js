const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'+1',
				'-2',
				'+3',
				'+1'
			]).part1).toBe(3)
		})

		test('test 2', () => {
			expect(Answer([
				'+1',
				'+1',
				'+1'
			]).part1).toBe(3)
		})

		test('test 3', () => {
			expect(Answer([
				'+1',
				'+1',
				'-2'
			]).part1).toBe(0)
		})

		test('test 4', () => {
			expect(Answer([
				'-1',
				'-2',
				'-3'
			]).part1).toBe(-6)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer([
				'+1',
				'-1'
			]).part2).toBe(0)
		})

		test('test 2', () => {
			expect(Answer([
				'+3',
				'+3',
				'+4',
				'-2',
				'-4'
			]).part2).toBe(10)
		})

		test('test 3', () => {
			expect(Answer([
				'-6',
				'+3',
				'+8',
				'+5',
				'-6'
			]).part2).toBe(5)
		})

		test('test 4', () => {
			expect(Answer([
				'+7',
				'+7',
				'-2',
				'-7',
				'-4'
			]).part2).toBe(14)
		})
	})
})
