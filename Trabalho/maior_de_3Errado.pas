{ ------------------------------------------ }
{ le tres numeros e escreve o maior dos tres }
{ ------------------------------------------ }

program maior3;

  var
    a,b,c : integer;
    maior : integer;
    t     : boolean;

  begin
    writeln("Informe 3 numeros: ");
    readln( a );
    readln( b );
    readln( c );
    readln( t );
 
    if (a > b) and (a > c) then 
       maior := a
    else if b > c then
            maior := b
         else begin
                maior := c;
              end;

     writeln ("maior dos tres valores: ", maior);
  end.
