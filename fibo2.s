Start: not r0 r1
	and r0 r0 r1
	not r1 r0
	add r1 r1 r1
	not r1 r1    ; needed to make reg1 1
	add r2 r1 r1
	add r2 r2 r2 ; contunuously multiplying by 2
	add r2 r2 r2
	add r2 r2 r2
	and r0 r2 r2 ; storing 16 
	add r2 r2 r2 
	add r2 r2 r2
	add r2 r0 r2
	add r2 r2 r0
	add r2 r2 r0
	and r0 r3 r3
	add r3 r1 r3 ; starting with 1
loop: and r1 r3 r3
	add r3 r0 r3 
	and r0 r1 r1
	add r1 r2 r1 ; checking if r1 is last number
	bnz loop
end: not r2 r2 ; continuous loop
	bnz end
