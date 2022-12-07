const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer('s1,x3/4,pe/b', 'abcde')

	describe('part 1', () => {
		it('test 1', () => {
			expect(answer.part1).to.equal('baedc')
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(answer.part2).to.equal('abcde')
		})
	})
})
