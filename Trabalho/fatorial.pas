{ ----------------------------------------- }
{ calcula o fatorial de um numero informado }
{ ----------------------------------------- }

program fatorial;

  var
    num : integer;
    cont, result : integer;

  begin
    writeln( "Informe um numero: ");
    readln( num);
 
    if num > 12 then 
       writeln( "Numero muito grande!!!")
    else begin

           cont := 1;
           result := 1;

           while ( cont < num ) do begin
              result := result * cont;
              cont := cont + 1;
           end;

           writeln( "Valor do fatorial: ", result);
       end
  end.
