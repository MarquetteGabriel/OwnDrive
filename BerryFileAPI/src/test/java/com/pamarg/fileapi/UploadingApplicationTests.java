package com.pamarg.fileapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

@SpringBootTest
class UploadingApplicationTests {/*
    @Autowired
    private DocumentRepo documentRepo;
    @Autowired
    private DocumentService documentService;


    @BeforeEach
    public void setUp() {
        documentRepo.deleteAll();
    }

    // @Test
// void contextLoads() {
// }
    @Test
    public void testSaveAttachment() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello, world!".getBytes());
        Document document = documentService.saveAttachment(mockFile);
        assertNotNull(document.getId());
        assertEquals("test.txt", document.getFileName());
        assertEquals("text/plain", document.getFileType());
    }

    @Test
    public void testSaveFiles() throws Exception {
        MockMultipartFile mockFile1 = new MockMultipartFile(
                "file", "test1.pdf", "text/plain", "Hello, world!".getBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile(
                "file", "test2.txt", "text/plain", "Goodbye, world!".getBytes());
        documentService.saveFiles(new MockMultipartFile[]{mockFile1, mockFile2});
        List<Document> documents = documentService.getAllFiles();
        System.out.println("Saved files:");
        for (Document document : documents) {
            System.out.println(document.getFileName());
        }
        assertEquals(2, documents.size());
        assertEquals("test1.pdf", documents.get(0).getFileName());
        assertEquals("test2.txt", documents.get(1).getFileName());
    }

    @Test
    public void testSaveAttachmentInvalidName() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "../test.txt", "text/plain", "Hello, world!".getBytes());
        assertThrows(Exception.class, () -> documentService.saveAttachment(mockFile));
    }

    @Test
    public void testSaveAttachmentTooLarge() {
        byte[] bytes = new byte[1024 * 1024 * 10];
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", bytes);
        assertThrows(Exception.class, () -> documentService.saveAttachment(mockFile));
    }*/
}