{ ----------------------------------------- }
{ calcula o fatorial de um numero informado }
{ ----------------------------------------- }

program fatorial;
  var
    num : integer;

  procedure escreveFat( N : integer);
    var cont, result : integer;
    	temp: boolean;

    begin 
    if N > 12 then 
       writeln( "Numero muito grande!!!")
    else begin

           cont := 1;
           result := 1;
           
           while ( cont < N ) do begin
              result := result * cont;
              cont := cont + 1;
           end;

           writeln( "Valor do fatorial: ", result);
       end
     end;

  begin
    writeln( "Informe um numero: ");
    readln( num);
    escreveFat(num);
  end.
