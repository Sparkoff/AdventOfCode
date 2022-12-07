const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([])

	describe('exec', () => {
		test('test 1', () => {
			expect(answer.exec('addr', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 6, 4])
		})

		test('test 2', () => {
			expect(answer.exec('addi', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 5, 4])
		})

		test('test 3', () => {
			expect(answer.exec('mulr', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 8, 4])
		})

		test('test 4', () => {
			expect(answer.exec('muli', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 6, 4])
		})

		test('test 5', () => {
			expect(answer.exec('banr', 1, 3, 2, [1, 5, 3, 3])).toStrictEqual([1, 5, 1, 3])
		})

		test('test 6', () => {
			expect(answer.exec('bani', 1, 3, 2, [1, 5, 3, 4])).toStrictEqual([1, 5, 1, 4])
		})

		test('test 7', () => {
			expect(answer.exec('borr', 1, 3, 2, [1, 5, 3, 3])).toStrictEqual([1, 5, 7, 3])
		})

		test('test 8', () => {
			expect(answer.exec('bori', 1, 5, 2, [1, 3, 3, 4])).toStrictEqual([1, 3, 7, 4])
		})

		test('test 9', () => {
			expect(answer.exec('setr', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 2, 4])
		})

		test('test 10', () => {
			expect(answer.exec('seti', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 1, 4])
		})

		test('test 11', () => {
			expect(answer.exec('gtir', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 0, 4])
			expect(answer.exec('gtir', 5, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 1, 4])
		})

		test('test 12', () => {
			expect(answer.exec('gtri', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 0, 4])
			expect(answer.exec('gtri', 1, 1, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 1, 4])
		})

		test('test 13', () => {
			expect(answer.exec('gtrr', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 0, 4])
			expect(answer.exec('gtrr', 3, 1, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 1, 4])
		})

		test('test 14', () => {
			expect(answer.exec('eqir', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 0, 4])
			expect(answer.exec('eqir', 4, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 1, 4])
		})

		test('test 15', () => {
			expect(answer.exec('eqri', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 0, 4])
			expect(answer.exec('eqri', 1, 2, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 1, 4])
		})

		test('test 16', () => {
			expect(answer.exec('eqrr', 1, 3, 2, [1, 2, 3, 4])).toStrictEqual([1, 2, 0, 4])
			expect(answer.exec('eqrr', 1, 3, 2, [1, 4, 3, 4])).toStrictEqual([1, 4, 1, 4])
		})
	})
})