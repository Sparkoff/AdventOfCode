class PassphraseValidator {
	constructor (passphrases) {
		this.passphrases = passphrases.map(p => p.split(/\s/))

		this.validList_duplicate = []
		this.validList_anagram = []

		this.run()
	}

	run () {
		this.passphrases.forEach(passphrase => {
			let duplicates = {
				knwonWords: [],
				valid: true
			}
			let anagrams = {
				knwonWords: [],
				valid: true
			}

			for (let word of passphrase) {
				if (!duplicates.valid && !anagrams.valid) {
					break
				}

				if (duplicates.valid) {
					if (duplicates.knwonWords.includes(word)) {
						duplicates.valid = false
					} else {
						duplicates.knwonWords.push(word)
					}
				}
				
				if (anagrams.valid) {
					word = word.split('')
					word.sort()
					word = word.join('')
					if (anagrams.knwonWords.includes(word)) {
						anagrams.valid = false
					} else {
						anagrams.knwonWords.push(word)
					}
				}
			}

			if (duplicates.valid) {
				this.validList_duplicate.push(passphrase.join(' '))
			}
			if (anagrams.valid) {
				this.validList_anagram.push(passphrase.join(' '))
			}
		})
	}

	get part1() {
		return this.validList_duplicate.length
	}

	get part2() {
		return this.validList_anagram.length
	}
}

module.exports = {
	answer: function (input) {
		return new PassphraseValidator(input)
	},

	part1: 383,
	part2: 265
}