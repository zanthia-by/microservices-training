package com.example.mservices.processor.service;

import org.apache.commons.io.IOUtils;
import org.apache.tika.exception.TikaException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class MetadataServiceTest {

    @Test
    void testExtractMetadata() throws IOException, TikaException, SAXException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("some-mp3.mp3").getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());

        MetadataService testee = new MetadataService();
        var mp3Metadata = testee.extractMetadata(bytes);

        assertEquals("Ich Will", mp3Metadata.getName());
        assertEquals("Rammstein", mp3Metadata.getArtist());
        assertEquals("Mutter", mp3Metadata.getAlbum());
        assertEquals(285, mp3Metadata.getLength());
        assertEquals(2001, mp3Metadata.getYear());
    }

}