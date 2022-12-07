const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer('3,4,1,5', 5).part1).to.equal(12)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer('').part2).to.equal('a2582a3a0e66e6e86e3812dcb672a272')
		})

		it('test 2', () => {
			expect(Answer('AoC 2017').part2).to.equal('33efeb34ea91902bb2f59c9920caa6cd')
		})

		it('test 3', () => {
			expect(Answer('1,2,3').part2).to.equal('3efbe78a8d82f29979031a4aa0b16a9d')
		})

		it('test 4', () => {
			expect(Answer('1,2,4').part2).to.equal('63960835bcdc130f0b66d7ff4f6a5a8e')
		})
	})
})
