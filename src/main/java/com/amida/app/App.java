package com.amida.app;

/**
 * Hello world!
 *
 */

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import java.io.IOException;
import java.util.List;

class JoltSample {
   
    public static void main(String[] args) throws IOException {
        
        // Only really need the pretty printing to review JSON manually.
        final int PRETTY_PRINT_INDENTATION = 4;

        String XMLString = "";
        String JSONString = "";
        String JSONOutput = "";
        JSONObject xmlJSONObj = new JSONObject();

        // Read in the XML File.
        File file = new File("/Users/matthew/Workspace/fhir-transformation-maps/data/sample/HannahBanana_EpicCCD.xml");
        FileInputStream fis = new FileInputStream(file);
            int data = fis.read();
            while(data != -1) {
                // System.out.print(data);
                XMLString = XMLString + (char)data;
               data=fis.read();
            }
            fis.close();

        // Convert XML to JSON.
        try {
            xmlJSONObj = XML.toJSONObject(XMLString);
            JSONString = xmlJSONObj.toString(PRETTY_PRINT_INDENTATION);
        } catch (JSONException je) {
            System.out.print(je.toString());
        }

        // Get the list of rules.
        List chainrSpecJSON = JsonUtils.filepathToList( "/Users/matthew/Workspace/fhir-transformation-maps/data/specs/spec.json" );
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );


        // Can save a little time by reading from the JSON conversion directly.
        //Object inputJSON = JsonUtils.filepathToObject( "/Users/matthew/Workspace/fhir-transformation-maps/data/sample/HannahBanana_EpicCCD.json" );
        Object inputJSON = JsonUtils.jsonToObject( JSONString );

        Object transformedOutput = chainr.transform( inputJSON );
        JSONOutput = JsonUtils.toJsonString( transformedOutput );
        System.out.println(JSONOutput);

        // Output JSON data.
        FileOutputStream outputStream = new FileOutputStream("/Users/matthew/Workspace/fhir-transformation-maps/data/sample/transformed.json");
        byte[] strToBytes = JSONOutput.getBytes();
        outputStream.write(strToBytes);
        outputStream.close();

    }
}