package com.pamarg.fileapi;

import com.pamarg.fileapi.model.Product;
import com.pamarg.fileapi.repo.ProductRepo;
import com.pamarg.fileapi.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;

@SpringBootTest
class UploadingApplicationTests {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ProductService productService;


    @BeforeEach
    public void setUp() {
        productRepo.deleteAll();
    }

    // @Test
// void contextLoads() {
// }
    @Test
    public void testSaveAttachment() throws Exception {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", "Hello, world!".getBytes());
        Product product = productService.saveAttachment(mockFile);
        assertNotNull(product.getId());
        assertEquals("test.txt", product.getFileName());
        assertEquals("text/plain", product.getFileType());
    }

    @Test
    public void testSaveFiles() throws Exception {
        MockMultipartFile mockFile1 = new MockMultipartFile(
                "file", "test1.pdf", "text/plain", "Hello, world!".getBytes());
        MockMultipartFile mockFile2 = new MockMultipartFile(
                "file", "test2.txt", "text/plain", "Goodbye, world!".getBytes());
        productService.saveFiles(new MockMultipartFile[]{mockFile1, mockFile2});
        List<Product> products = productService.getAllFiles();
        System.out.println("Saved files:");
        for (Product product : products) {
            System.out.println(product.getFileName());
        }
        assertEquals(2, products.size());
        assertEquals("test1.pdf", products.get(0).getFileName());
        assertEquals("test2.txt", products.get(1).getFileName());
    }

    @Test
    public void testSaveAttachmentInvalidName() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "../test.txt", "text/plain", "Hello, world!".getBytes());
        assertThrows(Exception.class, () -> productService.saveAttachment(mockFile));
    }

    @Test
    public void testSaveAttachmentTooLarge() {
        byte[] bytes = new byte[1024 * 1024 * 10];
        MockMultipartFile mockFile = new MockMultipartFile(
                "file", "test.txt", "text/plain", bytes);
        assertThrows(Exception.class, () -> productService.saveAttachment(mockFile));
    }
}