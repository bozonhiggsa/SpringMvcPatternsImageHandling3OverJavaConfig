package com.application.springMvc.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.ServletContextResource;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Controller for requests handling
 * @author Ihor Savchenko
 * @version 1.0
 */
@Controller
public class MvcController {

    @Autowired
    ServletContext servletContext;

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/")
    public String getHomePage(){
        return "index";
    }

    @GetMapping(value = "/getImageJpegOrPngOrGif")
    public @ResponseBody byte[] getImageAsByteArray() throws IOException {
        InputStream in = servletContext.getResourceAsStream("/resources/image.jpg");
        //InputStream in = servletContext.getResourceAsStream("/resources/image.png");
        //InputStream in = servletContext.getResourceAsStream("/resources/image.gif");
        return IOUtils.toByteArray(in);
    }

    @GetMapping(value = "/getImageFromResponseEntity")
    public ResponseEntity<byte[]> getImageAsResponseEntity() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        InputStream in = servletContext.getResourceAsStream("/resources/image.gif");
        byte[] media = IOUtils.toByteArray(in);
        //headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        headers.setCacheControl(CacheControl.maxAge(30, TimeUnit.DAYS).getHeaderValue());
        headers.setETag("W/Something like that\"");
        //headers.setETag("\"Something like that\"");
        headers.setExpires(System.currentTimeMillis() + 100000);

        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(value = "/getImageAsResource")
    @ResponseBody
    public Resource getImageAsResource() {
        return new ServletContextResource(servletContext, "/resources/image.gif");
    }

    @GetMapping(value = "/getImageAsResourceFromResponseEntity")
    @ResponseBody
    public ResponseEntity<Resource> getImageAsResourceFromResponseEntity() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        Resource resource =
                new ServletContextResource(servletContext, "/resources/image.jpg");
        System.out.println("resource.contentLength: " + resource.contentLength());
        System.out.println("resource.exists: " + resource.exists());
        System.out.println("resource.isFile: " + resource.isFile());
        System.out.println("resource.isReadable: " + resource.isReadable());
        System.out.println("resource.lastModified: " + resource.lastModified());
        System.out.println("resource.getFilename: " + resource.getFilename());
        System.out.println("resource.getFile: " + resource.getFile());
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping(value = "/getImageAsResourceFromResourceLoader")
    @ResponseBody
    public ResponseEntity<Resource> getImageAsResourceFromResourceLoader() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        //Resource resource = resourceLoader.getResource("/resources/image.gif");
        Resource resource = resourceLoader.getResource("file:e:/tmp/bry.png");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

}