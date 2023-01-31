package com.example.mservices.resource.webservice;

import com.amazonaws.services.s3.model.S3Object;
import com.example.mservices.resource.service.ResourceService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping(consumes = "audio/mpeg")
    @ResponseBody
    public Map<String, Object> acceptData(InputStream dataStream) throws Exception {
        var id = resourceService.upload(dataStream);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", id);
        return response;
    }

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    @ResponseBody
    HttpEntity<byte[]> getResource(@PathVariable Long id, @RequestHeader(value = "Range", required = false) String range) throws IOException {
        try (S3Object s3Object = resourceService.download(id)) {
            if (s3Object == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .build();
            }

            var bytes = s3Object.getObjectContent().readAllBytes();

            if (range == null) {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(bytes);
            }

            if (!range.contains("bytes=")) {
                return ResponseEntity
                        .status(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE)
                        .build();
            }

            String[] ranges = range.substring("bytes=".length()).split("-");
            int rangeStart = Integer.parseInt(ranges[0]);
            var bytesRange = Arrays.copyOfRange(bytes, rangeStart, getRangeEnd(range, bytes.length));
            return ResponseEntity
                    .status(HttpStatus.PARTIAL_CONTENT)
                    .body(bytesRange);
        }
    }

    private int getRangeEnd(String range, int maxRangeEnd) {
        String[] ranges = range.split("-");
        if (ranges.length > 1) {
            return Math.min(Integer.parseInt(ranges[1]), maxRangeEnd);
        }
        return maxRangeEnd;
    }

    @DeleteMapping()
    ResponseEntity<?> deleteResource(@RequestParam("id") String ids) {
        if (ids.length() >= 200) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        var listOfIds = Stream.of(ids.split(","))
                .map(Long::parseLong)
                .toList();
        var deletedIds = resourceService.delete(listOfIds);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("ids", deletedIds);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
