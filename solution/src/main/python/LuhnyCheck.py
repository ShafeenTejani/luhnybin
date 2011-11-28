#!/usr/bin/env python
# encoding: utf-8
"""
LuhnyCheck.py

A Python Solution to the luhnybin problem.

Maintains sequences of digits and delimiters and marks them
if they belong to a Luhn sequence. On output, the marked entries
are masked with an 'X'

"""

import sys
import os

DELIMITERS = [' ', '-']
MASK = 'X'

class MarkableEntry:
	
	def __init__(self,value):
		self.value = value
		self.marked = False
	
	def mark(self):
		self.marked = True
	
	def unmark(self):
		self.marked = False


class LuhnSequence:
	
	def __init__(self):
		self.numbers = []
		self.verbose = []
	
	def add_number(self, num):
		entry = MarkableEntry(num)
		self.numbers.append(entry)
		self.verbose.append(entry)
	
	def add_delimiter(self, delimiter):
		self.verbose.append(MarkableEntry(delimiter))
	
	def mark_luhn_numbers(self):
		for length in [14,15,16]:
			if len(self.numbers) < length:
				break
			checklist = self.numbers[-length:]
			if is_luhn_sequence([int(c.value) for c in checklist]):
				for c in checklist:
					c.mark()
		
	def output_masked(self,mask,output):
			for entry in self.verbose:
				output(mask if entry.marked else entry.value)
	
	def is_empty(self):
		return len(self.numbers) == 0
		
	def clear(self):
		self.numbers = []
		self.verbose = []
	


def process_input(input, output):
	
	sequence = LuhnSequence()
	
	for i in input:
		if i.isdigit():
			#process for digit
			sequence.add_number(i)
			sequence.mark_luhn_numbers()
			
		elif i in DELIMITERS:
			#process for delimiters
			if sequence.is_empty():
				output(i)
			else:
				sequence.add_delimiter(i)
		else:
			#process for text
			sequence.output_masked(MASK,output)
			sequence.clear()
			output(i)
	
	#clean up
	if not sequence.is_empty():
		sequence.output_masked(MASK,output)
		sequence.clear()
	
	

def is_luhn_sequence(numbers):
	return (sum(numbers[::-2]) + sum(sum(divmod(2*d,10)) for d in numbers[-2::-2])) % 10 == 0
	
	
def main():
	process_input(iter(sys.stdin.read()),sys.stdout.write)

if __name__ == '__main__':
	main()

