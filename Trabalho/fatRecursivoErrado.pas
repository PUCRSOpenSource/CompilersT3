program fatorial;

  var result, valor : integer;

  function fat ( num : integer) : integer ; 
    var res : integer;
    begin
      writeln("entrou em fat: ",num);

      if num > 1 then     
	 res := num * fat(num-1)
      else 
          res := 1;

     writeln("fim de fat com ", res);
     fat := res;
    end;

  function fat2 ( num : integer) : integer ; 
    {var res : integer;}
    begin
      writeln("entrou em fat2: ",num);

      if num > 1 then     
	 res := num * fat2(num-1)
      else 
          res := 1;

     writeln("fim de fat2 com ", res);
     fat2 := res;
    end;


  begin
     
     writeln("informe um numero: ");
     readln(valor);
     result := fat(valor, 1) ;
     writeln("result: ", result);
  end.
