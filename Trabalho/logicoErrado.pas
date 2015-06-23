{ ----------------------------------------- }
{ faz nada                                  }
{ ----------------------------------------- }

program vazio;

  var
     i, j : integer;
     b1, b2 : boolean;

  begin
 
     i := 5;
     j := 7;
 
     if i > j then 
     	b1 := 1
     else 
     	b1 := false;

     b2 := (i < j)  and not b1;  

    if b1 then writeln ("b1 eh verdadeiro")
          else writeln ("b1 eh falso");

    

    if not b2 then writeln ("b2 eh falso")
          else writeln ("b2 eh verdadeiro");

  end.
