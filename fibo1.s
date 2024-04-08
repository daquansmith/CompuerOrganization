Start: not r0 r1  ;making reg0 ff
       and r0 r0 r1  
       not r1 r0           
       add r1 r1 r1  ;multiplying by two
	 not r1 r1      ; reg now equals one
end:  add r3 r0 r1  ;storing into reg three
	 not r0 r1 
	 not r0 r0 
	 not r1 r3
	 not r1 r1
     	 bnz end 	; looping fibo sequence
	
          