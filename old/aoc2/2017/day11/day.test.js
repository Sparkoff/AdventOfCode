const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer('ne,ne,ne').part1).to.equal(3)
		})

		it('test 2', () => {
			expect(Answer('ne,ne,sw,sw').part1).to.equal(0)
		})

		it('test 3', () => {
			expect(Answer('ne,ne,s,s').part1).to.equal(2)
		})

		it('test 4', () => {
			expect(Answer('se,sw,se,sw,sw').part1).to.equal(3)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer('ne,ne,ne').part2).to.equal(3)
		})

		it('test 2', () => {
			expect(Answer('ne,ne,sw,sw').part2).to.equal(2)
		})

		it('test 3', () => {
			expect(Answer('ne,ne,s,s').part2).to.equal(2)
		})

		it('test 4', () => {
			expect(Answer('se,sw,se,sw,sw').part2).to.equal(3)
		})
	})
})
