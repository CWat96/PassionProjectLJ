package com.example.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller //handles incoming request from an outgoing response to client
// provides necessary endpoints to access and manipulate respective domain objects
@RequestMapping(value = "/Stone")
public class StoneGemController {
    private StoneGemService service;

    @Autowired // constructor
    public StoneGemController(StoneGemService service) {
        this.service = service;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<StoneGem> create(@RequestBody StoneGem stoneGem) {
        return new ResponseEntity<>(service.create(stoneGem), HttpStatus.CREATED);
    }
    // allows me to create new stone/gem record
    //@request body tells Spring request body needs to be converted to an instance of stoneGem
    // delegates the stone/gem persistence to StoneGemRepository’s save method called by the StoneGemService’s create method.

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<StoneGem> readById(@PathVariable Long id) {
        return new ResponseEntity<>(service.readById(id), HttpStatus.OK);
    }
    //allows us to access an individual stone/gem
    //The placeholder {id} along with @PathVarible annotation allows Spring to examine the request URI path and extract the pollId parameter value.
    //StoneGemService’s readById finder method retrieves the respective StoneGem from the StoneGemRepository and pass the result to a ResponseEntity
    @GetMapping(value = "/readAll")
    public ResponseEntity<List<StoneGem>> readAll() {
        return new ResponseEntity<>(service.readAll(), HttpStatus.OK);
    }
    //allows me to access all StoneGem records
    //The value attribute in @GetMapping takes a URI template /read/{id}
    // all other functions similar to /read/{id

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<StoneGem> updateById(
            @PathVariable Long id,
            @RequestBody StoneGem newData) {
        return new ResponseEntity<>(service.update(id, newData), HttpStatus.OK);
    }
    //enables me to update a StoneGem with new data.

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<StoneGem> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(service.deleteById(id), HttpStatus.OK);
    }
    //enables me to delete a StoneGem
}
