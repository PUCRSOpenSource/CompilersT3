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


  begin
     
     writeln("informe um numero: ");
     readln(valor);
     result := fat(valor) ;
     writeln("result: ", result);
  end.
