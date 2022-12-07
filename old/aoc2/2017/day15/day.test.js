const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer([
				'Generator A starts with 65',
				'Generator B starts with 8921'
			], 5).part1).to.equal(1)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer([
				'Generator A starts with 65',
				'Generator B starts with 8921'
			], 1056).part2).to.equal(1)
		})
	})
})
