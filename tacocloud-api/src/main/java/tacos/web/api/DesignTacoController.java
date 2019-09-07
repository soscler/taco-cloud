package tacos.web.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.Taco;
import tacos.data.TacoRepository;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
@RequestMapping(path = "/design", produces = "application/json")
@CrossOrigin(origins = "*")

public class DesignTacoController {

    /*@Autowired
    EntityLinks entityLinks;
*/
    private TacoRepository tacoRepo;

    @Autowired
    public DesignTacoController(TacoRepository tacoRepo){
        this.tacoRepo = tacoRepo;
    }

    @GetMapping("/recent")
    public Iterable<Taco> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return tacoRepo.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity tacoById(@PathVariable Long id) {

        Optional<Taco> optTaco = tacoRepo.findById(id);
        if(optTaco.isPresent()){
            return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null,null, HttpStatus.NOT_FOUND);
    }
}
