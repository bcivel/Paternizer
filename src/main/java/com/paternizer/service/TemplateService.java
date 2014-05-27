/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.paternizer.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author memiks
 */
public class TemplateService {
    private Pattern pattern;
    private Matcher matcher;

    public String getFile(URL u) throws IOException {
        URLConnection uc = u.openConnection();
        int FileLenght = uc.getContentLength();
        if (FileLenght == -1) {
            throw new IOException("Unable to read file.");
        }

        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new InputStreamReader(uc.getInputStream()));

        String line;
        StringBuilder template = new StringBuilder();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.length() == 0) {
                break;
            }
            template.append(line);
        }

        return template.toString();
    }

    public List<String> getParameters(String template) {
        Set<String> parameters;
        if (template != null) {
            parameters = new HashSet<String>();

            pattern = Pattern.compile("#([0-9a-zA-z_-]*)#");
            matcher = pattern.matcher(template);
            while (matcher.find()) {
                parameters.add(matcher.group(1));
            }

            if (parameters.size() > 0) {
                List<String> sortedParameters = new ArrayList<String>(parameters);
                Collections.sort(sortedParameters);
                return sortedParameters;
            }

        }

        return null;
    }

}
