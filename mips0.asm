	.data
	nl: .asciiz "\n"
	.text
	.globl main
main:
	#load value into $v0
	li $v0 3
	#load value in $v0 into $a0 and print
	move $a0 $v0
	li $v0 1
	syscall
	#print new line
	li $v0 4
	la $a0 nl
	syscall
	#normal termination
	li $v0 10
	syscall
