package com.amida.app;

/**
 * Hello world!
 *
 */

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

//import org.json.JSONObject;
//import org.json.XML;
import java.io.FileInputStream;
import java.io.File;

import java.io.IOException;
import java.util.List;

class JoltSample {
   
    public static void main(String[] args) throws IOException {

        // Read in the XML File.

        String fileContents = "";
        File file = new File("/Users/matthew/Workspace/fhir-transformation-maps/data/sample/HannahBanana_EpicCCD.xml");
        FileInputStream fis = new FileInputStream(file);
            int data = fis.read();
            while(data != -1) {
                // System.out.print(data);
               fileContents = fileContents + (char)data;
               data=fis.read();
            }
            fis.close();

        System.out.println(fileContents);


        // How to access the test artifacts, i.e. JSON files
        //  JsonUtils.classpathToList : assumes you put the test artifacts in your class path
        //  JsonUtils.filepathToList : you can use an absolute path to specify the files

        List chainrSpecJSON = JsonUtils.filepathToList( "/Users/matthew/Workspace/fhir-transformation-maps/data/spec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );

        Object inputJSON = JsonUtils.filepathToList( "/Users/matthew/Workspace/fhir-transformation-maps/data/sample.json" );

        Object transformedOutput = chainr.transform( inputJSON );
        System.out.println( JsonUtils.toJsonString( transformedOutput ) );
    }
}