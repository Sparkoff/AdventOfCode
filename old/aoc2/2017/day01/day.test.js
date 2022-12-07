const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer('1122').part1).to.equal(3)
		})

		it('test 2', () => {
			expect(Answer('1111').part1).to.equal(4)
		})

		it('test 3', () => {
			expect(Answer('1234').part1).to.equal(0)
		})

		it('test 4', () => {
			expect(Answer('91212129').part1).to.equal(9)
		})
	})

	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer('1212').part2).to.equal(6)
		})

		it('test 2', () => {
			expect(Answer('1221').part2).to.equal(0)
		})

		it('test 3', () => {
			expect(Answer('123425').part2).to.equal(4)
		})

		it('test 4', () => {
			expect(Answer('123123').part2).to.equal(12)
		})

		it('test 5', () => {
			expect(Answer('12131415').part2).to.equal(4)
		})
	})
})
