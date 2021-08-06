package co.com.ies.pruebas.webservice;


import co.com.ies.pruebas.webservice.task.TaskTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.SecureRandom;
import java.util.Random;

@RestController
public class SplitTransactionController {

    @PostMapping("/resquest")
    public ResponseEntity<String> request(){

        Random rn = new SecureRandom();
        final int nexInt = rn.nextInt();
        return getStringResponseEntity(nexInt);
    }
    @PostMapping("/resquest/{nexInt}")
    public ResponseEntity<String> request(@PathVariable int nexInt){

        return getStringResponseEntity(nexInt);
    }

    private ResponseEntity<String> getStringResponseEntity(int nexInt) {
        System.out.println("SplitTransactionController.request agregando nexInt = " + nexInt);


        return ResponseEntity.ok(String.valueOf(nexInt));
    }

    @PostMapping("/result")
    public ResponseEntity<Boolean> result(@RequestBody TaskTest value){

        final boolean nextBoolean = true;
        return ResponseEntity.ok(nextBoolean);
    }
    @PostMapping("/process")
    public ResponseEntity<String> procesarQueue(){

        return ResponseEntity.ok("Procesada la lista");
    }


}

