const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer(['aa bb cc dd ee']).part1).toBe(1)
		})

		test('test 2', () => {
			expect(Answer(['aa bb cc dd aa']).part1).toBe(0)
		})

		test('test 3', () => {
			expect(Answer(['aa bb cc dd aaa']).part1).toBe(1)
		})

		test('test 4', () => {
			expect(Answer([
				'aa bb cc dd ee',
				'aa bb cc dd aa',
				'aa bb cc dd aaa'
			]).part1).toBe(2)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer(['abcde fghij']).part2).toBe(1)
		})

		test('test 2', () => {
			expect(Answer(['abcde xyz ecdab']).part2).toBe(0)
		})

		test('test 3', () => {
			expect(Answer(['a ab abc abd abf abj']).part2).toBe(1)
		})

		test('test 4', () => {
			expect(Answer(['iiii oiii ooii oooi oooo']).part2).toBe(1)
		})

		test('test 5', () => {
			expect(Answer(['oiii ioii iioi iiio']).part2).toBe(0)
		})

		test('test 6', () => {
			expect(Answer([
				'abcde fghij',
				'abcde xyz ecdab',
				'a ab abc abd abf abj',
				'iiii oiii ooii oooi oooo',
				'oiii ioii iioi iiio'
			]).part2).toBe(3)
		})
	})
})
