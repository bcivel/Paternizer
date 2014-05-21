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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author memiks
 */
public class TemplateService {
    private static Pattern pattern;
    private static Matcher matcher;

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
        List<String> parameters;
        if (template != null) {
            parameters = new ArrayList<String>();
            pattern = Pattern.compile("#(.*)#");
            matcher = pattern.matcher(template);
            // lancement de la recherche de toutes les occurrences
            // si recherche fructueuse
            if (matcher.matches()) {
                // pour chaque groupe
                for (int i = 0; i <= matcher.groupCount(); i++) {
                    // affichage de la sous-chaîne capturée
                    parameters.add(matcher.group(i));
                }
            }

            if (parameters.size() > 0) {
                return parameters;
            }
        }

        return null;
    }

}
