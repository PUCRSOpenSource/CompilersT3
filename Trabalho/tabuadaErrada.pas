{ ----------------------------------------------- }
{ escreve a tabuada de 1 a 10 no formato tabular  }
{ ----------------------------------------------- }

program tabuada;

  var numero : integer;

  procedure mostraTabuada( param: integer);
  var
    i : integer;
    j : integer;

  begin
    i := 1; 
    while i < param do begin
       writeln( " ");
 
       j := 1;
       while j < param do begin
             if i*j < param then 
             	writeln(" ");
             
             writeln(i*j, "   ");
             j := j + 1;
       end;

       i := i + true;

    end;

    writeln (" ");
  end;

  begin
    writeln("tabuada - matriz ");
    mostraTabuada(numero);
  end.
