package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.*;
import com.proximity.technicaltest.service.SubjectService;
import com.proximity.technicaltest.service.TagsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class TagsController {

    private final TagsService tagsService;

    @Autowired
    public TagsController( TagsService tagsService){
        this.tagsService = tagsService;
    }

    @ApiOperation("Create a Tag")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/tags")
    public ResponseEntity create(@RequestBody @Valid final TagsRequest tagsRequest) {
        TagsController.log.info("Request : {}", tagsRequest);
        tagsService.create(tagsRequest.toTagsEntity());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("Update a Tag")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PutMapping("/tags/{tag_name}")
    public ResponseEntity update(@PathVariable("tag_name") String tagName, @RequestBody @Valid final TagsUpsertRequest tagsUpsertRequest) {
        TagsController.log.info("Request : {}", tagsUpsertRequest);
        tagsService.update(tagsUpsertRequest,tagName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Delete a Tag")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @DeleteMapping("/tags/{tag_name}")
    public ResponseEntity delete(@PathVariable("tag_name") String tagName) {
        tagsService.delete(tagName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Get all Tags")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Accepted")
    })
    @GetMapping("/tags")
    public ResponseEntity get() {
       List<TagsModel> tags = tagsService.getAllTags();
        return new ResponseEntity(tags, HttpStatus.OK);
    }


}
