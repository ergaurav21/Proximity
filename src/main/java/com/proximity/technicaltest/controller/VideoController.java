package com.proximity.technicaltest.controller;

import com.proximity.technicaltest.model.*;
import com.proximity.technicaltest.service.VideoService;
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
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController( VideoService videoService){
        this.videoService = videoService;
    }

    @ApiOperation("Create a Video")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 201, message = "Created"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/videos")
    public ResponseEntity create(@RequestBody @Valid final VideoRequest videoRequest) {
        VideoController.log.info("Request : {}", videoRequest);
        videoService.create(videoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @ApiOperation("Update a Video")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PutMapping("/videos/{title}")
    public ResponseEntity update(@PathVariable("title") String title, @RequestBody @Valid final VideoUpsertRequest videoUpsertRequest) {
        VideoController.log.info("Request : {}", videoUpsertRequest);
        videoService.update(videoUpsertRequest,title);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Delete a Video")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @DeleteMapping("/videos/{title}")
    public ResponseEntity delete(@PathVariable("title") String title) {
        videoService.delete(title);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Get all Videos")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Accepted")
    })
    @GetMapping("/videos")
    public ResponseEntity getAll(@RequestParam(value = "title",required = false) String title, @RequestParam(value = "tag",required = false) String tag) {
        List<VideoModel> videos = videoService.getAllVideos(title, tag);
        return new ResponseEntity(videos, HttpStatus.OK);
    }

    @ApiOperation("Update  Tags and Lessons")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 202, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @PatchMapping("/lessons/{title}")
    public ResponseEntity updateTagsAndLessons(
            @PathVariable("title") String title,
            @RequestBody @Valid  VideoUpsertRequest videoUpsertRequest) {
        VideoController.log.info("Request : {}", videoUpsertRequest);
        videoService.updateLessonsAndTags(videoUpsertRequest, title);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @ApiOperation("Get all Videos")
    @ApiResponses({
            @io.swagger.annotations.ApiResponse(code = 200, message = "Accepted"),
            @io.swagger.annotations.ApiResponse(code = 400, message = "Bad Request")
    })
    @GetMapping("/videos{title}")
    public ResponseEntity get(@PathVariable("title") String title) {
        VideoModel video = videoService.getVideoDetails(title);
        return new ResponseEntity(video, HttpStatus.OK);
    }
}
