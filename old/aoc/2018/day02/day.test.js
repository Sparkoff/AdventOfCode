const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'abcdef',
				'bababc',
				'abbcde',
				'abcccd',
				'aabcdd',
				'abcdee',
				'ababab'
			]).part1).toBe(12)
		})
	})

	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer([
				'abcde',
				'fghij',
				'klmno',
				'pqrst',
				'fguij',
				'axcye',
				'wvxyz'
			]).part2).toBe('fgij')
		})
	})
})
