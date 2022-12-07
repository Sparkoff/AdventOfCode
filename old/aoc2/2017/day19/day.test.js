const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer([
				'     |          ',
				'     |  +--+    ',
				'     A  |  C    ',
				' F---|----E|--+ ',
				'     |  |  |  D ',
				'     +B-+  +--+ ',
				'                '
			]).part1).to.equal('ABCDEF')
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer([
				'     |          ',
				'     |  +--+    ',
				'     A  |  C    ',
				' F---|--|-E---+ ',
				'     |  |  |  D ',
				'     +B-+  +--+ ',
				'                '
			]).part2).to.equal(38)
		})
	})
})
