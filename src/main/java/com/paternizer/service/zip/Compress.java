/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paternizer.service.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.zip.GZIPOutputStream;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author bcivel
 */
public class Compress {

    public String add_files_to_an_archive(String[] fileUrl, String outputFormat, String outputName) throws Exception {
        String msg = "An error occured compressing the files " + fileUrl.toString();
        try {
            /**
             * Get Files to compress
             */
            Collection<File> filesToCompress = getFiles(fileUrl);
            /**
             * Create output
             */
            File destination = new File(outputName + "." + outputFormat);
            destination.delete();
            /**
             * Compress accordingly to the specified format
             */
            if ("zip".equals(outputFormat)) {
                addFilesToZip(filesToCompress, destination);
                msg = "OK";
            } else if ("gz".equals(outputFormat)) {
                if (filesToCompress.size() == 1) {
                    gz(fileUrl[0], destination);
                    msg = "OK";
                } else {
                    msg = "You're trying to gzip several files. Please, use zip to package several files";
                }
            } else {
                msg = "Unsupported outputFormat. Try using zip, gz";
            }
        } catch (IOException ex) {
        } catch (ArchiveException ex) {
        }
        return msg;
    }

    private Collection<File> getFiles(String[] fileUrl) throws MalformedURLException {
        Collection<File> fileList = new ArrayList();
        for (String file : fileUrl) {
            URL templateURL = new URL(file);
            File source = new File(templateURL.getFile());
            fileList.add(source);
        }
        return fileList;
    }

    private void addFilesToZip(Collection<File> fileList, File destination) throws IOException, ArchiveException {
        OutputStream archiveStream = new FileOutputStream(destination);
        ArchiveOutputStream archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, archiveStream);
        try {
            for (File file : fileList) {
                String entryName = file.getName();
                ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
                archive.putArchiveEntry(entry);

                BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));

                IOUtils.copy(input, archive);
                input.close();
                archive.closeArchiveEntry();
            }
        } finally {
            if (archive != null) {
                archive.finish();
            }
            if (archiveStream != null) {
                archiveStream.close();
            }
        }
    }

    public static void gz(String file, File destination) throws FileNotFoundException, IOException {
        GZIPOutputStream out = null;
        byte[] imageBytes = null;
        InputStream is = null;
        URL templateURL = new URL(file);
        URLConnection connection = templateURL.openConnection();
        InputStream in = connection.getInputStream();
        
        try {
            out = new GZIPOutputStream(
                    new BufferedOutputStream(new FileOutputStream(destination.getPath())));
            byte[] buf = new byte[4096];
            while (true) {
                int len = in.read(buf);
                if (len == -1) {
                    break;
                }
                out.write(buf, 0, len);
            }
            
            out.finish();
            out.close();
        } catch (IOException e) {
            System.err.printf("Failed while reading bytes from %s: %s", templateURL.getPath(), e.getMessage());
            // Perform any other exception handling that's appropriate.
        } finally {
            if (out != null) {
                out.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }
}
