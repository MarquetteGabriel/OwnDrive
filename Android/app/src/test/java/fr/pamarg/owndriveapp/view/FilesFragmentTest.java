package fr.pamarg.owndriveapp.view;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FilesFragmentTest {

    @Test
    public void cropTextLengthTest() {
        FilesFragment filesFragment = new FilesFragment();

        // Test when the length of the text is less than 17
        String shortText = "Hello";
        assertEquals(shortText, filesFragment.cropTextLength(shortText));

        // Test when the length of the text is greater than 17
        String longText = "This is a long text that needs to be cropped";
        String croppedText = "This is a long te...";
        assertEquals(croppedText, filesFragment.cropTextLength(longText));

        // Test when the length of the text is exactly 17
        String textExactly17 = "Exactly 17 chars";
        assertEquals(textExactly17, filesFragment.cropTextLength(textExactly17));
    }
}