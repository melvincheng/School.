INCLUDE Irvine32.inc

.data
countPrompt	BYTE "How many integers: ", 0
count		DWORD 0
numPrompt	BYTE "Enter an integer: ", 0
sumLine		BYTE "-----", 0
sum			DWORD ?

.code
main proc
	mov edx, OFFSET countPrompt
	call WriteString
	call ReadInt
	mov count, eax
	mov ecx, count
	mov sum, 0
	mov edx, OFFSET numPrompt

L1: call WriteString
	call ReadInt
	push eax
	add sum, eax
	loop L1 

	mov ecx, count

L2: pop eax
	mov [edx], eax
	call WriteInt
	call Crlf
	loop L2

	mov edx, OFFSET sumLine
	call WriteString
	call Crlf

	mov eax, sum
	mov [edx], eax
	call WriteInt

	mov eax, 0
	ret 0

main endp
end main