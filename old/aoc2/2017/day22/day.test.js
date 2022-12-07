const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	describe('part 1', () => {
		it('test 1', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			], 7).part1).to.equal(5)
		})

		it('test 2', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			], 70).part1).to.equal(41)
		})

		it('test 3', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			]).part1).to.equal(5587)
		})
	})
	
	describe('part 2', () => {
		it('test 1', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			], 100).part2).to.equal(26)
		})

		it('test 2', () => {
			expect(Answer([
				'..#',
				'#..',
				'...'
			]).part2).to.equal(2511944)
		})
	})
})
