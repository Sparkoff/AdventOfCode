const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer('{}').part1).toBe(1)
		})

		test('test 2', () => {
			expect(Answer('{{{}}}').part1).toBe(6)
		})

		test('test 3', () => {
			expect(Answer('{{},{}}').part1).toBe(5)
		})

		test('test 4', () => {
			expect(Answer('{{{},{},{{}}}}').part1).toBe(16)
		})

		test('test 5', () => {
			expect(Answer('{<a>,<a>,<a>,<a>}').part1).toBe(1)
		})

		test('test 6', () => {
			expect(Answer('{{<ab>},{<ab>},{<ab>},{<ab>}}').part1).toBe(9)
		})

		test('test 7', () => {
			expect(Answer('{{<!!>},{<!!>},{<!!>},{<!!>}}').part1).toBe(9)
		})

		test('test 8', () => {
			expect(Answer('{{<a!>},{<a!>},{<a!>},{<ab>}}').part1).toBe(3)
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer('{<>}').part2).toBe(0)
		})

		test('test 2', () => {
			expect(Answer('{<random characters>}').part2).toBe(17)
		})

		test('test 3', () => {
			expect(Answer('{<<<<>}').part2).toBe(3)
		})

		test('test 4', () => {
			expect(Answer('{<{!>}>}').part2).toBe(2)
		})

		test('test 5', () => {
			expect(Answer('{<!!>}').part2).toBe(0)
		})

		test('test 6', () => {
			expect(Answer('{<!!!>>}').part2).toBe(0)
		})

		test('test 7', () => {
			expect(Answer('{<{o"i!a,<{i<a>}').part2).toBe(10)
		})
	})
})
