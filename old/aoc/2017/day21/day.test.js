const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	test('test 1', () => {
		let answer = Answer([
			'../.# => ##./#../...',
			'.#./..#/### => #..#/..../..../#..#'
		])
		answer.draw(2)
		expect(answer.stayOnCount).toBe(12)
	})
})
