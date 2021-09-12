package ru.alekseiadamov.pictureservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.alekseiadamov.pictureservice.service.PictureService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/picture")
public class PictureController {

    private final PictureService service;

    @Autowired
    public PictureController(PictureService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public void downloadProductPicture(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        final Optional<String> contentType = service.getPictureContentTypeById(id);
        if (contentType.isPresent()) {
            final Optional<byte[]> pictureData = service.getPictureDataById(id);
            if (pictureData.isPresent()) {
                response.setContentType(contentType.get());
                response.getOutputStream().write(pictureData.get());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
