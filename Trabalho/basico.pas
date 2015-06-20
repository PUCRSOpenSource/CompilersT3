program fatorial;
var
	temp1, temp2, temp3: integer;
	result, cont, a: integer;
	yes : boolean;
	
	procedure proc1;
	begin
		writeln("Function dummy!");
	end;
	
	procedure proc2( i, j:integer);
	begin
		if( i < j) then
			while( i < j) do
			begin
				writeln("Valor do i = ", i);
			end;
	end;
	
begin
	writeln(temp1, temp2, temp3);
	proc2( 1, 2);
	temp1 := 1; 
end.
