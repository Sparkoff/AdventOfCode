const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		test('test 1', () => {
			expect(Answer([
				'     |          ',
				'     |  +--+    ',
				'     A  |  C    ',
				' F---|----E|--+ ',
				'     |  |  |  D ',
				'     +B-+  +--+ ',
				'                '
			]).part1).toBe('ABCDEF')
		})
	})
	
	describe('part 2', () => {
		test('test 1', () => {
			expect(Answer([
				'     |          ',
				'     |  +--+    ',
				'     A  |  C    ',
				' F---|--|-E---+ ',
				'     |  |  |  D ',
				'     +B-+  +--+ ',
				'                '
			]).part2).toBe(38)
		})
	})
})
