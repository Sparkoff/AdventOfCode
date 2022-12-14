const Answer = require('./day').answer

describe(require('../../utils/utils').getDirname(__dirname), () => {
	let answer = Answer([
		'#ip 3',
		'seti 123 0 4',
		'bani 4 456 4',
		'eqri 4 72 4',
		'addr 4 3 3',
		'seti 0 0 3',
		'seti 0 6 4',
		'bori 4 65536 1',
		'seti 678134 1 4',
		'bani 1 255 5',
		'addr 4 5 4',
		'bani 4 16777215 4',
		'muli 4 65899 4',
		'bani 4 16777215 4',
		'gtir 256 1 5',
		'addr 5 3 3',
		'addi 3 1 3',
		'seti 27 8 3',
		'seti 0 1 5',
		'addi 5 1 2',
		'muli 2 256 2',
		'gtrr 2 1 2',
		'addr 2 3 3',
		'addi 3 1 3',
		'seti 25 7 3',
		'addi 5 1 5',
		'seti 17 1 3',
		'setr 5 3 1',
		'seti 7 8 3',
		'eqrr 4 0 5',
		'addr 5 3 3',
		'seti 5 4 3'
	])

	describe('part 1', () => {
		test('test 1', () => {
			expect(answer.runSimplify(10961197)).toStrictEqual(answer.run(10961197))
		})
	})
})
