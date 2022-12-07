const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer(3)

	describe('part 1', () => {
		it('test 1', () => {
			expect(answer.part1).to.equal(638)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(answer.part2).to.equal(1222153)
		})
	})
})
