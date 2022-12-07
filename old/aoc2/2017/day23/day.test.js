const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer([
				'set a 10',
				'mul a 1',
				'sub a 1',
				'jnz a -2'
			]).part1).to.equal(10)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer([], "debug").part2).to.equal(1)
		})
	})
})
