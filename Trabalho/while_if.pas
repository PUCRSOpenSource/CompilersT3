{ ------------------------------------------ }
{ le tres numeros e escreve o maior dos tres }
{ ------------------------------------------ }

program maior3;

  var
    a : integer;
    b : integer;
    c : integer;
    d : boolean;

  begin
    writeln("Informe 3 numeros: ");
    readln( a );
    readln( b );
    readln( c );
 
    while a < b do begin
        if b < c then b := b + 3
                 else a := a + 3;
       
     writeln ("a : ", a);
     writeln ("b : ", b);
     writeln ("c : ", c);
     writeln ("");
    end
  end.
