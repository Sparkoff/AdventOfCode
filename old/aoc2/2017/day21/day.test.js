const expect = require('chai').expect
const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	it('test 1', () => {
		let answer = Answer([
			'../.# => ##./#../...',
			'.#./..#/### => #..#/..../..../#..#'
		])
		answer.draw(2)
		expect(answer.stayOnCount).to.equal(12)
	})
})
