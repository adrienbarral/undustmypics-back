package com.barral.controllers;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class PictureUndustingController {

    @Value("${tmpfolder:/tmp}")
    private String tmpFolder;

    @Value("${undustexecutable}")
    private String undustExe;

    @RequestMapping(value = "/undust-picture", method = RequestMethod.POST)
    @ResponseBody
    byte[] undustPicture(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) return new byte[]{};
        File outFile = new File(tmpFolder + "/" + file.getOriginalFilename());
        BufferedOutputStream stream =
                new BufferedOutputStream(new FileOutputStream(outFile));
        stream.write(file.getBytes());
        stream.close();

        Runtime r = Runtime.getRuntime();
        Process p = r.exec(undustExe + " --file " + outFile.getAbsolutePath() +
                " --seuilRouge 10 --plusGrosseTache 0.07");
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        File inFile = new File(tmpFolder + "/UNDUSTED_" + file.getOriginalFilename());
        BufferedInputStream inStream = new BufferedInputStream((new FileInputStream(inFile)));
        return IOUtils.toByteArray(inStream);
    }
}