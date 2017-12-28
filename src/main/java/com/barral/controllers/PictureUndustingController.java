package com.barral.controllers;

import com.barral.repository.StatisticsRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class PictureUndustingController {

    @Value("${tmpfolder:/tmp}")
    private String tmpFolder;

    @Value("${undustexecutable}")
    private String undustExe;

    @Autowired
    StatisticsRepository statisticsRepository;

    @RequestMapping(value = "/undust-picture", method = RequestMethod.POST)
    @ResponseBody
    ResponseEntity<byte[]> undustPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return new ResponseEntity<byte[]>(HttpStatus.NO_CONTENT);
        }

        File outFile = new File(tmpFolder + "/" + file.getOriginalFilename());
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(outFile));
        stream.write(file.getBytes());
        stream.close();

        Runtime r = Runtime.getRuntime();
        Process p = r.exec(undustExe + " --file " + outFile.getAbsolutePath() +
                " --seuilRouge 30 --plusGrosseTache 0.07");
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        File inFile = new File(tmpFolder + "/UNDUSTED_" + file.getOriginalFilename());
        BufferedInputStream inStream = new BufferedInputStream((new FileInputStream(inFile)));
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content", "image/jpeg");
        responseHeaders.set("content-type", "image/jpeg");

        statisticsRepository.incrementUsageForToday();

        return new ResponseEntity<>(IOUtils.toByteArray(inStream), responseHeaders, HttpStatus.OK);
    }
}
