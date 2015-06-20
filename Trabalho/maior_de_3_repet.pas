{ ----------------------------------------------- }
{ le sequencias de tres numeros e escreve o maior }
{ ----------------------------------------------- }

program maior3repet;

  var
    a,b,c : integer;
    maior : integer;

  begin
    while true do begin
       writeln (" ");
       writeln ("Informe 3 numeros: ");
       readln(a);
       readln(b);
       readln(c);
 
       if (a > b) and (a > c) then 
          maior := a
       else if b > c then
               maior := b
            else maior := c;
 
        writeln ("maior dos tres valores: ", maior);
    end
  end.
