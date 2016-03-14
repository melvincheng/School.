INCLUDE Irvine32.inc

.data
numPrompt BYTE "Enter the size of the message(1-50): ",0
strPrompt BYTE "Enter the message: ",0
keyPrompt BYTE "Enter the key: ",0
encMessage BYTE "Encrypted message: ",0
decMessage BYTE "Decrypted message: ",0
key BYTE 50 DUP(0), 0
message BYTE 50 DUP(0), 0
encrypt BYTE 50 DUP(0), 0
decrypt BYTE 50 DUP(0), 0
len DWORD 0

.code
main proc
	mov edx, OFFSET numPrompt
	call WriteString
	call ReadInt
	mov len, eax

	mov edx, OFFSET strPrompt
	call WriteString
	mov ecx, len - 1
	mov edx, OFFSET message
	call ReadString

	mov edx, OFFSET keyPrompt
	call WriteString
	mov ecx, len - 1
	mov edx, OFFSET key
	call ReadString

	mov ecx, len
	mov eax, OFFSET message
	mov ebx, OFFSET key
	mov esi, OFFSET encrypt

L1: 
	mov dl,[eax]
	xor dl,[ebx]
	mov [esi], dl
	
	inc eax
	inc ebx
	inc esi
	loop L1

	mov ecx, len
	mov eax, OFFSET encrypt
	mov ebx, OFFSET key
	mov esi, OFFSET decrypt

L2: 
	mov dl,[eax]
	xor dl,[ebx]
	mov [esi], dl
	
	inc eax
	inc ebx
	inc esi
	loop L2

	mov edx, OFFSET encMessage
	call WriteString
	call Crlf

	mov edx, OFFSET encrypt
	call WriteString
	call Crlf

	mov edx, OFFSET decMessage
	call WriteString
	call Crlf

	mov edx, OFFSET decrypt
	call WriteString
	call Crlf

	mov eax, 0
	ret 0

main endp
end main