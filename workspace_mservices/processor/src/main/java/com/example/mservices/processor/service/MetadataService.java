package com.example.mservices.processor.service;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MetadataService {

    public Mp3Metadata extractMetadata(byte[] bytes) throws TikaException, IOException, SAXException {
        Metadata metadata = new Metadata();
        Mp3Parser Mp3Parser = new Mp3Parser();
        Mp3Parser.parse(new ByteArrayInputStream(bytes), new BodyContentHandler(), metadata, new ParseContext());

        Mp3Metadata mp3Metadata = new Mp3Metadata();
        mp3Metadata.setName(metadata.get("dc:title"));
        mp3Metadata.setArtist(metadata.get("xmpDM:artist"));
        mp3Metadata.setAlbum(metadata.get("xmpDM:album"));
        String yearString = metadata.get("xmpDM:releaseDate");
        if (yearString != null) {
            try {
                mp3Metadata.setYear(Integer.valueOf(yearString));
            } catch (NumberFormatException nfe) {
                // do nothing
            }
        }
        String lengthSecString = metadata.get("xmpDM:duration");
        if (yearString != null) {
            try {
                mp3Metadata.setLength(Double.valueOf(lengthSecString).intValue());
            } catch (NumberFormatException nfe) {
                // do nothing
            }
        }

        return mp3Metadata;
    }

}
